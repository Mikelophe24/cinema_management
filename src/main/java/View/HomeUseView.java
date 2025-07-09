//package View;
//
//import Model.User;
//import javax.swing.*;
//import java.awt.*;
//
//@SuppressWarnings("unused")
//public class HomeUseView extends JFrame {
//    private static final long serialVersionUID = 1L;
//    private JLabel bannerLabel;
//    private JLabel titleLabel;
//    private JLabel priceLabel;
//    private JLabel stickerLabel;
//    private JLabel dateLabel;
//    private User user;
//
//    public HomeUseView(User user) {
//        this(); // gọi constructor mặc định để setup giao diện
//        this.user = user;
//    }
//
//    public HomeUseView() {
//        setTitle("Beta Cinemas - Trang Chủ");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        // Lấy kích thước màn hình
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        setSize(screenSize.width, screenSize.height);
//        setLocationRelativeTo(null);
//        setLayout(new BorderLayout());
//
//     // Thanh menu trên cùng
//        JPanel menuPanel = new JPanel(new BorderLayout());
//        menuPanel.setBackground(Color.WHITE);
//        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//        // Logo
//        JLabel logoLabel = new JLabel("<html><span style='color:#2196F3;font-size:28px;font-weight:bold;'>BETA</span> <span style='color:#666;font-size:24px;'>- CINEMAS</span></html>");
//        logoLabel.setIconTextGap(10);
//        menuPanel.add(logoLabel, BorderLayout.WEST);
//
//        // Menu items
//        String[] menuItems = {"LỊCH CHIẾU THEO RẠP", "PHIM", "RẠP", "GIÁ VÉ", "TIN MỚI VÀ ƯU ĐÃI", "NHƯỢNG QUYỀN", "THÀNH VIÊN"};
//        JPanel navPanel = new JPanel();
//        navPanel.setBackground(Color.WHITE);
//        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.X_AXIS));
//
//        // Thêm khoảng trống bên trái để đẩy menu vào giữa
//        navPanel.add(Box.createHorizontalGlue());
//
//        for (String item : menuItems) {
//            JLabel navLabel = new JLabel(item);
//            navLabel.setFont(new Font("Arial", Font.BOLD, 15));
//            navLabel.setForeground(Color.BLACK);
//            navPanel.add(navLabel);
//            navPanel.add(Box.createRigidArea(new Dimension(35, 0))); // Khoảng cách giữa các menu items
//        }
//
//        // Thêm khoảng trống bên phải để cân bằng
//        navPanel.add(Box.createHorizontalGlue());
//
//        menuPanel.add(navPanel, BorderLayout.CENTER);
//
//        add(menuPanel, BorderLayout.NORTH);
//
//        // Panel hiển thị banner
//        JPanel bannerPanel = new JPanel(new BorderLayout());
//        bannerPanel.setBackground(Color.WHITE);
//        bannerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // bỏ padding
//
//        // Label hiển thị ảnh banner
//        bannerLabel = new JLabel();
//        bannerLabel.setHorizontalAlignment(JLabel.CENTER);
//        bannerLabel.setVerticalAlignment(JLabel.TOP);
//        bannerPanel.add(bannerLabel, BorderLayout.CENTER);
//
//        add(bannerPanel, BorderLayout.CENTER);
//
//        // Khởi tạo và hiển thị ảnh banner
//        setBannerImage("https://files.betacorp.vn/media/images/2025/06/10/1702x621-15-135304-100625-93.png");
//    }
//
//    private void setBannerImage(String url) {
//        try {
//            ImageIcon icon = new ImageIcon(new java.net.URL(url));
//            // Lấy kích thước frame hiện tại
//            int width = getWidth();
//            int height = (int)(width * 0.4); // Tỷ lệ chiều cao 40% so với chiều rộng
//            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
//            bannerLabel.setIcon(new ImageIcon(img));
//        } catch (Exception e) {
//            bannerLabel.setText("Không tải được ảnh");
//            e.printStackTrace();
//        }
//    }
//
//    private void setStickerImage(JLabel label, String url) {
//        try {
//            ImageIcon icon = new ImageIcon(new java.net.URL(url));
//            Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
//            label.setIcon(new ImageIcon(img));
//        } catch (Exception e) {
//            label.setText("");
//        }
//    }
//}
