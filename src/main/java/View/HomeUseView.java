package View;

import Model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import View.MovieSection.ComingSoonView;
import View.MovieSection.NowShowingView;
import View.MovieSection.SpecialScreeningView;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class HomeUseView extends JFrame {
    private static final long serialVersionUID = 1L;
    private JLabel[] bannerLabels;
    private int currentBannerIndex = 0;
    private Timer slideTimer;
    private final String[] bannerUrls = {
        "https://files.betacorp.vn/media/images/2025/06/10/1702x621-15-135304-100625-93.png",
        "https://files.betacorp.vn/media/images/2025/07/08/1702x621-17-111453-080725-79.png",
        "https://files.betacorp.vn/media/images/2025/06/26/1702-x-621-143544-260625-61.png", 
        "https://files.betacorp.vn/media/images/2025/06/23/art-online-ca-sua-1702-x-621-090026-230625-10.png",
        "https://files.betacorp.vn/media/images/2025/07/07/tai-sinh-khung-long-1702x621-1-104612-070725-67.png"
    };
    private int selectedTabIndex = 0;
    private final String[] tabTitles = {"PHIM SẮP CHIẾU", "PHIM ĐANG CHIẾU", "SUẤT CHIẾU ĐẶC BIỆT"};
    private JLabel[] tabLabels;
    private JPanel contentPanel;
    private ComingSoonView comingSoonView;
    private NowShowingView nowShowingView;
    private SpecialScreeningView specialScreeningView;
    private User user;
    private String selectedRap = "Hà Nội";

    public HomeUseView(User user) {
        this();
        this.user = user;
    }

    public HomeUseView() {
        setTitle("Beta Cinemas - Trang Chủ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tạo mainPanel chứa toàn bộ nội dung để scroll
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        // Menu trên cùng
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(Color.WHITE);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Logo + combobox chọn cụm rạp
        JPanel logoComboPanel = new JPanel();
        logoComboPanel.setOpaque(false);
        logoComboPanel.setLayout(new BoxLayout(logoComboPanel, BoxLayout.X_AXIS));
        JLabel logoLabel = new JLabel("<html><span style='color:#2196F3;font-size:28px;font-weight:bold;'>BETA</span> <span style='color:#666;font-size:24px;'>- CINEMAS</span></html>");
        logoLabel.setIconTextGap(10);
        logoLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        logoComboPanel.add(logoLabel);
        logoComboPanel.add(Box.createHorizontalStrut(16));
        String[] rapList = {"Hà Nội", "TP. Hồ Chí Minh", "An Giang", "Bắc Giang", "Đồng Nai", "Khánh Hòa", "Thái Nguyên", "Thanh Hóa", "Vĩnh Phúc", "Lào Cai"};
        JComboBox<String> rapCombo = new JComboBox<>(rapList);
        rapCombo.setMaximumSize(new Dimension(180, 32));
        rapCombo.setFont(new Font("Arial", Font.BOLD, 15));
        rapCombo.setAlignmentY(Component.CENTER_ALIGNMENT);
        rapCombo.setFocusable(false);
        rapCombo.setSelectedItem(selectedRap);
        logoComboPanel.add(rapCombo);
        logoComboPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        menuPanel.add(logoComboPanel, BorderLayout.WEST);

        // Map tỉnh -> danh sách rạp
        Map<String, String[]> rapMap = new HashMap<>();
        rapMap.put("Hà Nội", new String[]{"Beta Thanh Xuân", "Beta Mỹ Đình", "Beta Đan Phượng", "Beta Giải Phóng", "Beta Xuân Thủy", "Beta Tây Sơn"});
        rapMap.put("TP. Hồ Chí Minh", new String[]{"Beta Quang Trung", "Beta Ung Văn Khiêm", "Beta Empire Bình Dương", "Beta Hồ Tràm", "Beta Tân Uyên"});
        rapMap.put("An Giang", new String[]{"Beta TRMall Phú Quốc"});
        rapMap.put("Bắc Giang", new String[]{"Beta Bắc Giang"});
        rapMap.put("Đồng Nai", new String[]{"Beta Biên Hòa", "Beta Long Khánh"});
        rapMap.put("Khánh Hòa", new String[]{"Beta Nha Trang"});
        rapMap.put("Thái Nguyên", new String[]{"Beta Thái Nguyên"});
        rapMap.put("Thanh Hóa", new String[]{"Beta Thanh Hóa"});
        rapMap.put("Vĩnh Phúc", new String[]{"Beta Vĩnh Yên"});
        rapMap.put("Lào Cai", new String[]{"Beta Lào Cai"});

        // Popup menu rạp
        JPopupMenu rapPopup = new JPopupMenu();
        rapCombo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
                // Không làm gì
            }
            @Override
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {
                rapPopup.setVisible(false);
            }
            @Override
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {
                rapPopup.setVisible(false);
            }
        });
        rapCombo.addActionListener(evt -> {
            int idx = rapCombo.getSelectedIndex();
            if (idx >= 0) {
                String province = (String) rapCombo.getSelectedItem();
                String[] cinemas = rapMap.getOrDefault(province, new String[]{});
                rapPopup.removeAll();
                for (String r : cinemas) {
                    JMenuItem item = new JMenuItem(r);
                    item.setFont(new Font("Arial", Font.PLAIN, 14));
                    item.addActionListener(ev -> {
                        // Khi chọn rạp, đổi combobox thành tên rạp, reset trang
                        rapCombo.setSelectedItem(r);
                        selectedRap = r;
                        SwingUtilities.getWindowAncestor(rapCombo).dispose();
                        new HomeUseView(user).setVisible(true);
                    });
                    rapPopup.add(item);
                }
                if (cinemas.length > 0) {
                    SwingUtilities.invokeLater(() -> {
                        try {
                            Rectangle cbBounds = rapCombo.getBounds();
                            Point cbLoc = rapCombo.getLocationOnScreen();
                            rapPopup.show(rapCombo, cbBounds.width, 0);
                        } catch (Exception ex) {
                            rapPopup.show(rapCombo, 0, rapCombo.getHeight());
                        }
                    });
                } else {
                    rapPopup.setVisible(false);
                }
            }
        });

        String[] menuItems = {"LỊCH CHIẾU THEO RẠP", "PHIM", "RẠP", "GIÁ VÉ", "TIN MỚI VÀ ƯU ĐÃI", "NHƯỢNG QUYỀN", "THÀNH VIÊN"};
        JPanel navPanel = new JPanel();
        navPanel.setBackground(Color.WHITE);
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.X_AXIS));
        navPanel.add(Box.createHorizontalGlue());

        for (String item : menuItems) {
            JLabel navLabel = new JLabel(item);
            navLabel.setFont(new Font("Arial", Font.BOLD, 15));
            navLabel.setForeground(Color.BLACK);
            navPanel.add(navLabel);
            navPanel.add(Box.createRigidArea(new Dimension(35, 0)));
        }

        navPanel.add(Box.createHorizontalGlue());
        menuPanel.add(navPanel, BorderLayout.CENTER);
        mainPanel.add(menuPanel);

        // Banner
        JPanel bannerPanel = new JPanel(null);
        bannerPanel.setBackground(Color.WHITE);
        bannerPanel.setPreferredSize(new Dimension(getWidth(), (int)(getWidth() * 0.4)));
        mainPanel.add(bannerPanel);

        // Padding giữa banner và tab
        mainPanel.add(Box.createVerticalStrut(24));

        // Tabs + nội dung
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        JPanel tabPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (tabLabels != null && tabLabels[selectedTabIndex] != null) {
                    JLabel selected = tabLabels[selectedTabIndex];
                    int underlineY = selected.getY() + selected.getHeight();
                    int underlineX = selected.getX();
                    int underlineW = selected.getWidth();
                    g.setColor(new Color(33, 150, 243));
                    g.fillRect(underlineX, underlineY - 2, underlineW, 4);
                }
                g.setColor(new Color(200, 200, 200));
                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            }
        };
        tabPanel.setLayout(null);
        tabPanel.setBackground(Color.WHITE);
        tabPanel.setPreferredSize(new Dimension(getWidth(), 55));
        tabPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        centerPanel.add(tabPanel);

        // Padding giữa tab và content
        centerPanel.add(Box.createVerticalStrut(24));

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        centerPanel.add(contentPanel);

        mainPanel.add(centerPanel);

        // Add scroll pane chứa toàn bộ mainPanel
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        // Tabs setup
        tabLabels = new JLabel[tabTitles.length];
        int totalTabWidth = 0, padding = 30;
        for (int i = 0; i < tabTitles.length; i++) {
            JLabel temp = new JLabel(tabTitles[i]);
            temp.setFont(new Font("Arial", Font.BOLD, 28));
            totalTabWidth += temp.getPreferredSize().width + 10;
            if (i < tabTitles.length - 1) totalTabWidth += padding;
        }

        int panelWidth = getWidth();
        int x = (panelWidth - totalTabWidth) / 2;
        int y = 10;

        for (int i = 0; i < tabTitles.length; i++) {
            JLabel tab = new JLabel(tabTitles[i]);
            tab.setFont(new Font("Arial", Font.BOLD, 28));
            tab.setCursor(new Cursor(Cursor.HAND_CURSOR));
            tab.setOpaque(false);
            tab.setBounds(x, y, tab.getPreferredSize().width + 10, 35);
            tab.setForeground(i == selectedTabIndex ? new Color(33, 150, 243) : Color.BLACK);

            final int idx = i;
            tab.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    selectedTabIndex = idx;
                    for (int j = 0; j < tabLabels.length; j++) {
                        tabLabels[j].setForeground(j == selectedTabIndex ? new Color(33, 150, 243) : Color.BLACK);
                    }
                    tabPanel.repaint();
                    if (selectedTabIndex == 0) {
                        if (comingSoonView == null) comingSoonView = new ComingSoonView();
                        setContentPanel(comingSoonView);
                    } else if (selectedTabIndex == 1) {
                        if (nowShowingView == null) nowShowingView = new NowShowingView();
                        setContentPanel(nowShowingView);
                    } else if (selectedTabIndex == 2) {
                        if (specialScreeningView == null) specialScreeningView = new SpecialScreeningView();
                        setContentPanel(specialScreeningView);
                    } else {
                        setContentPanel(null);
                    }
                }
            });

            tabPanel.add(tab);
            tabLabels[i] = tab;
            x += tab.getPreferredSize().width + 10 + padding;
        }

        // Resize tab layout khi thay đổi cửa sổ
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int panelWidth = getWidth();
                int totalTabWidth = 0, padding = 30;
                for (JLabel label : tabLabels) {
                    totalTabWidth += label.getPreferredSize().width + 10;
                }
                totalTabWidth += (tabLabels.length - 1) * padding;

                int x = (panelWidth - totalTabWidth) / 2;
                for (JLabel label : tabLabels) {
                    label.setBounds(x, y, label.getPreferredSize().width + 10, 35);
                    x += label.getPreferredSize().width + 10 + padding;
                }
                tabPanel.repaint();
            }
        });

        // Banners setup
        bannerLabels = new JLabel[bannerUrls.length];
        for (int i = 0; i < bannerUrls.length; i++) {
            bannerLabels[i] = new JLabel();
            bannerLabels[i].setHorizontalAlignment(JLabel.CENTER);
            bannerLabels[i].setVerticalAlignment(JLabel.TOP);
            bannerLabels[i].setBounds(getWidth(), 0, getWidth(), (int)(getWidth() * 0.4));
            bannerPanel.add(bannerLabels[i]);
        }

        setBannerImage(0);

        slideTimer = new Timer(4000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                slideToNextBanner();
            }
        });
        slideTimer.start();

        // Mặc định vào tab đầu
        if (selectedTabIndex == 0) {
            comingSoonView = new ComingSoonView();
            setContentPanel(comingSoonView);
        }
    }

    private void setBannerImage(int index) {
        try {
            ImageIcon icon = new ImageIcon(new java.net.URL(bannerUrls[index]));
            int width = getWidth();
            int height = (int)(width * 0.4);
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            bannerLabels[index].setIcon(new ImageIcon(img));
            bannerLabels[index].setBounds(0, 0, width, height);

            for (int i = 0; i < bannerLabels.length; i++) {
                if (i != index) {
                    bannerLabels[i].setBounds(getWidth(), 0, width, height);
                }
            }
        } catch (Exception e) {
            bannerLabels[index].setText("Không tải được ảnh");
        }
    }

    private void slideToNextBanner() {
        int width = getWidth();
        int height = (int)(width * 0.4);
        int nextIndex = (currentBannerIndex + 1) % bannerLabels.length;
        JLabel current = bannerLabels[currentBannerIndex];
        JLabel next = bannerLabels[nextIndex];

        next.setBounds(width, 0, width, height);
        if (next.getIcon() == null) {
            try {
                ImageIcon icon = new ImageIcon(new java.net.URL(bannerUrls[nextIndex]));
                Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                next.setIcon(new ImageIcon(img));
            } catch (Exception e) {
                next.setText("Không tải được ảnh");
            }
        }

        Timer animationTimer = new Timer(10, null);
        animationTimer.addActionListener(new ActionListener() {
            int xCurrent = 0;
            int xNext = width;

            @Override
            public void actionPerformed(ActionEvent e) {
                xCurrent -= 40;
                xNext -= 40;
                if (xNext <= 0) {
                    current.setBounds(-width, 0, width, height);
                    next.setBounds(0, 0, width, height);
                    animationTimer.stop();
                    currentBannerIndex = nextIndex;
                } else {
                    current.setBounds(xCurrent, 0, width, height);
                    next.setBounds(xNext, 0, width, height);
                }
            }
        });
        animationTimer.start();
    }

    private void setContentPanel(JPanel panel) {
        contentPanel.removeAll();
        if (panel != null) contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
