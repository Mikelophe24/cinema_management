package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class SeatSelectionView extends JDialog {
    private static final int ROWS = 10;
    private static final int COLS = 15;
    private static final String[] ROW_LABELS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    private static final Color COLOR_AVAILABLE = new Color(220, 220, 220);
    private static final Color COLOR_SELECTED = new Color(0, 102, 204);
    private static final Color COLOR_SOLD = new Color(220, 53, 69);
    private static final Color COLOR_RESERVED = new Color(255, 193, 7);
    private static final Color COLOR_HELD = new Color(0, 153, 255);

    private Set<String> selectedSeats = new HashSet<>();
    private JLabel totalLabel;
    private JLabel selectedSeatsLabel;

    public SeatSelectionView(String movieName, String cinemaName, String showDate, String showTime, String genre, String duration, String imageUrl) {
        super((Frame) null, "Chọn ghế", true);
        setSize(1200, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel, BorderLayout.CENTER);

        // LEFT - Sơ đồ ghế
        JPanel seatPanel = new JPanel();
        seatPanel.setLayout(new BoxLayout(seatPanel, BoxLayout.Y_AXIS));
        seatPanel.setBackground(Color.WHITE);

        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 5));
        legendPanel.setBackground(Color.WHITE);
        legendPanel.add(createLegend(COLOR_AVAILABLE, "Ghế trống"));
        legendPanel.add(createLegend(COLOR_SELECTED, "Ghế đang chọn"));
        legendPanel.add(createLegend(COLOR_SOLD, "Ghế đã bán"));
        seatPanel.add(legendPanel);

        JLabel screenLabel = new JLabel("MÀN HÌNH CHIẾU", SwingConstants.CENTER);
        screenLabel.setFont(new Font("Arial", Font.BOLD, 16));
        screenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        seatPanel.add(Box.createVerticalStrut(10));
        seatPanel.add(screenLabel);

        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                String seatCode = ROW_LABELS[row] + (col + 1);
                JButton seatBtn = new JButton(seatCode);
                seatBtn.setPreferredSize(new Dimension(44, 36));
                seatBtn.setFont(new Font("Arial", Font.PLAIN, 12));
                seatBtn.setFocusPainted(false);
                seatBtn.setBackground(COLOR_AVAILABLE);
                seatBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                if ((row == 2 && col == 3) || (row == 5 && col == 7)) {
                    seatBtn.setBackground(COLOR_SOLD);
                    seatBtn.setEnabled(false);
                } else if ((row == 1 && col == 5) || (row == 7 && col == 10)) {
                    seatBtn.setBackground(COLOR_HELD);
                    seatBtn.setEnabled(false);
                } else if ((row == 3 && col == 8) || (row == 8 && col == 2)) {
                    seatBtn.setBackground(COLOR_RESERVED);
                    seatBtn.setEnabled(false);
                } else {
                    seatBtn.addActionListener(e -> {
                        if (selectedSeats.contains(seatCode)) {
                            selectedSeats.remove(seatCode);
                            seatBtn.setBackground(COLOR_AVAILABLE);
                        } else {
                            selectedSeats.add(seatCode);
                            seatBtn.setBackground(COLOR_SELECTED);
                        }
                        updateTotal();
                    });
                }

                gbc.gridx = col;
                gbc.gridy = row;
                gridPanel.add(seatBtn, gbc);
            }
        }
        seatPanel.add(Box.createVerticalStrut(10));
        seatPanel.add(gridPanel);

        mainPanel.add(seatPanel, BorderLayout.CENTER);

        // RIGHT - Info phim
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setPreferredSize(new Dimension(320, 0));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel poster = new JLabel();
        poster.setPreferredSize(new Dimension(180, 260));
        try {
            ImageIcon icon = new ImageIcon(new java.net.URL(imageUrl));
            Image img = icon.getImage().getScaledInstance(180, 260, Image.SCALE_SMOOTH);
            poster.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            poster.setIcon(new ImageIcon(new BufferedImage(180, 260, BufferedImage.TYPE_INT_RGB)));
        }
        infoPanel.add(poster);
        infoPanel.add(Box.createVerticalStrut(10));
        JLabel nameLabel = new JLabel("<html><b>Tên phim: " + movieName + "</b></html>");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        infoPanel.add(nameLabel);
        JLabel genreLabel = new JLabel("Thể loại: " + genre);
        genreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(genreLabel);
        JLabel durationLabel = new JLabel("Thời lượng: " + duration);
        durationLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(durationLabel);
        JLabel cinemaLabel = new JLabel("Rạp chiếu: " + cinemaName);
        cinemaLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(cinemaLabel);
        JLabel dateLabel = new JLabel("Ngày chiếu: " + showDate);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(dateLabel);
        JLabel timeLabel = new JLabel("Giờ chiếu: " + showTime);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(timeLabel);
        JLabel roomLabel = new JLabel("Phòng chiếu: P2");
        roomLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(roomLabel);
        selectedSeatsLabel = new JLabel("Ghế ngồi: ...");
        selectedSeatsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(selectedSeatsLabel);
        infoPanel.add(Box.createVerticalStrut(20));
        JButton nextBtn = new JButton("TIẾP TỤC");
        nextBtn.setBackground(new Color(33, 150, 243));
        nextBtn.setForeground(Color.WHITE);
        nextBtn.setFont(new Font("Arial", Font.BOLD, 20));
        nextBtn.setFocusPainted(false);
        nextBtn.setPreferredSize(new Dimension(180, 40));
        nextBtn.addActionListener(e -> {
            java.util.List<String> sortedSeats = new java.util.ArrayList<>(selectedSeats);
            java.util.Collections.sort(sortedSeats);
            int total = selectedSeats.size() * 50000;
            new View.PaymentView(
                movieName,
                cinemaName,
                showDate,
                showTime,
                genre,
                duration,
                imageUrl,
                sortedSeats,
                total
            ).setVisible(true);
        });
        infoPanel.add(nextBtn);

        mainPanel.add(infoPanel, BorderLayout.EAST);

        // BOTTOM - Thời gian và tổng tiền
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        bottomPanel.setBackground(Color.WHITE);
        totalLabel = new JLabel("Tổng tiền: 0 VND");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(new JLabel("Ghế thường"));
        bottomPanel.add(new JLabel("Ghế đôi"));
        bottomPanel.add(totalLabel);
        bottomPanel.setFont(new Font("Arial", Font.BOLD, 16));
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createLegend(Color color, String text) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        panel.setBackground(Color.WHITE);
        JLabel icon = new JLabel();
        icon.setPreferredSize(new Dimension(28, 18));
        icon.setOpaque(true);
        icon.setBackground(color);
        icon.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(icon);
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(label);
        return panel;
    }

    private JPanel createInfoLabel(String title, String value) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        p.setBackground(Color.WHITE);
        JLabel label1 = new JLabel(title);
        label1.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel label2 = new JLabel(value);
        label2.setFont(new Font("Arial", Font.PLAIN, 14));
        p.add(label1);
        p.add(label2);
        return p;
    }

    private void updateTotal() {
        int total = selectedSeats.size() * 50000;
        totalLabel.setText("Tổng tiền: " + total + " VND");
        // Cập nhật ghế ngồi
        if (selectedSeats.isEmpty()) {
            selectedSeatsLabel.setText("Ghế ngồi: ...");
        } else {
            java.util.List<String> sorted = new java.util.ArrayList<>(selectedSeats);
            java.util.Collections.sort(sorted);
            selectedSeatsLabel.setText("Ghế ngồi: " + String.join(",", sorted));
        }
    }
}
