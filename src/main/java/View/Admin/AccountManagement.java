package View.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AccountManagement extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public AccountManagement() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Tiêu đề
        JLabel titleLabel = new JLabel("👤 Danh sách tài khoản", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        add(titleLabel, BorderLayout.NORTH);

        // Cột tiêu đề
        String[] columnNames = {
            "ID", "Username", "Password", "Role", "Created At", "Updated At",
            "Status", "Display Name", "Avatar"
        };

        // Dữ liệu mẫu giống ảnh bạn gửi
        Object[][] sampleData = {
            {1, "KAdmin", "********", "admin", "2025-06-14 16:50", "2025-06-14 16:50", "active", "Quản trị viên K", "https://www.google.com/..."},
            {2, "KUser", "********", "customer", "2025-06-14 17:45", "2025-06-14 17:45", "active", "Khách hàng 1", "https://www.google.com/..."},
            {10, "test123", "********", "customer", "2025-07-08 19:24", "2025-07-08 19:24", "active", "Test User", "NULL"},
            {12, "test1", "123456", "customer", "2025-07-09 08:49", "2025-07-09 08:49", "active", "Test User", "NULL"}
        };

        // Tạo model và bảng
        tableModel = new DefaultTableModel(sampleData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // không cho sửa trực tiếp
            }
        };

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel chứa nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnAdd = new JButton("➕ Thêm");
        JButton btnEdit = new JButton("✏️ Sửa");
        JButton btnDelete = new JButton("🗑️ Xóa");

        btnAdd.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnEdit.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnDelete.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        add(buttonPanel, BorderLayout.SOUTH);

        // Sự kiện tạm thời
        btnAdd.addActionListener(e -> showAccountForm(false, null));
        // Nút sửa
        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để sửa");
                return;
            }

            Object[] rowData = new Object[tableModel.getColumnCount()];
            for (int i = 0; i < rowData.length; i++) {
                rowData[i] = tableModel.getValueAt(row, i);
            }

            showAccountForm(true, rowData);
            });
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa");
            } else {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bạn có chắc muốn xóa tài khoản này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    tableModel.removeRow(row);
                }
            }
        });
    }
        // Hiển thị form thêm hoặc sửa
    private void showAccountForm(boolean isEdit, Object[] existingData) {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), isEdit ? "Sửa tài khoản" : "Thêm tài khoản", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(10, 2, 5, 5));

        JTextField txtUsername = new JTextField();
        JTextField txtPassword = new JTextField();
        JTextField txtRole = new JTextField();
        JTextField txtDisplayName = new JTextField();
        JTextField txtStatus = new JTextField("active");
        JTextField txtAvatar = new JTextField("NULL");

        if (isEdit && existingData != null) {
            txtUsername.setText(existingData[1].toString());
            txtPassword.setText(existingData[2].toString());
            txtRole.setText(existingData[3].toString());
            txtDisplayName.setText(existingData[7].toString());
            txtStatus.setText(existingData[6].toString());
            txtAvatar.setText(existingData[8].toString());
        }

        dialog.add(new JLabel("Username:")); dialog.add(txtUsername);
        dialog.add(new JLabel("Password:")); dialog.add(txtPassword);
        dialog.add(new JLabel("Role:")); dialog.add(txtRole);
        dialog.add(new JLabel("Display Name:")); dialog.add(txtDisplayName);
        dialog.add(new JLabel("Status:")); dialog.add(txtStatus);
        dialog.add(new JLabel("Avatar URL:")); dialog.add(txtAvatar);

        JButton btnSubmit = new JButton(isEdit ? "Lưu sửa" : "Thêm mới");
        JButton btnCancel = new JButton("Hủy");

        dialog.add(btnSubmit); dialog.add(btnCancel);

        btnCancel.addActionListener(e -> dialog.dispose());

        btnSubmit.addActionListener(e -> {
            String[] row = {
                String.valueOf(isEdit ? existingData[0] : tableModel.getRowCount() + 1),
                txtUsername.getText(),
                txtPassword.getText(),
                txtRole.getText(),
                "2025-07-09", "2025-07-09",
                txtStatus.getText(),
                txtDisplayName.getText(),
                txtAvatar.getText()
            };

            if (isEdit) {
                int selectedRow = table.getSelectedRow();
                for (int i = 0; i < row.length; i++) {
                    tableModel.setValueAt(row[i], selectedRow, i);
                }
            } else {
                tableModel.addRow(row);
            }

            dialog.dispose();
        });

        dialog.setVisible(true);
    }

}
