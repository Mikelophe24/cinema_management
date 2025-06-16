package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class RegisterUseView extends JFrame {
	private static final long serialVersionUID = -4581810238220354257L;
	private JTextField txtFullName;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JPasswordField txtConfirmPassword;
	private JTextField txtEmail;
	private JTextField txtPhoneNumber;
	private JButton btnRegister;

	private java.util.Map<JTextField, JLabel> errorLabels = new java.util.HashMap<>();

	private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
	private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
	private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
	private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
	private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

	public RegisterUseView() {
		setTitle("Đăng ký tài khoản");
		setSize(450, 750);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		getContentPane().setBackground(BACKGROUND_COLOR);
		initComponents();
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(RegisterUseView.this, // Parent component
						"Bạn có muốn thoát khỏi trang đăng ký không?", // Message
						"Xác nhận thoát", // Title
						JOptionPane.YES_NO_OPTION, // Option type
						JOptionPane.QUESTION_MESSAGE); // Message type

				if (result == JOptionPane.YES_OPTION) {
					dispose(); // Close current window
					new LoginView().setVisible(true); // Open LoginView
				}
			}
		});
	}

	private void initComponents() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBackground(BACKGROUND_COLOR);
		mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

		JLabel lblTitle = new JLabel("ĐĂNG KÝ", SwingConstants.CENTER);
		lblTitle.setFont(TITLE_FONT);
		lblTitle.setForeground(PRIMARY_COLOR);
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(lblTitle);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

		mainPanel.add(createInputPanel("Họ và Tên:", txtFullName = new JTextField()));
		mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		mainPanel.add(createInputPanel("Tên đăng nhập:", txtUsername = new JTextField()));
		mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		mainPanel.add(createInputPanel("Mật khẩu:", txtPassword = new JPasswordField()));
		mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		mainPanel.add(createInputPanel("Nhập lại mật khẩu:", txtConfirmPassword = new JPasswordField()));
		mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		mainPanel.add(createInputPanel("Email:", txtEmail = new JTextField()));
		mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

		mainPanel.add(createInputPanel("Số điện thoại:", txtPhoneNumber = new JTextField()));
		mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

		btnRegister = new JButton("ĐĂNG KÝ");
		styleButton(btnRegister);
		btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(btnRegister);

		btnRegister.addActionListener(e -> handleRegister());

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

		textField.setFont(LABEL_FONT);
		textField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
				BorderFactory.createEmptyBorder(5, 10, 5, 10)));
		textField.setMaximumSize(new Dimension(300, 35));
		textField.setPreferredSize(new Dimension(300, 35));
		panel.add(textField);

		JLabel errorLabel = new JLabel("Lỗi!"); // Generic error label
		errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		errorLabel.setForeground(Color.RED);
		errorLabel.setVisible(false);
		errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(errorLabel);

		errorLabels.put(textField, errorLabel);

		// Add focus listener for all text fields
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (textField == txtPassword) {
					validatePasswordStrictness(txtPassword);
				} else if (textField == txtConfirmPassword) {
					validateConfirmPassword();
				} else if (textField == txtEmail) {
					validateEmailFormat(txtEmail);
				} else if (textField == txtPhoneNumber) {
					validatePhoneNumberFormat(txtPhoneNumber);
				} else {
					if (textField.getText().trim().isEmpty()) {
						errorLabel.setText("Chưa nhập thông tin này!");
						errorLabel.setVisible(true);
						textField.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
					} else {
						errorLabel.setVisible(false);
						textField.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
					}
				}
			}
		});

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

	// New method for password strictness validation
	private boolean validatePasswordStrictness(JPasswordField passwordField) {
		JLabel errorLabel = errorLabels.get(passwordField);
		String password = new String(passwordField.getPassword());
		String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\[\\]{};':\"\\|,.<>/?]).{6,}$";
		boolean isValid = true;

		if (password.trim().isEmpty()) {
			errorLabel.setText("Chưa nhập thông tin này!");
			errorLabel.setVisible(true);
			passwordField.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			isValid = false;
		} else if (!password.matches(passwordRegex)) {
			errorLabel.setText("Mật khẩu: 6+ ký tự, 1 hoa, 1 số, 1 đặc biệt.");
			errorLabel.setVisible(true);
			passwordField.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			isValid = false;
		} else {
			errorLabel.setVisible(false);
			passwordField.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
		}
		return isValid;
	}

	// New method for confirm password validation
	private boolean validateConfirmPassword() {
		String password = new String(txtPassword.getPassword());
		String confirmPassword = new String(txtConfirmPassword.getPassword());
		JLabel errorLabel = errorLabels.get(txtConfirmPassword);
		boolean isValid = true;

		if (confirmPassword.trim().isEmpty()) {
			errorLabel.setText("Chưa nhập thông tin này!");
			errorLabel.setVisible(true);
			txtConfirmPassword.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			isValid = false;
		} else if (!password.equals(confirmPassword)) {
			errorLabel.setText("Mật khẩu không khớp!");
			errorLabel.setVisible(true);
			txtConfirmPassword.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			isValid = false;
		} else {
			errorLabel.setVisible(false);
			txtConfirmPassword.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
		}
		return isValid;
	}

	// New method for email format validation
	private boolean validateEmailFormat(JTextField emailField) {
		JLabel errorLabel = errorLabels.get(emailField);
		String email = emailField.getText().trim();
		// Regex for email validation (a simple one, can be more complex if needed)
		String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
		boolean isValid = true;

		if (email.isEmpty()) {
			errorLabel.setText("Chưa nhập thông tin này!");
			errorLabel.setVisible(true);
			emailField.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			isValid = false;
		} else if (!email.matches(emailRegex)) {
			errorLabel.setText("Email không đúng định dạng!");
			errorLabel.setVisible(true);
			emailField.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			isValid = false;
		} else {
			errorLabel.setVisible(false);
			emailField.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
		}
		return isValid;
	}

	// New method for phone number format validation
	private boolean validatePhoneNumberFormat(JTextField phoneField) {
		JLabel errorLabel = errorLabels.get(phoneField);
		String phoneNumber = phoneField.getText().trim();
		// Regex: starts with 0, 9 to 11 digits total
		String phoneRegex = "^0\\d{8,10}$";
		boolean isValid = true;

		if (phoneNumber.isEmpty()) {
			errorLabel.setText("Chưa nhập thông tin này!");
			errorLabel.setVisible(true);
			phoneField.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			isValid = false;
		} else if (!phoneNumber.matches(phoneRegex)) {
			errorLabel.setText("SĐT phải bắt đầu bằng 0 và có 9-11 chữ số.");
			errorLabel.setVisible(true);
			phoneField.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			isValid = false;
		} else {
			errorLabel.setVisible(false);
			phoneField.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
		}
		return isValid;
	}

	private void handleRegister() {
		boolean allValid = true;

		// Reset all error labels and borders first
		for (JTextField field : errorLabels.keySet()) {
			errorLabels.get(field).setVisible(false);
			field.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
		}

		// Validate all text fields for emptiness
		JTextField[] fields = { txtFullName, txtUsername };
		for (JTextField field : fields) {
			if (field.getText().trim().isEmpty()) {
				errorLabels.get(field).setText("Chưa nhập thông tin này!");
				errorLabels.get(field).setVisible(true);
				field.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
				allValid = false;
			}
		}

		// Validate password strictness and confirm password
		boolean passwordValid = validatePasswordStrictness(txtPassword);
		boolean confirmPasswordValid = validateConfirmPassword();
		boolean emailValid = validateEmailFormat(txtEmail);
		boolean phoneNumberValid = validatePhoneNumberFormat(txtPhoneNumber); // Validate phone number

		if (!passwordValid || !confirmPasswordValid || !emailValid || !phoneNumberValid) {
			allValid = false;
		}

		if (allValid) {
			JOptionPane.showMessageDialog(this, "Đăng ký thành công! Vui lòng đăng nhập.", "Đăng ký thành công",
					JOptionPane.INFORMATION_MESSAGE);
			dispose(); // Close registration window
			new LoginView().setVisible(true); // Open LoginView
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ và chính xác thông tin.", "Lỗi đăng ký",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new RegisterUseView().setVisible(true));
	}
}
