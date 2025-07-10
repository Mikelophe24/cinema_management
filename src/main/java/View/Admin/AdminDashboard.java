package View.Admin;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

//import View.Admin.panels.AccountsPanel;
//import View.Admin.panels.CustomersPanel;
//import View.Admin.panels.InvoicesPanel;
//// Import các panel classes
//import View.Admin.panels.MoviesPanel;
import View.Admin.panels.RoomsPanel;
//import View.Admin.panels.SchedulePanel;
import View.Admin.panels.StaffPanel;

public class AdminDashboard extends JFrame {

	private JPanel contentPanel;
	private CardLayout cardLayout;
	private JMenu selectedMenu = null;

	public AdminDashboard() {
		setTitle("🎬 Cinema Admin Dashboard");
		setSize(1000, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Giao diện sáng, hiện đại
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		UIManager.put("MenuBar.background", Color.WHITE);
		UIManager.put("Menu.foreground", Color.DARK_GRAY);
		UIManager.put("Menu.font", new Font("Segoe UI", Font.BOLD, 15));
		UIManager.put("MenuItem.font", new Font("Segoe UI", Font.PLAIN, 14));

		// Navbar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		menuBar.setBackground(Color.WHITE);

		JMenu menuMovie = createMenu("Quản lý phim", "movies");
		JMenu menuRoom = createMenu("Quản lý phòng", "rooms");
		JMenu menuSchedule = createMenu("Quản lý lịch", "schedule");
		JMenu menuAccount = createMenu("Tài khoản", "accounts");
		JMenu menuStaff = createMenu("Nhân viên", "staff");
		JMenu menuCustomer = createMenu("️Khách hàng", "customers");
		JMenu menuInvoice = createMenu("Hóa đơn", "invoices");

		// Đảm bảo không lặp menu, đúng thứ tự
		menuBar.add(menuMovie);
		menuBar.add(menuRoom);
		menuBar.add(menuSchedule);
		menuBar.add(menuAccount);
		menuBar.add(menuStaff);
		menuBar.add(menuCustomer);
		menuBar.add(menuInvoice);

		setJMenuBar(menuBar);

		// Content Panel - không gradient
		cardLayout = new CardLayout();
		contentPanel = new JPanel(cardLayout);
		contentPanel.setBackground(Color.WHITE);

		contentPanel.add(new RoomsPanel(), "rooms");
		contentPanel.add(new StaffPanel(), "staff"); // ✅ THÊM DÒNG NÀY
		add(contentPanel);

		// Mặc định hiển thị trang phim và active menu phim
		cardLayout.show(contentPanel, "movies");
		setMenuActive(menuMovie);
	}

	private JMenu createMenu(String text, String panelName) {
		JMenu menu = new JMenu(text);
		menu.setForeground(new Color(60, 60, 60));
		menu.setFont(new Font("Segoe UI", Font.BOLD, 15));

		// Chỉ thêm hiệu ứng click
		menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Chuyển trang và cập nhật trạng thái menu
				cardLayout.show(contentPanel, panelName);
				setMenuActive(menu);
			}
		});

		return menu;
	}

	private void setMenuActive(JMenu activeMenu) {
		// Reset tất cả menu về trạng thái bình thường
		for (Component comp : getJMenuBar().getComponents()) {
			if (comp instanceof JMenu) {
				JMenu menu = (JMenu) comp;
				menu.setForeground(new Color(60, 60, 60));
			}
		}

		// Set menu được chọn thành màu active
		if (activeMenu != null) {
			activeMenu.setForeground(new Color(0, 123, 255));
			selectedMenu = activeMenu;
		}
	}

	private JPanel createPlaceholderPanel(String title) {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(Color.WHITE);
		JLabel label = new JLabel(title);
		label.setFont(new Font("Segoe UI", Font.BOLD, 26));
		label.setForeground(new Color(30, 144, 255));
		panel.add(label);
		return panel;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
	}
}
