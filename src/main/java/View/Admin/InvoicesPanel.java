package View.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InvoicesPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnEdit, btnDelete, btnRefresh;

    public InvoicesPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("🧾 Quản lý hóa đơn", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(30, 144, 255));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Mã HD", "Khách hàng", "Ngày lập", "Tổng tiền", "Phương thức"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xoá");
        btnRefresh = new JButton("Làm mới");

        for (JButton btn : new JButton[]{btnAdd, btnEdit, btnDelete, btnRefresh}) {
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            btn.setBackground(new Color(230, 240, 255));
            p.add(btn);
        }
        return p;
    }
}
