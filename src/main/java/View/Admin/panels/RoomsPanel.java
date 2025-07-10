package View.Admin.panels;

import View.Admin.common.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class RoomsPanel extends BasePanel {
    
    public RoomsPanel() {
        super("Quản lý phòng chiếu", "🎭");
    }
    
    @Override
    protected void createComponents() {
        // Tạo search panel
        String[] fieldLabels = {"Tên phòng"};
        searchPanel = new SearchPanel(fieldLabels, new String[]{}, new String[][]{});
        
        // Tạo table
        String[] columns = {"ID", "Tên phòng", "Ghế đôi", "Ghế đơn", "Trạng thái", "Ghi chú"};
        table = createStyledTable(columns);
        
        // Tạo form panel
        String[] formFieldLabels = {"Tên phòng", "Ghế đôi", "Ghế đơn"};
        formPanel = new FormPanel("Thông tin phòng chiếu", formFieldLabels, new String[]{}, new String[][]{});
        
        // Tạo button panel
        buttonPanel = new ButtonPanel(new String[]{});
    }
    
    @Override
    protected void addSampleData() {
        Object[][] sampleData = {
            {1, "Phòng 1", 20, 80, "Hoạt động", "Phòng chiếu thường"},
            {2, "Phòng 2", 10, 70, "Hoạt động", "Phòng chiếu 3D"},
            {3, "Phòng 3", 15, 55, "Hoạt động", "Phòng IMAX"},
            {4, "Phòng 4", 8, 32, "Hoạt động", "Phòng VIP"},
            {5, "Phòng 5", 25, 100, "Bảo trì", "Đang bảo trì"},
            {6, "Phòng 6", 12, 90, "Hoạt động", "Phòng 3D mới"},
            {7, "Phòng 7", 18, 60, "Hoạt động", "Phòng IMAX lớn"},
            {8, "Phòng 8", 5, 30, "Hoạt động", "Phòng VIP cao cấp"},
            {9, "Phòng 9", 22, 98, "Hoạt động", "Phòng chiếu thường"},
            {10, "Phòng 10", 14, 86, "Hoạt động", "Phòng 3D tiêu chuẩn"}
        };
        
        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
    }
    
    @Override
    protected void displaySelectedRowData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            formPanel.setTextFieldValue(0, table.getValueAt(selectedRow, 1).toString()); // Tên phòng
            formPanel.setTextFieldValue(1, table.getValueAt(selectedRow, 2).toString()); // Ghế đôi
            formPanel.setTextFieldValue(2, table.getValueAt(selectedRow, 3).toString()); // Ghế đơn
        }
    }
    
    @Override
    protected void clearForm() {
        formPanel.clearForm();
    }
    
    @Override
    protected Map<String, String> getFormData() {
        Map<String, String> data = new HashMap<>();
        data.put("roomName", formPanel.getTextFieldValue(0));
        data.put("doubleSeat", formPanel.getTextFieldValue(1));
        data.put("singleSeat", formPanel.getTextFieldValue(2));
        return data;
    }
    
    @Override
    protected void handleEdit(int selectedRow) {
        System.out.println("Sửa phòng chiếu ở hàng: " + selectedRow);
        System.out.println("Dữ liệu form: " + getFormData());
    }
    
    @Override
    protected void handleAdd() {
        System.out.println("Thêm phòng chiếu mới");
        System.out.println("Dữ liệu form: " + getFormData());
    }
    
    @Override
    protected void handleSearch() {
        JTextField[] searchFields = searchPanel.getSearchFields();
        JComboBox<String>[] searchCombos = searchPanel.getSearchCombos();
        
        String roomName = searchFields[0].getText().trim();
        String roomType = searchCombos[0].getSelectedItem().toString();
        
        System.out.println("Tìm kiếm: " + roomName + ", " + roomType);
    }
} 