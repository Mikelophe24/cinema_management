package View.Admin.panels;

import java.util.HashMap;
import java.util.Map;
import Dao.AccountDao;
import Model.Account;
import Enum.AccountEnum;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import View.Admin.common.BasePanel;
import View.Admin.common.ButtonPanel;
import View.Admin.common.FormPanel;
import View.Admin.common.SearchPanel;

public class AccountsPanel extends BasePanel {
    
    public AccountsPanel() {
        super("Quản lý tài khoản", "👤");
    }
    
    @Override
    protected void createComponents() {
        // Tạo search panel
        String[] fieldLabels = {"Tên đăng nhập"};
        String[] comboLabels = {"Quyền", "Trạng thái"};
        String[][] comboOptions = {
            {"Tất cả", "Admin", "User", "Manager"},
            {"Tất cả", "Active", "Inactive", "Suspended"}
        };
        
        searchPanel = new SearchPanel(fieldLabels, comboLabels, comboOptions);
        
        // Tạo table
        String[] columns = {
            "ID", "Tên đăng nhập", "Mật khẩu", "Quyền", "Trạng thái", "Ngày tạo",
            "Ngày kết thúc", "Tên người dùng", "Avatar"
        };
        table = createStyledTable(columns);
        
        
        // Tạo form panel
        String[] formFieldLabels = {
            "ID", "Tên đăng nhập", "Mật khẩu", "Ngày tạo", "Ngày kết thúc", "Tên người dùng", "Avatar"
        };
        String[] formComboLabels = {"Quyền", "Trạng thái"};
        String[][] formComboOptions = {
            {"Admin", "User", "Manager"},
            {"Active", "Inactive", "Suspended"}
        };
        formPanel = new FormPanel("Thông tin tài khoản", formFieldLabels, formComboLabels, formComboOptions);
        
        // Tạo button panel
        buttonPanel = new ButtonPanel(new String[]{});
    }
    
    @Override
    protected void addSampleData() {
        tableModel.setRowCount(0);

        List<Account> accounts = AccountDao.getAll();
        for (Account acc : accounts) {
            Object[] row = {
                acc.getAccountId(),
                acc.getUsername(),
                acc.getPassword(),
                acc.getRole() != null ? acc.getRole().getValue() : "",
                acc.getStatus() != null ? acc.getStatus().getValue() : "",
                acc.getCreatedAt() != null ? acc.getCreatedAt().toString() : "",
                acc.getUpdatedAt() != null ? acc.getUpdatedAt().toString() : "",
                acc.getDisplayName() != null ? acc.getDisplayName() : "",
                acc.getAvatar() != null ? acc.getAvatar() : ""
            };
            tableModel.addRow(row);
        }
    }
    
