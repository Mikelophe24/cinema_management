package View.Admin.panels;

import Dao.TheaterDao;
import Enum.TheaterEnum;
import Model.Theater;
import View.Admin.common.*;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomsPanel extends BasePanel {

    public RoomsPanel() {
        super("Quản lý phòng chiếu", "");
        loadDataFromDatabase();
    }

    @Override
    protected void createComponents() {
        String[] fieldLabels = {"Tên phòng"};
        String[] comboOptions = {"Tất cả", "active", "closed", "maintenance"};
        searchPanel = new SearchPanel(fieldLabels, new String[]{"Trạng thái"}, new String[][]{comboOptions});

        String[] columns = {"ID", "Tên phòng", "Ghế đơn", "Ghế đôi", "Trạng thái", "Mô tả"};
        table = createStyledTable(columns);

        String[] formFieldLabels = {"Tên phòng", "Ghế đơn", "Ghế đôi", "Mô tả"};
        String[] formComboLabels = {"Trạng thái"};
        String[][] formComboOptions = {{"active", "closed", "maintenance"}};
        formPanel = new FormPanel("Thông tin phòng chiếu", formFieldLabels, formComboLabels, formComboOptions);

        buttonPanel = new ButtonPanel(new String[]{});
    }

    @Override
    protected void displaySelectedRowData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            formPanel.setTextFieldValue(0, table.getValueAt(selectedRow, 1).toString()); // Tên phòng
            formPanel.setTextFieldValue(1, table.getValueAt(selectedRow, 2).toString()); // Ghế đơn
            formPanel.setTextFieldValue(2, table.getValueAt(selectedRow, 3).toString()); // Ghế đôi
            formPanel.setComboBoxValue(0, table.getValueAt(selectedRow, 4).toString());  // Trạng thái
            formPanel.setTextFieldValue(3, table.getValueAt(selectedRow, 5).toString()); // Mô tả
        }
    }

    @Override
    protected void clearForm() {
        formPanel.clearForm();
        table.clearSelection();
    }

    @Override
    protected Map<String, String> getFormData() {
        Map<String, String> data = new HashMap<>();
        data.put("roomName", formPanel.getTextFieldValue(0));
        data.put("normalSeat", formPanel.getTextFieldValue(1));
        data.put("coupleSeat", formPanel.getTextFieldValue(2));
        data.put("description", formPanel.getTextFieldValue(3));
        data.put("status", formPanel.getComboBoxValue(0));
        return data;
    }

    @Override
    protected void handleAdd() {
        try {
            Map<String, String> data = getFormData();
            String name = data.get("roomName").trim();
            String normalSeatStr = data.get("normalSeat");
            String coupleSeatStr = data.get("coupleSeat");

            if (normalSeatStr.isEmpty() || coupleSeatStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ số ghế đơn và ghế đôi!");
                return;
            }

            int normalSeat = Integer.parseInt(normalSeatStr);
            int coupleSeat = Integer.parseInt(coupleSeatStr);
            String description = data.get("description");
            String statusStr = data.get("status");

            int seatCount = normalSeat + coupleSeat;
            int capacity = 2 * seatCount - normalSeat;

            if (coupleSeat < 0 || normalSeat < 0) {
                JOptionPane.showMessageDialog(this, "Số ghế không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            TheaterEnum.Status status = TheaterEnum.Status.valueOf(statusStr.toUpperCase());

            Theater theater = new Theater(
                    name,
                    status,
                    capacity,
                    seatCount,
                    description,
                    ""
            );

            Theater created = TheaterDao.create(theater);
            if (created != null) {
                JOptionPane.showMessageDialog(this, "Thêm phòng chiếu thành công!");
                loadDataFromDatabase();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    @Override
    protected void handleEdit(int selectedRow) {
        try {
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phòng để sửa.");
                return;
            }

            int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
            Map<String, String> data = getFormData();
            String name = data.get("roomName").trim();
            int normalSeat = Integer.parseInt(data.get("normalSeat"));
            int coupleSeat = Integer.parseInt(data.get("coupleSeat"));
            String description = data.get("description");
            String statusStr = data.get("status");

            int seatCount = normalSeat + coupleSeat;
            int capacity = 2 * seatCount - normalSeat;

            if (coupleSeat < 0 || normalSeat < 0) {
                JOptionPane.showMessageDialog(this, "Số ghế không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            TheaterEnum.Status status = TheaterEnum.Status.valueOf(statusStr.toUpperCase());

            Map<String, Object> updateFields = new HashMap<>();
            updateFields.put("name", name);
            updateFields.put("capacity", capacity);
            updateFields.put("seat_count", seatCount);
            updateFields.put("status", status.getValue());
            updateFields.put("description", description);
            updateFields.put("image", "");

            boolean updated = TheaterDao.update(id, updateFields);
            if (updated) {
                JOptionPane.showMessageDialog(this, "Cập nhật phòng chiếu thành công!");
                loadDataFromDatabase();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật: " + e.getMessage());
        }
    }

    @Override
    protected void handleSearch() {
        JTextField[] searchFields = searchPanel.getSearchFields();
        JComboBox<String>[] comboBoxes = searchPanel.getComboBoxes();

        String keyword = searchFields[0].getText().trim().toLowerCase();
        String selectedStatus = comboBoxes[0].getSelectedItem().toString().toLowerCase();

        List<TheaterDao.TheaterWithSeats> list = TheaterDao.queryList();
        tableModel.setRowCount(0);

        for (TheaterDao.TheaterWithSeats tws : list) {
            Theater t = tws.getTheater();
            boolean matchName = t.getName().toLowerCase().contains(keyword);
            boolean matchStatus = selectedStatus.equals("tất cả") || t.getStatus().getValue().toLowerCase().equals(selectedStatus);

            if (matchName && matchStatus) {
                int coupleSeat = t.getCapacity() - t.getSeatCount();
                int normalSeat = 2 * t.getSeatCount() - t.getCapacity();

                tableModel.addRow(new Object[]{
                        t.getId(), t.getName(), normalSeat, coupleSeat, t.getStatus().getValue(), t.getDescription()
                });
            }
        }
    }

    @Override
    protected void loadDataFromDatabase() {
        List<TheaterDao.TheaterWithSeats> list = TheaterDao.queryList();
        tableModel.setRowCount(0);

        for (TheaterDao.TheaterWithSeats tws : list) {
            Theater t = tws.getTheater();
            int coupleSeat = t.getCapacity() - t.getSeatCount();
            int normalSeat = 2 * t.getSeatCount() - t.getCapacity();

            tableModel.addRow(new Object[]{
                    t.getId(), t.getName(), normalSeat, coupleSeat, t.getStatus().getValue(), t.getDescription()
            });
        }
    }

    @Override
    protected void addSampleData() {
        // Không dùng dữ liệu giả nữa.
    }

    @Override
    protected void handleDelete(int selectedRow) {
        try {
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một phòng để xóa.");
                return;
            }
            int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
            boolean deleted = TheaterDao.delete(id);
            if (deleted) {
                JOptionPane.showMessageDialog(this, "Xóa phòng chiếu thành công!");
                loadDataFromDatabase();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + e.getMessage());
        }
    }
}
