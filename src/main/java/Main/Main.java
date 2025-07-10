package Main;

import javax.swing.SwingUtilities;

import View.Admin.AdminDashboard;

public class Main {
	public static void main(String[] args) {
//		SwingUtilities.invokeLater(() -> new HomeUseView(new Account(1, "1", "1", AccountEnum.Role.CUSTOMER,
//				AccountEnum.Status.ACTIVE, "k", "sssss", LocalDateTime.now(), LocalDateTime.now())).setVisible(true));
//		SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
		SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
	}
}