    @Override
    protected void displaySelectedRowData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            formPanel.setTextFieldValue(0, table.getValueAt(selectedRow, 0).toString()); // ID
            formPanel.setTextFieldValue(1, table.getValueAt(selectedRow, 1).toString()); // username
            formPanel.setTextFieldValue(2, table.getValueAt(selectedRow, 2).toString()); // password
            formPanel.setTextFieldValue(3, table.getValueAt(selectedRow, 5).toString()); // Ngày tạo
            formPanel.setTextFieldValue(4, table.getValueAt(selectedRow, 6).toString()); // Ngày kết thúc
            formPanel.setTextFieldValue(5, table.getValueAt(selectedRow, 7).toString()); // Tên người dùng
            formPanel.setTextFieldValue(6, table.getValueAt(selectedRow, 8).toString()); // Avatar
            formPanel.setComboBoxValue(0, table.getValueAt(selectedRow, 3).toString()); // Quyền
            formPanel.setComboBoxValue(1, table.getValueAt(selectedRow, 4).toString()); // Trạng thái
        }
    }
    
    @Override
    protected void clearForm() {
        formPanel.clearForm();
    }
    
    @Override
    protected Map<String, String> getFormData() {
        Map<String, String> data = new HashMap<>();
        data.put("id", formPanel.getTextFieldValue(0));
        data.put("username", formPanel.getTextFieldValue(1));
        data.put("password", formPanel.getTextFieldValue(2));
        data.put("createdAt", formPanel.getTextFieldValue(3));
        data.put("updatedAt", formPanel.getTextFieldValue(4));
        data.put("displayName", formPanel.getTextFieldValue(5));
        data.put("avatar", formPanel.getTextFieldValue(6));
        data.put("role", formPanel.getComboBoxValue(0));
        data.put("status", formPanel.getComboBoxValue(1));
        return data;
    }
    
    @Override
    protected void handleEdit(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để sửa!");
            return;
        }
        Map<String, String> data = getFormData();
        try {
            int id = Integer.parseInt(data.get("id"));
            Map<String, Object> updateFields = new HashMap<>();
            // Cập nhật tất cả các trường cho phép
            updateFields.put("password", data.get("password"));
            updateFields.put("role", data.get("role"));
            updateFields.put("status", data.get("status"));
            updateFields.put("display_name", data.get("displayName"));
            updateFields.put("avatar", data.get("avatar"));
            updateFields.put("username", data.get("username"));
            // Nếu muốn cập nhật thêm trường khác, cần sửa AccountDao cho phép

            boolean success = Dao.AccountDao.update(id, updateFields);
            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                addSampleData(); // Làm mới bảng
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    protected void handleAdd() {
        Map<String, String> data = getFormData();
        // Kiểm tra dữ liệu bắt buộc
        if (data.get("username").isEmpty() || data.get("password").isEmpty() ||
            data.get("role").isEmpty() || data.get("status").isEmpty() ||
            data.get("displayName").isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin tài khoản!");
            return;
        }
        try {
            // Lấy role và status đúng kiểu enum
            AccountEnum.Role role = AccountEnum.Role.fromValue(data.get("role"));
            AccountEnum.Status status = AccountEnum.Status.fromValue(data.get("status"));
            // Gọi register
            Account acc = AccountDao.register(
                data.get("username"),
                data.get("password"),
                data.get("displayName"),
                data.get("avatar"),
                role
            );
            if (acc != null) {
                // Sau khi thêm, cập nhật trạng thái nếu cần
                if (!acc.getStatus().equals(status)) {
                    java.util.HashMap<String, Object> updateFields = new java.util.HashMap<>();
                    updateFields.put("status", status.getValue());
                    AccountDao.update(acc.getAccountId(), updateFields);
                }
                JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!");
                addSampleData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm tài khoản thất bại!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    protected void handleSearch() {
        JTextField[] searchFields = searchPanel.getSearchFields();
        JComboBox<String>[] searchCombos = searchPanel.getSearchCombos();

        String username = searchFields[0].getText().trim();
        String role = searchCombos[0].getSelectedItem().toString();
        // Nếu có thêm combo trạng thái thì lấy searchCombos[1]

        // Xóa dữ liệu cũ trên bảng
        tableModel.setRowCount(0);

        // Lấy danh sách tài khoản từ DB
        List<Account> accounts = AccountDao.getAll();
        for (Account acc : accounts) {
            boolean match = true;
            // Lọc theo username bắt đầu bằng
            if (!username.isEmpty() && !acc.getUsername().toLowerCase().startsWith(username.toLowerCase())) {
                match = false;
            }
            // Lọc theo quyền
            if (!role.equals("Tất cả") && (acc.getRole() == null || !acc.getRole().getValue().equals(role))) {
                match = false;
            }
            if (match) {
                Object[] row = {
                    acc.getAccountId(),
                    acc.getUsername(),
                    acc.getPassword(),
                    acc.getRole() != null ? acc.getRole().getValue() : "",
                    acc.getStatus() != null ? acc.getStatus().getValue() : "",
                    acc.getCreatedAt() != null ? acc.getCreatedAt().toString() : "",
                    acc.getUpdatedAt() != null ? acc.getUpdatedAt().toString() : "",
                    acc.getDisplayName() != null ? acc.getDisplayName() : "",
                    acc.getAvatar() != null ? acc.getAvatar() : ""
                };
                tableModel.addRow(row);
            }
        }
    }

    @Override
    protected void handleDelete(int selectedRow) {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản để xóa!");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể xác định ID tài khoản!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa tài khoản này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = Dao.AccountDao.delete(id);
            if (success) {
                JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công!");
                addSampleData(); // Làm mới bảng
            } else {
                JOptionPane.showMessageDialog(this, "Xóa tài khoản thất bại!");
            }
        }
    }
} 
