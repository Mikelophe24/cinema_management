package View;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.awt.image.BufferedImage;

public class PaymentView extends JDialog {
    private JLabel totalLabel;
    private JLabel discountLabel;
    private JLabel payLabel;
    private int totalAmount;

    public PaymentView(String movieName, String cinemaName, String showDate, String showTime, String genre, String duration, String imageUrl, List<String> selectedSeats, int totalAmount) {
        super((Frame) null, "Thanh Toán", true);
        this.totalAmount = totalAmount;
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245)); // Light gray background for a modern look

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel, BorderLayout.CENTER);

        // Left: Payment info, combo, and payment methods
        JPanel leftContentPanel = new JPanel();
        leftContentPanel.setLayout(new BoxLayout(leftContentPanel, BoxLayout.Y_AXIS));
        leftContentPanel.setBackground(new Color(245, 245, 245));

        // Payment info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(33, 150, 243)), "THÔNG TIN THANH TOÁN", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(33, 150, 243)));
        infoPanel.add(Box.createVerticalStrut(15));

        // User info
        JPanel row1 = new JPanel(new GridLayout(1, 3, 20, 0));
        row1.setBackground(Color.WHITE);
        row1.add(createInfoField("Họ Tên:", ""));
        row1.add(createInfoField("Số điện thoại:", ""));
        row1.add(createInfoField("Email:", ""));
        infoPanel.add(row1);
        infoPanel.add(Box.createVerticalStrut(15));

        // Seat info
        JPanel row2 = new JPanel(new BorderLayout(10, 0));
        row2.setBackground(Color.WHITE);
        JLabel seatType = new JLabel("GHẾ:");
        seatType.setFont(new Font("Arial", Font.BOLD, 16));
        row2.add(seatType, BorderLayout.WEST);
        JLabel seatCalc = new JLabel(selectedSeats.size() + " x 50.000");
        seatCalc.setFont(new Font("Arial", Font.PLAIN, 16));
        row2.add(seatCalc, BorderLayout.CENTER);
        JLabel seatTotal = new JLabel("= " + totalAmount + " vnd");
        seatTotal.setFont(new Font("Arial", Font.BOLD, 16));
        row2.add(seatTotal, BorderLayout.EAST);
        infoPanel.add(row2);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(new JSeparator());

        // Combo section
        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.Y_AXIS));
        comboPanel.setBackground(Color.WHITE);
        comboPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(33, 150, 243)), "COMBO ƯU ĐÃI", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(33, 150, 243)));

        // Combo header
        JPanel header = new JPanel(new GridLayout(1, 4));
        header.setBackground(Color.WHITE);
        header.add(new JLabel("")); // Image placeholder
        JLabel tenCombo = new JLabel("Tên Combo");
        tenCombo.setFont(new Font("Arial", Font.BOLD, 16));
        header.add(tenCombo);
        JLabel moTa = new JLabel("Mô tả");
        moTa.setFont(new Font("Arial", Font.BOLD, 16));
        header.add(moTa);
        JLabel soLuong = new JLabel("Số lượng");
        soLuong.setFont(new Font("Arial", Font.BOLD, 16));
        header.add(soLuong);
        comboPanel.add(header);
        comboPanel.add(new JSeparator());

        // Combo list with prices
        int[] comboPrices = {95000, 56000, 28000, 46000}; // Prices for each combo
        comboPanel.add(createComboRowV2("Family Combo 69oz", "TIẾT KIỆM 95K!! Gồm: 2 Bắp (69oz) + 4 Nước có gaz (22oz) + 2 Snack Oishi (80g)", 0, "https://files.betacorp.vn/media/combopackage/2025/07/02/combo-online-05-134512-020725-51.png", comboPrices[0]));
        comboPanel.add(new JSeparator());
        comboPanel.add(createComboRowV2("Combo See Mê - Cầu Vồng", "TIẾT KIỆM 56K!!! Sở hữu ngay: 1 Ly Cầu Vồng kèm nước + 1 Bắp (69oz)", 0, "https://files.betacorp.vn/media/combopackage/2024/04/17/combo-online-19-100930-170424-86.png", comboPrices[1]));
        comboPanel.add(new JSeparator());
        comboPanel.add(createComboRowV2("Beta Combo 69oz", "TIẾT KIỆM 28K!!! Gồm: 1 Bắp (69oz) + 1 Nước có gaz (22oz)", 0, "https://files.betacorp.vn/media/combopackage/2025/07/02/combo-online-03-134211-020725-96.png", comboPrices[2]));
        comboPanel.add(new JSeparator());
        comboPanel.add(createComboRowV2("Sweet Combo 69oz", "TIẾT KIỆM 46K!!! Gồm: 1 Bắp (69oz) + 2 Nước có gaz (22oz)", 0, "https://files.betacorp.vn/media/combopackage/2025/07/02/combo-online-04-134413-020725-25.png", comboPrices[3]));

        // Total amount
        JPanel totalPanel = new JPanel(new GridBagLayout());
        totalPanel.setBackground(Color.WHITE);
        GridBagConstraints tgbc = new GridBagConstraints();
        tgbc.insets = new Insets(5, 10, 5, 10);
        tgbc.anchor = GridBagConstraints.EAST;
        tgbc.gridx = 0;
        tgbc.gridy = 0;
        JLabel tongTienLabel = new JLabel("Tổng tiền:");
        tongTienLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        totalPanel.add(tongTienLabel, tgbc);
        tgbc.gridx = 1;
        totalLabel = new JLabel(totalAmount + " vnd");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(new Color(200, 0, 0));
        totalPanel.add(totalLabel, tgbc);
        tgbc.gridx = 0;
        tgbc.gridy = 1;
        JLabel giamLabel = new JLabel("Số tiền được giảm:");
        giamLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        totalPanel.add(giamLabel, tgbc);
        tgbc.gridx = 1;
        discountLabel = new JLabel("0 vnd");
        discountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        discountLabel.setForeground(new Color(200, 0, 0));
        totalPanel.add(discountLabel, tgbc);
        tgbc.gridx = 0;
        tgbc.gridy = 2;
        JLabel payLabel1 = new JLabel("Số tiền cần thanh toán:");
        payLabel1.setFont(new Font("Arial", Font.PLAIN, 16));
        totalPanel.add(payLabel1, tgbc);
        tgbc.gridx = 1;
        payLabel = new JLabel(totalAmount + " vnd");
        payLabel.setFont(new Font("Arial", Font.BOLD, 16));
        payLabel.setForeground(new Color(200, 0, 0));
        totalPanel.add(payLabel, tgbc);

        // Payment methods
        JPanel payPanel = new JPanel();
        payPanel.setLayout(new BoxLayout(payPanel, BoxLayout.Y_AXIS));
        payPanel.setBackground(Color.WHITE);
        payPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(33, 150, 243)), "PHƯƠNG THỨC THANH TOÁN", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(33, 150, 243)));
        JPanel methodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        methodPanel.setBackground(Color.WHITE);
        ButtonGroup group = new ButtonGroup();

        String[] methods = {"Tiền mặt", "Chuyển khoản"};
        for (int i = 0; i < methods.length; i++) {
            JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            item.setBackground(Color.WHITE);
            JRadioButton rb = new JRadioButton();
            rb.setBackground(Color.WHITE);
            group.add(rb);
            JLabel label = new JLabel(methods[i]);
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            item.add(rb);
            item.add(label);
            methodPanel.add(item);
        }
        payPanel.add(methodPanel);

        // Continue button
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(new Color(245, 245, 245));
        JButton nextBtn = new JButton("TIẾP TỤC");
        nextBtn.setBackground(new Color(33, 150, 243));
        nextBtn.setForeground(Color.WHITE);
        nextBtn.setFont(new Font("Arial", Font.BOLD, 16));
        nextBtn.setPreferredSize(new Dimension(120, 40));
        nextBtn.setFocusPainted(false);
        nextBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnPanel.add(nextBtn);

        // Add to left content panel
        leftContentPanel.add(infoPanel);
        leftContentPanel.add(Box.createVerticalStrut(20));
        leftContentPanel.add(comboPanel);
        leftContentPanel.add(Box.createVerticalStrut(20));
        leftContentPanel.add(totalPanel);
        leftContentPanel.add(Box.createVerticalStrut(20));
        leftContentPanel.add(payPanel);
        leftContentPanel.add(Box.createVerticalStrut(10));

        // Wrap left content in a scroll pane
        mainPanel.add(leftContentPanel, BorderLayout.CENTER);

        // Right: Movie info
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setPreferredSize(new Dimension(320, 0));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        JLabel poster = new JLabel();
        poster.setPreferredSize(new Dimension(180, 260));
        try {
            ImageIcon icon = new ImageIcon(new URL(imageUrl));
            Image img = icon.getImage().getScaledInstance(180, 260, Image.SCALE_SMOOTH);
            poster.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            BufferedImage placeholder = new BufferedImage(180, 260, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = placeholder.createGraphics();
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(0, 0, 180, 260);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            g2d.drawString("No Image", 50, 130);
            g2d.dispose();
            poster.setIcon(new ImageIcon(placeholder));
        }
        rightPanel.add(poster);
        rightPanel.add(Box.createVerticalStrut(15));
        rightPanel.add(createInfoLabel("Tên phim:", movieName));
        rightPanel.add(createInfoLabel("Thể loại:", genre));
        rightPanel.add(createInfoLabel("Thời lượng:", duration));
        rightPanel.add(createInfoLabel("Rạp chiếu:", cinemaName));
        rightPanel.add(createInfoLabel("Ngày chiếu:", showDate));
        rightPanel.add(createInfoLabel("Giờ chiếu:", showTime));
        rightPanel.add(createInfoLabel("Ghế ngồi:", String.join(", ", selectedSeats)));
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(btnPanel);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // Bottom: chỉ hiển thị thời gian còn lại ở góc trái
        // JPanel bottomPanel = new JPanel(new BorderLayout());
        // bottomPanel.setBackground(new Color(245, 245, 245));
        // bottomPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        // JLabel timerLabel = new JLabel("10:00");
        // timerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        // timerLabel.setForeground(new Color(33, 150, 243));
        // bottomPanel.add(timerLabel, BorderLayout.WEST);
        // add(bottomPanel, BorderLayout.SOUTH);

        // Timer logic
        // final int[] timeLeft = {10 * 60};
        // new javax.swing.Timer(1000, e -> {
        //     timeLeft[0]--;
        //     int min = timeLeft[0] / 60;
        //     int sec = timeLeft[0] % 60;
        //     timerLabel.setText(String.format("%d:%02d", min, sec));
        //     if (timeLeft[0] <= 0) {
        //         ((javax.swing.Timer) e.getSource()).stop();
        //         timerLabel.setText("0:00");
        //         JOptionPane.showMessageDialog(this, "Hết thời gian giữ vé!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        //         dispose();
        //     }
        // }).start();
    }

    private JPanel createInfoLabel(String label, String value) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        p.setBackground(Color.WHITE);
        JLabel l1 = new JLabel(label);
        l1.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel l2 = new JLabel(value);
        l2.setFont(new Font("Arial", Font.PLAIN, 14));
        p.add(l1);
        p.add(l2);
        return p;
    }

    private JPanel createInfoField(String label, String value) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        p.setBackground(Color.WHITE);
        JLabel l1 = new JLabel(label);
        l1.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField field = new JTextField(value, 15);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        p.add(l1);
        p.add(field);
        return p;
    }

    private JPanel createComboRowV2(String name, String desc, int qty, String imgUrl, int price) {
        JPanel row = new JPanel(new GridLayout(1, 4));
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Image
        JLabel img = new JLabel();
        img.setHorizontalAlignment(SwingConstants.CENTER);
        img.setPreferredSize(new Dimension(60, 60));
        try {
            ImageIcon icon = new ImageIcon(new URL(imgUrl));
            Image image = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            img.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            img.setBackground(Color.LIGHT_GRAY);
            img.setOpaque(true);
        }
        row.add(img);

        // Combo name
        JLabel nameLabel = new JLabel("<html><b>" + name + "</b></html>");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        row.add(nameLabel);

        // Description
        JLabel descLabel = new JLabel("<html>" + desc + "</html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        row.add(descLabel);

        // Quantity
        JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        qtyPanel.setBackground(Color.WHITE);
        JButton minus = new JButton("-");
        minus.setPreferredSize(new Dimension(32, 32));
        JButton plus = new JButton("+");
        plus.setPreferredSize(new Dimension(32, 32));
        JLabel qtyLabel = new JLabel(String.valueOf(qty));
        qtyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        plus.setBackground(new Color(33, 150, 243));
        plus.setForeground(Color.WHITE);
        minus.setBackground(new Color(200, 200, 210));
        minus.setForeground(Color.DARK_GRAY);
        plus.setFocusPainted(false);
        minus.setFocusPainted(false);
        plus.setBorder(BorderFactory.createLineBorder(new Color(33, 150, 243)));
        minus.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210)));

        plus.addActionListener(e -> {
            int val = Integer.parseInt(qtyLabel.getText());
            qtyLabel.setText(String.valueOf(val + 1));
            updateTotal(price);
        });
        minus.addActionListener(e -> {
            int val = Integer.parseInt(qtyLabel.getText());
            if (val > 0) {
                qtyLabel.setText(String.valueOf(val - 1));
                updateTotal(-price);
            }
        });
        qtyPanel.add(qtyLabel);
        qtyPanel.add(plus);
        qtyPanel.add(minus);
        row.add(qtyPanel);

        return row;
    }

    private void updateTotal(int amount) {
        totalAmount += amount;
        totalLabel.setText(totalAmount + " vnd");
        payLabel.setText(totalAmount + " vnd");
    }
}