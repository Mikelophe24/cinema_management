package Main;

import javax.swing.SwingUtilities;

import View.HomeUseView;
//import View.LoginView;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new HomeUseView().setVisible(true));
	}
}