package View;

import Controller.AuthController;
import Model.User;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginView() {
        setTitle("Đăng nhập hệ thống");
        setSize(350, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        JLabel lblPassword = new JLabel("Mật khẩu:");

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        btnLogin = new JButton("Đăng nhập");

        btnLogin.addActionListener(e -> handleLogin());

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(lblUsername);
        panel.add(txtUsername);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(new JLabel());
        panel.add(btnLogin);

        add(panel);
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
            return;
        }

        AuthController auth = new AuthController();
        User user = auth.login(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            dispose(); // đóng cửa sổ đăng nhập

            if (user.getRole().equalsIgnoreCase("admin")) {
                new MainAdminView(user).setVisible(true);
            } else {
                new MainUserView(user).setVisible(true); // giả định bạn có giao diện người dùng thường
            }

        } else {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng.");
        }
    }
}
