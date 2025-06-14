package View;

import Dao.TicketDao;
import Model.Ticket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TicketManagementView extends JFrame {
    private JTable table;
    private JButton btnRefresh;
    private JButton btnDelete;

    public TicketManagementView() {
        setTitle("Quản lý vé");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        loadTickets();
    }

    private void initComponents() {
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        btnRefresh = new JButton("Làm mới");
        btnRefresh.addActionListener(e -> loadTickets());
        
        btnDelete = new JButton("Xóa vé");
        btnDelete.addActionListener(e -> deleteSelectedTicket());
        
        JPanel panelBottom = new JPanel();
        panelBottom.add(btnRefresh);
        panelBottom.add(btnDelete);


        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }

    private void loadTickets() {
        List<Ticket> tickets = new TicketDao().getAllTickets();

        String[] columns = {"ID", "Người dùng", "Phim", "Lịch chiếu", "Ghế"};
        String[][] data = new String[tickets.size()][5];

        for (int i = 0; i < tickets.size(); i++) {
            Ticket t = tickets.get(i);
            data[i][0] = String.valueOf(t.getId());
            data[i][1] = t.getUser().getUsername();
            data[i][2] = t.getMovieTitle();
            data[i][3] = t.getShowtime();
            data[i][4] = t.getSeat();
        }
        
        

        table.setModel(new DefaultTableModel(data, columns));
    }
    
    
    
    
    
        private void deleteSelectedTicket() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int ticketId = Integer.parseInt(table.getValueAt(row, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa vé này?",
                    "Xác nhận xóa vé", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = new TicketDao().deleteTicketById(ticketId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Xóa vé thành công.");
                    loadTickets();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa vé thất bại.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một vé để xóa.");
        }
    }

}
