package View.ModalView;

import javax.swing.*;
import java.awt.*;

public class ConfirmBookingDialog extends JDialog {
    public ConfirmBookingDialog(Frame owner, String movieName, String cinemaName, String showDate, String showTime) {
        super(owner, "Xác nhận đặt vé", true);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        JLabel title = new JLabel("BẠN ĐANG ĐẶT VÉ XEM PHIM");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(18, 24, 10, 0));
        header.add(title, BorderLayout.WEST);
        // Nút đóng
        JButton closeBtn = new JButton("✕");
        closeBtn.setFocusPainted(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        closeBtn.setForeground(Color.GRAY);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> dispose());
        header.add(closeBtn, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Gạch ngang
        JSeparator sep1 = new JSeparator(SwingConstants.HORIZONTAL);
        sep1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep1.setForeground(Color.LIGHT_GRAY);
        add(sep1, BorderLayout.AFTER_LAST_LINE);

        // Nội dung chính
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        // Tên phim
        JLabel movieLabel = new JLabel(movieName);
        movieLabel.setFont(new Font("Arial", Font.BOLD, 32));
        movieLabel.setForeground(new Color(33, 150, 243));
        movieLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(Box.createVerticalStrut(10));
        content.add(movieLabel);
        content.add(Box.createVerticalStrut(18));

        // Gạch ngang
        JSeparator sep2 = new JSeparator(SwingConstants.HORIZONTAL);
        sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep2.setForeground(Color.LIGHT_GRAY);
        content.add(sep2);
        content.add(Box.createVerticalStrut(18));

        // Bảng thông tin
        JPanel infoPanel = new JPanel(new GridLayout(2, 3, 0, 0));
        infoPanel.setBackground(new Color(250, 250, 250));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JLabel rapTitle = new JLabel("Rạp chiếu", SwingConstants.CENTER);
        rapTitle.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel dateTitle = new JLabel("Ngày chiếu", SwingConstants.CENTER);
        dateTitle.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel timeTitle = new JLabel("Giờ chiếu", SwingConstants.CENTER);
        timeTitle.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(rapTitle);
        infoPanel.add(dateTitle);
        infoPanel.add(timeTitle);
        JLabel rapValue = new JLabel(cinemaName, SwingConstants.CENTER);
        rapValue.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel dateValue = new JLabel(showDate, SwingConstants.CENTER);
        dateValue.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel timeValue = new JLabel(showTime, SwingConstants.CENTER);
        timeValue.setFont(new Font("Arial", Font.BOLD, 20));
        infoPanel.add(rapValue);
        infoPanel.add(dateValue);
        infoPanel.add(timeValue);
        content.add(infoPanel);
        content.add(Box.createVerticalStrut(18));

        // Gạch ngang
        JSeparator sep3 = new JSeparator(SwingConstants.HORIZONTAL);
        sep3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep3.setForeground(Color.LIGHT_GRAY);
        content.add(sep3);
        content.add(Box.createVerticalStrut(18));

        // Nút đồng ý
        JButton agreeBtn = new JButton("ĐỒNG Ý");
        agreeBtn.setFont(new Font("Arial", Font.BOLD, 18));
        agreeBtn.setBackground(new Color(33, 150, 243));
        agreeBtn.setForeground(Color.WHITE);
        agreeBtn.setFocusPainted(false);
        agreeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        agreeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        agreeBtn.setPreferredSize(new Dimension(160, 40));
        agreeBtn.addActionListener(e -> dispose());
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(agreeBtn);
        content.add(btnPanel);

        add(content, BorderLayout.CENTER);
    }
}
