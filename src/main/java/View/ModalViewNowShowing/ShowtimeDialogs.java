package View.ModalViewNowShowing;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ShowtimeDialogs extends JDialog {
    private String movieName;
    private String cinemaName;
    private String showDate;
    private JPanel showtimePanel;
    private String selectedDate;
    private String genre;
    private String duration;
    private String imageUrl;

    private static final String[][] SAMPLE_SHOWTIMES = {
        {"17:15", "120"}, {"19:15", "131"}, {"20:45", "61"}, {"22:00", "115"}, {"23:00", "90"}
    };

    public ShowtimeDialogs(Frame owner, String movieName, String cinemaName, String showDate, String genre, String duration, String imageUrl) {
        super(owner, "LỊCH CHIẾU - " + movieName, true);
        this.movieName = movieName;
        this.cinemaName = cinemaName;
        this.showDate = showDate;
        this.genre = genre;
        this.duration = duration;
        this.imageUrl = imageUrl;
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        JLabel title = new JLabel("LỊCH CHIẾU - " + movieName);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 0));
        header.add(title, BorderLayout.WEST);

        JButton closeBtn = new JButton("✕");
        closeBtn.setFocusPainted(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setFont(new Font("Arial", Font.PLAIN, 20));
        closeBtn.setForeground(Color.GRAY);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> dispose());
        header.add(closeBtn, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel cinemaLabel = new JLabel(cinemaName);
        cinemaLabel.setFont(new Font("Arial", Font.BOLD, 30));
        cinemaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(cinemaLabel);
        content.add(Box.createVerticalStrut(20));

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        datePanel.setBackground(Color.WHITE);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDate = LocalDate.parse(showDate, formatter);
        String[] days = new String[8];
        JButton[] dayButtons = new JButton[8];

        for (int i = 0; i < 8; i++) {
            days[i] = startDate.plusDays(i).format(formatter);
            JButton btn = new JButton(days[i]);
            btn.setFont(new Font("Arial", Font.BOLD, 15));
            btn.setPreferredSize(new Dimension(110, 40));
            btn.setBackground(i == 0 ? new Color(33, 150, 243) : Color.WHITE);
            btn.setForeground(i == 0 ? Color.WHITE : new Color(33, 150, 243));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(new Color(33, 150, 243), 2, true));
            int idx = i;
            btn.addActionListener(e -> {
                selectedDate = days[idx];
                for (int j = 0; j < dayButtons.length; j++) {
                    dayButtons[j].setBackground(j == idx ? new Color(33, 150, 243) : Color.WHITE);
                    dayButtons[j].setForeground(j == idx ? Color.WHITE : new Color(33, 150, 243));
                }
            });
            dayButtons[i] = btn;
            datePanel.add(btn);
        }
        selectedDate = days[0];
        content.add(datePanel);
        content.add(Box.createVerticalStrut(20));

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setForeground(new Color(220, 220, 220));
        content.add(separator);
        content.add(Box.createVerticalStrut(20));

        JLabel subLabel = new JLabel("2D PHỤ ĐỀ");
        subLabel.setFont(new Font("Arial", Font.BOLD, 20));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(subLabel);
        content.add(Box.createVerticalStrut(10));

        showtimePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        showtimePanel.setBackground(Color.WHITE);
        updateShowtimes();
        content.add(showtimePanel);

        add(content, BorderLayout.CENTER);
    }

    private void updateShowtimes() {
        showtimePanel.removeAll();
        for (String[] show : SAMPLE_SHOWTIMES) {
            String time = show[0];
            String seat = show[1];
            JButton btn = new JButton("<html><div style='text-align:center;'><b>" + time + "</b><br/>" + seat + " ghế trống</div></html>");
            btn.setPreferredSize(new Dimension(120, 60));
            btn.setBackground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
            btn.setFont(new Font("Arial", Font.PLAIN, 14));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.addActionListener(e -> {
                new View.ModalViewNowShowing.ConfirmBookingDialogs(
                    (Frame) SwingUtilities.getWindowAncestor(this),
                    movieName,
                    cinemaName,
                    selectedDate,
                    time,
                    genre,
                    duration,
                    imageUrl
                ).setVisible(true);
            });
            showtimePanel.add(btn);
        }
        showtimePanel.revalidate();
        showtimePanel.repaint();
    }
}
