//package View;
//
//import Dao.TicketDao;
//import Model.Ticket;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.util.List;
//
//public class UserTicketsView extends JFrame {
//
//    public UserTicketsView(int userId) {
//        setTitle("Vé đã đặt");
//        setSize(600, 400);
//        setLocationRelativeTo(null);
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//
//        TicketDao dao = new TicketDao();
//        List<Ticket> tickets = dao.getTicketsByUserId(userId);
//
//        String[] columns = {"Tên phim", "Lịch chiếu", "Ghế"};
//        String[][] data = new String[tickets.size()][3];
//
//        for (int i = 0; i < tickets.size(); i++) {
//            Ticket t = tickets.get(i);
//            data[i][0] = t.getMovieTitle();
//            data[i][1] = t.getShowtime();
//            data[i][2] = t.getSeat();
//        }
//
//        JTable ticketTable = new JTable(new DefaultTableModel(data, columns));
//        JScrollPane scrollPane = new JScrollPane(ticketTable);
//
//        JLabel lblTitle = new JLabel("Danh sách vé đã đặt", SwingConstants.CENTER);
//        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
//
//        setLayout(new BorderLayout());
//        add(lblTitle, BorderLayout.NORTH);
//        add(scrollPane, BorderLayout.CENTER);
//    }
//}
