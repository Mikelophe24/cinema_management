package View.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminDashboard extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;

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

        JMenu menuMovie = createMenu("Quản lý phim");
        JMenu menuRoom = createMenu("Quản lý phòng");
        JMenu menuAccount = createMenu("Tài khoản");
        JMenu menuStaff = createMenu("Nhân viên");
        JMenu menuCustomer = createMenu("️Khách hàng");
        JMenu menuInvoice = createMenu("Hóa đơn");

        menuBar.add(menuMovie);
        menuBar.add(menuRoom);
        menuBar.add(menuAccount);
        menuBar.add(menuStaff);
        menuBar.add(menuCustomer);
        menuBar.add(menuInvoice);
        setJMenuBar(menuBar);

        // Content Panel - không gradient
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        contentPanel.add(createPlaceholderPanel("🎞 Quản lý phim"), "movies");
        contentPanel.add(createPlaceholderPanel("🏢 Quản lý phòng"), "rooms");
        contentPanel.add(new AccountManagement(), "accounts");

        contentPanel.add(createPlaceholderPanel("🧑‍💼 Quản lý nhân viên"), "staff");
        contentPanel.add(createPlaceholderPanel("🧍‍♂️ Quản lý khách hàng"), "customers");
        contentPanel.add(createPlaceholderPanel("🧾 Quản lý hóa đơn"), "invoices");

        add(contentPanel);

        // Actions
        menuMovie.addMouseListener(new MenuClick("movies"));
        menuRoom.addMouseListener(new MenuClick("rooms"));
        menuAccount.addMouseListener(new MenuClick("accounts"));
        menuStaff.addMouseListener(new MenuClick("staff"));
        menuCustomer.addMouseListener(new MenuClick("customers"));
        menuInvoice.addMouseListener(new MenuClick("invoices"));
    }

    private JMenu createMenu(String text) {
        JMenu menu = new JMenu(text);
        menu.setForeground(new Color(60, 60, 60));
        menu.setFont(new Font("Segoe UI", Font.BOLD, 15));
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menu.setForeground(new Color(0, 123, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menu.setForeground(new Color(60, 60, 60));
            }
        });
        return menu;
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

    private class MenuClick extends MouseAdapter {
        private final String panelName;

        public MenuClick(String panelName) {
            this.panelName = panelName;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            cardLayout.show(contentPanel, panelName);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}
