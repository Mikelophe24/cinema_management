package View.Admin.panels;

import Dao.EmployeeDao;
import Model.Employee;
import View.Admin.common.*;

import javax.swing.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import View.Admin.common.BasePanel;
import View.Admin.common.ButtonPanel;
import View.Admin.common.FormPanel;
import View.Admin.common.SearchPanel;

public class StaffPanel extends BasePanel {

    public StaffPanel() {
        super("Quản lý nhân viên", "👥");
    }

    @Override
    protected void createComponents() {
        // 🔸 Tạo SearchPanel (chỉ gồm Họ tên, SĐT)
        String[] fieldLabels = {"Họ tên", "SĐT"};
        searchPanel = new SearchPanel(fieldLabels);

        // 🔸 Tạo bảng hiển thị
        String[] columns = {"ID", "Họ tên", "SĐT", "Email", "Giới tính", "Ngày vào làm"};
        table = createStyledTable(columns);

        // 🔸 Tạo FormPanel (gồm 4 text fields + 1 combo box)
        String[] formFieldLabels = {"Họ tên", "SĐT", "Email", "Ngày vào làm"};
        String[] formComboLabels = {"Giới tính"};
        String[][] formComboOptions = {{"Nam", "Nữ"}};
        formPanel = new FormPanel("Thông tin nhân viên", formFieldLabels, formComboLabels, formComboOptions);

        // 🔸 Button panel
        buttonPanel = new ButtonPanel(new String[]{});
    }

    @Override
    protected void addSampleData() {
        loadDataFromDatabase();
    }

    @Override
    protected void displaySelectedRowData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            formPanel.setTextFieldValue(0, table.getValueAt(selectedRow, 1).toString()); // Họ tên
            formPanel.setTextFieldValue(1, table.getValueAt(selectedRow, 2).toString()); // SĐT
            formPanel.setTextFieldValue(2, table.getValueAt(selectedRow, 3).toString()); // Email
            formPanel.setTextFieldValue(3, table.getValueAt(selectedRow, 5).toString()); // Ngày vào làm
            formPanel.setComboBoxValue(0, table.getValueAt(selectedRow, 4).toString()); // Giới tính
        }
    }

    @Override
    protected void clearForm() {
        formPanel.clearForm();
    }

    @Override
    protected Map<String, String> getFormData() {
        Map<String, String> data = new HashMap<>();
        data.put("fullName", formPanel.getTextFieldValue(0));
        data.put("phone", formPanel.getTextFieldValue(1));
        data.put("email", formPanel.getTextFieldValue(2));
        data.put("hireDate", formPanel.getTextFieldValue(3));
        data.put("gender", formPanel.getComboBoxValue(0));
        return data;
    }

    @Override
    protected void handleAdd() {
        Map<String, String> data = getFormData();
        try {
            Employee emp = new Employee();
            emp.setFullName(data.get("fullName"));
            emp.setPhoneNumber(data.get("phone"));
            emp.setEmail(data.get("email"));
            emp.setGender("Nam".equals(data.get("gender")) ? 1 : 0);
            emp.setHireDate(LocalDate.parse(data.get("hireDate"))); // ⚠ Yêu cầu định dạng yyyy-MM-dd

            boolean success = EmployeeDao.add(emp);
            if (success) {
                loadDataFromDatabase();
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    protected void handleEdit(int selectedRow) {
        int employeeId = (int) table.getValueAt(selectedRow, 0);
        Map<String, String> data = getFormData();

        try {
            Map<String, Object> updateFields = new HashMap<>();
            updateFields.put("full_name", data.get("fullName"));
            updateFields.put("phone_number", data.get("phone"));
            updateFields.put("email", data.get("email"));
            updateFields.put("gender", "Nam".equals(data.get("gender")) ? 1 : 0);
            updateFields.put("hire_date", LocalDate.parse(data.get("hireDate")));

            boolean success = EmployeeDao.update(employeeId, updateFields);
            if (success) {
                loadDataFromDatabase();
                JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    protected void loadDataFromDatabase() {
        tableModel.setRowCount(0);
        for (Employee emp : EmployeeDao.queryList()) {
            Object[] row = {
                    emp.getId(),
                    emp.getFullName(),
                    emp.getPhoneNumber(),
                    emp.getEmail(),
                    emp.getGender() == 1 ? "Nam" : "Nữ",
                    emp.getHireDate() != null ? emp.getHireDate().toString() : ""
            };
            tableModel.addRow(row);
        }
    }

    @Override
    protected void handleSearch() {
        JTextField[] searchFields = searchPanel.getSearchFields();
        String fullName = searchFields[0].getText().trim();
        String phone = searchFields[1].getText().trim();

        // Gọi DAO
        List<Employee> results = EmployeeDao.search(fullName, phone);

        // Cập nhật bảng
        tableModel.setRowCount(0);
        for (Employee emp : results) {
            Object[] row = {
                    emp.getId(),
                    emp.getFullName(),
                    emp.getPhoneNumber(),
                    emp.getEmail(),
                    emp.getGender() == 1 ? "Nam" : "Nữ",
                    emp.getHireDate() != null ? emp.getHireDate().toString() : ""
            };
            tableModel.addRow(row);
        }
    }


    @Override
    protected void handleDelete(int selectedRow) {
        int employeeId = (int) table.getValueAt(selectedRow, 0);
        try {
            boolean success = EmployeeDao.delete(employeeId);
            if (success) {
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Không thể xóa nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
