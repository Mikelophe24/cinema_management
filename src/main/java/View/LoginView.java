package View;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Dao.AccountDao;
import Enum.AccountEnum;
import Model.Account;

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

		txtUsername.addActionListener(e -> txtPassword.requestFocusInWindow());

		txtPassword.addActionListener(e -> handleLogin());

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

		AccountDao accountDao = new AccountDao();
		Account account = accountDao.login(username, password);

		if (account != null) {
			JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
			dispose();
			System.out.println(account.getType());
			System.out.println(AccountEnum.Role.ADMIN);
			System.out.println(AccountEnum.Role.CUSTOMER);
			if (account.getType() == AccountEnum.Role.ADMIN) {
				new MainAdminView(account).setVisible(true);
			} else if (account.getType() == AccountEnum.Role.CUSTOMER) {
				new MainUserView(account).setVisible(true);
			} else if (account.getType() == AccountEnum.Role.EMPLOYEE) {
				//
			}
		} else {
			JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng.");
		}
	}
}
