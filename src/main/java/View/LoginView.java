package View;

import Dao.UserDao;
import Model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    public LoginView() {
        setTitle("Đăng nhập hệ thống");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND_COLOR);
        initComponents();
    }

    private void initComponents() {
        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Title
        JLabel lblTitle = new JLabel("ĐĂNG NHẬP", SwingConstants.CENTER);
        lblTitle.setFont(TITLE_FONT);
        lblTitle.setForeground(PRIMARY_COLOR);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Username panel
        JPanel usernamePanel = createInputPanel("Tên đăng nhập:", txtUsername = new JTextField());
        mainPanel.add(usernamePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        

        // Password panel
        JPanel passwordPanel = createInputPanel("Mật khẩu:", txtPassword = new JPasswordField());
        mainPanel.add(passwordPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Login button
        btnLogin = new JButton("ĐĂNG NHẬP");
        styleButton(btnLogin);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(btnLogin);

        // Add registration link
        JLabel lblRegister = new JLabel(
                "<html>Chưa có tài khoản? Vui lòng <font color=\"#2980B9\"><b>Đăng ký</b></font></html>",
                SwingConstants.CENTER);
        lblRegister.setFont(LABEL_FONT);
        lblRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new RegisterUseView().setVisible(true);
            }
        });
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(lblRegister);

        // Add event listeners
        txtUsername.addActionListener(e -> txtPassword.requestFocusInWindow());
        txtPassword.addActionListener(e -> handleLogin());
        btnLogin.addActionListener(e -> handleLogin());

        add(mainPanel);
    }

    private JPanel createInputPanel(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        label.setForeground(PRIMARY_COLOR);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        textField.setMaximumSize(new Dimension(300, 35));
        textField.setPreferredSize(new Dimension(300, 35));
        textField.setFont(LABEL_FONT);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.add(textField);

        return panel;
    }

    private void styleButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(300, 40));
        button.setPreferredSize(new Dimension(300, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu.");
            return;
        }

        UserDao userDao = new UserDao();
        User user = userDao.checkLogin(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            dispose(); // đóng cửa sổ đăng nhập

            if ("admin".equalsIgnoreCase(user.getRole())) {
                new MainAdminView(user).setVisible(true);
            } else {
                new HomeUseView(user).setVisible(true);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng.");
        }
//        Nguyễn Huy DŨng
    }
}
