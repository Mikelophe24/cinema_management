package View;

import Dao.TicketDao;
import Model.Ticket;
import Model.User;

import javax.swing.*;
import java.awt.*;

public class ShowtimeBookingView extends JFrame {
    public ShowtimeBookingView(User user, int movieId, String movieTitle) {
        setTitle("Đặt vé - " + movieTitle);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblInfo = new JLabel("Chọn lịch chiếu và ghế cho phim: " + movieTitle, SwingConstants.CENTER);
        lblInfo.setFont(new Font("Arial", Font.BOLD, 16));

        String[] showtimes = {"10:00", "13:00", "16:00", "19:00", "21:30"};
        JComboBox<String> cbShowtime = new JComboBox<>(showtimes);

        JLabel lblSeat = new JLabel("Chọn ghế (ví dụ: A1, B2):");
        JTextField txtSeat = new JTextField(10);

        JButton btnConfirm = new JButton("Xác nhận đặt vé");

        btnConfirm.addActionListener(e -> {
            String showtime = cbShowtime.getSelectedItem().toString();
            String seat = txtSeat.getText().trim();
            if (seat.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ghế!");
                return;
            }

            Ticket ticket = new Ticket(user.getId(), movieId, showtime, seat);
            boolean success = new TicketDao().addTicket(ticket);
            if (success) {
                JOptionPane.showMessageDialog(this, "Đã đặt vé thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Đặt vé thất bại!");
            }
        });

        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        centerPanel.add(lblInfo);
        centerPanel.add(cbShowtime);
        centerPanel.add(lblSeat);
        centerPanel.add(txtSeat);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnConfirm);

        setLayout(new BorderLayout());
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
