package Main;

import java.time.LocalDateTime;

import javax.swing.SwingUtilities;

import Enum.AccountEnum;
import Model.Account;
import View.HomeUseView;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new HomeUseView(new Account(1, "1", "1", AccountEnum.Role.CUSTOMER,
				AccountEnum.Status.ACTIVE, "k", "sssss", LocalDateTime.now(), LocalDateTime.now())).setVisible(true));
//		SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
	}
}