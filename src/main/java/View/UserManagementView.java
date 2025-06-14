package View;

import Dao.UserDao;
import Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserManagementView extends JFrame {
    private JTable userTable;
    private JTextField txtUsername, txtPassword, txtSearch;
    private JComboBox<String> cboRole;
    private JButton btnAdd, btnEdit, btnDelete, btnSearch, btnReset;

    public UserManagementView() {
        setTitle("Quản lý tài khoản người dùng");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        loadUsers("");
    }

    private void initComponents() {
        JPanel panelMain = new JPanel(new BorderLayout());

        // Title
        JLabel lblTitle = new JLabel("Quản lý tài khoản", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        panelMain.add(lblTitle, BorderLayout.NORTH);

        // Table
        userTable = new JTable();
        panelMain.add(new JScrollPane(userTable), BorderLayout.CENTER);

        // Bottom Panel
        JPanel panelBottom = new JPanel(new GridLayout(2, 1));

        JPanel panelForm = new JPanel();
        txtUsername = new JTextField(10);
        txtPassword = new JTextField(10);
        cboRole = new JComboBox<>(new String[]{"user", "admin"});
        panelForm.add(new JLabel("Tên đăng nhập:"));
        panelForm.add(txtUsername);
        panelForm.add(new JLabel("Mật khẩu:"));
        panelForm.add(txtPassword);
        panelForm.add(new JLabel("Quyền:"));
        panelForm.add(cboRole);

        JPanel panelButtons = new JPanel();
        txtSearch = new JTextField(10);
        btnSearch = new JButton("Tìm");
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnReset = new JButton("Làm mới");

        panelButtons.add(new JLabel("Tìm tên:"));
        panelButtons.add(txtSearch);
        panelButtons.add(btnSearch);
        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        panelButtons.add(btnDelete);
        panelButtons.add(btnReset);

        panelBottom.add(panelForm);
        panelBottom.add(panelButtons);

        panelMain.add(panelBottom, BorderLayout.SOUTH);
        setContentPane(panelMain);

        btnSearch.addActionListener(e -> loadUsers(txtSearch.getText().trim()));

        btnAdd.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String role = cboRole.getSelectedItem().toString();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không được bỏ trống!");
                return;
            }

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole(role);

            if (new UserDao().addUser(user)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadUsers("");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            }
        });

        btnEdit.addActionListener(e -> {
            int row = userTable.getSelectedRow();
            if (row == -1) return;

            int id = Integer.parseInt(userTable.getValueAt(row, 0).toString());
            String password = txtPassword.getText();
            String role = cboRole.getSelectedItem().toString();
            String username = txtUsername.getText();

            User user = new User();
            user.setId(id);
            user.setPassword(password);
            user.setRole(role);
            user.setUsername(username);

            if (new UserDao().updateUser(user)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadUsers("");
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
            }
        });

        btnDelete.addActionListener(e -> {
            int row = userTable.getSelectedRow();
            if (row == -1) return;

            int id = Integer.parseInt(userTable.getValueAt(row, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (new UserDao().deleteUser(id)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadUsers("");
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                }
            }
        });
        

        btnReset.addActionListener(e -> {
            txtUsername.setText("");
            txtPassword.setText("");
            cboRole.setSelectedIndex(0);
            txtSearch.setText("");
            userTable.clearSelection();
            loadUsers("");
        });

        
        
        userTable.getSelectionModel().addListSelectionListener(e -> {
            int row = userTable.getSelectedRow();
            if (row >= 0) {
                txtUsername.setText(userTable.getValueAt(row, 1).toString());
                txtPassword.setText(userTable.getValueAt(row, 2).toString());
                cboRole.setSelectedItem(userTable.getValueAt(row, 3).toString());
            }
        });
    }

    private void loadUsers(String keyword) {
        List<User> users = keyword.isEmpty() ? new UserDao().getAllUsers() : new UserDao().searchUsers(keyword);
        String[] columns = {"ID", "Username", "Password", "Role"};
        String[][] data = new String[users.size()][4];

        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            data[i][0] = String.valueOf(u.getId());
            data[i][1] = u.getUsername();
            data[i][2] = u.getPassword();
            data[i][3] = u.getRole();
        }

        userTable.setModel(new DefaultTableModel(data, columns));
    }
}
