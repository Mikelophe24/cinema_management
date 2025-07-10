//package View.Admin.panels;
//
//import View.Admin.common.*;
//import javax.swing.*;
//import java.util.HashMap;
//import java.util.Map;
//
//public class AccountsPanel extends BasePanel {
//
//    public AccountsPanel() {
//        super("Quản lý tài khoản", "👤");
//    }
//
//    @Override
//    protected void createComponents() {
//        // Tạo search panel
//        String[] fieldLabels = {"Tên đăng nhập", "Email"};
//        String[] comboLabels = {"Quyền", "Trạng thái"};
//        String[][] comboOptions = {
//            {"Tất cả", "Admin", "User", "Manager"},
//            {"Tất cả", "Active", "Inactive", "Suspended"}
//        };
//
//        searchPanel = new SearchPanel(fieldLabels, comboLabels, comboOptions);
//
//        // Tạo table
//        String[] columns = {"ID", "Tên đăng nhập", "Email", "Quyền", "Trạng thái", "Ngày tạo"};
//        table = createStyledTable(columns);
//
//        // Tạo form panel
//        String[] formFieldLabels = {"Tên đăng nhập", "Email", "Mật khẩu"};
//        String[] formComboLabels = {"Quyền", "Trạng thái"};
//        String[][] formComboOptions = {
//            {"Admin", "User", "Manager"},
//            {"Active", "Inactive", "Suspended"}
//        };
//
//        formPanel = new FormPanel("Thông tin tài khoản", formFieldLabels, formComboLabels, formComboOptions);
//
//        // Tạo button panel
//        buttonPanel = new ButtonPanel(new String[]{});
//    }
//
//    @Override
//    protected void addSampleData() {
//        Object[][] sampleData = {
//            {1, "admin", "admin@cinema.com", "Admin", "Active", "2024-01-01"},
//            {2, "manager1", "manager1@cinema.com", "Manager", "Active", "2024-01-02"},
//            {3, "user1", "user1@cinema.com", "User", "Active", "2024-01-03"},
//            {4, "user2", "user2@cinema.com", "User", "Inactive", "2024-01-04"},
//            {5, "manager2", "manager2@cinema.com", "Manager", "Suspended", "2024-01-05"},
//            {6, "user3", "user3@cinema.com", "User", "Active", "2024-01-06"},
//            {7, "admin2", "admin2@cinema.com", "Admin", "Active", "2024-01-07"},
//            {8, "user4", "user4@cinema.com", "User", "Inactive", "2024-01-08"},
//            {9, "manager3", "manager3@cinema.com", "Manager", "Active", "2024-01-09"},
//            {10, "user5", "user5@cinema.com", "User", "Active", "2024-01-10"}
//        };
//
//        for (Object[] row : sampleData) {
//            tableModel.addRow(row);
//        }
//    }
//
//    @Override
//    protected void displaySelectedRowData() {
//        int selectedRow = table.getSelectedRow();
//        if (selectedRow != -1) {
//            formPanel.setTextFieldValue(0, table.getValueAt(selectedRow, 1).toString());
//            formPanel.setTextFieldValue(1, table.getValueAt(selectedRow, 2).toString());
//            formPanel.setTextFieldValue(2, "********");
//            formPanel.setComboBoxValue(0, table.getValueAt(selectedRow, 3).toString());
//            formPanel.setComboBoxValue(1, table.getValueAt(selectedRow, 4).toString());
//        }
//    }
//
//    @Override
//    protected void clearForm() {
//        formPanel.clearForm();
//    }
//
//    @Override
//    protected Map<String, String> getFormData() {
//        Map<String, String> data = new HashMap<>();
//        data.put("username", formPanel.getTextFieldValue(0));
//        data.put("email", formPanel.getTextFieldValue(1));
//        data.put("password", formPanel.getTextFieldValue(2));
//        data.put("role", formPanel.getComboBoxValue(0));
//        data.put("status", formPanel.getComboBoxValue(1));
//        return data;
//    }
//
//    @Override
//    protected void handleEdit(int selectedRow) {
//        System.out.println("Sửa tài khoản ở hàng: " + selectedRow);
//        System.out.println("Dữ liệu form: " + getFormData());
//    }
//
//    @Override
//    protected void handleAdd() {
//        System.out.println("Thêm tài khoản mới");
//        System.out.println("Dữ liệu form: " + getFormData());
//    }
//
//    @Override
//    protected void handleSearch() {
//        JTextField[] searchFields = searchPanel.getSearchFields();
//        JComboBox<String>[] searchCombos = searchPanel.getSearchCombos();
//
//        String username = searchFields[0].getText().trim();
//        String email = searchFields[1].getText().trim();
//        String role = searchCombos[0].getSelectedItem().toString();
//        String status = searchCombos[1].getSelectedItem().toString();
//
//        System.out.println("Tìm kiếm: " + username + ", " + email + ", " + role + ", " + status);
//    }
//}