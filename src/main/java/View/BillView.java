package View;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BillView extends JDialog {
    public BillView(Window parent, String movieName, String genre, String duration, String cinemaName, String showDate, String showTime, java.util.List<String> seats, int tongTienAmount, int payAmount, String paymentMethod, String customerName, String customerPhone) {
        super(parent, "Hóa đơn vé xem phim", ModalityType.APPLICATION_MODAL);
        setSize(500, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("HÓA ĐƠN VÉ XEM PHIM");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(createInfoLabel("Họ và Tên:", customerName));
        mainPanel.add(createInfoLabel("Số điện thoại:", customerPhone));
        mainPanel.add(createInfoLabel("Tên phim:", movieName));
        mainPanel.add(createInfoLabel("Thể loại:", genre));
        mainPanel.add(createInfoLabel("Thời lượng:", duration));
        mainPanel.add(createInfoLabel("Rạp chiếu:", cinemaName));
        mainPanel.add(createInfoLabel("Ngày chiếu:", showDate));
        mainPanel.add(createInfoLabel("Giờ chiếu:", showTime));
        mainPanel.add(createInfoLabel("Ghế ngồi:", String.join(", ", seats)));
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(new JSeparator());
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(createInfoLabel("Tổng tiền:", tongTienAmount + " VND"));
        mainPanel.add(createInfoLabel("Số tiền cần thanh toán:", tongTienAmount + " VND"));
        mainPanel.add(createInfoLabel("Phương thức thanh toán:", paymentMethod));
        mainPanel.add(Box.createVerticalStrut(30));

        JButton closeBtn = new JButton("Đóng");
        closeBtn.setFont(new Font("Arial", Font.BOLD, 16));
        closeBtn.setBackground(new Color(33, 150, 243));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFocusPainted(false);
        closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeBtn.setPreferredSize(new Dimension(120, 40));
        closeBtn.addActionListener(e -> {
            // Đóng tất cả các cửa sổ hiện tại và mở lại HomeUseView
            Window[] windows = Window.getWindows();
            for (Window w : windows) {
                if (w != null && w.isDisplayable()) {
                    w.dispose();
                }
            }
            new HomeUseView().setVisible(true);
        });

        JButton printBtn = new JButton("In");
        printBtn.setFont(new Font("Arial", Font.BOLD, 16));
        printBtn.setBackground(new Color(76, 175, 80));
        printBtn.setForeground(Color.WHITE);
        printBtn.setFocusPainted(false);
        printBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        printBtn.setPreferredSize(new Dimension(120, 40));
        printBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Lưu hóa đơn");
            fileChooser.setSelectedFile(new java.io.File("HoaDonVeXemPhim.txt"));
            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                try (java.io.PrintWriter writer = new java.io.PrintWriter(fileToSave, "UTF-8")) {
                    writer.println("===== HÓA ĐƠN VÉ XEM PHIM =====");
                    writer.println("Họ và Tên: " + customerName);
                    writer.println("Số điện thoại: " + customerPhone);
                    writer.println("Tên phim: " + movieName);
                    writer.println("Thể loại: " + genre);
                    writer.println("Thời lượng: " + duration);
                    writer.println("Rạp chiếu: " + cinemaName);
                    writer.println("Ngày chiếu: " + showDate);
                    writer.println("Giờ chiếu: " + showTime);
                    writer.println("Ghế ngồi: " + String.join(", ", seats));
                    writer.println("Tổng tiền: " + tongTienAmount + " VND");
                    writer.println("Số tiền cần thanh toán: " + tongTienAmount + " VND");
                    writer.println("Phương thức thanh toán: " + paymentMethod);
                    writer.println("===============================");
                    JOptionPane.showMessageDialog(this, "Đã lưu hóa đơn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi lưu hóa đơn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(printBtn);
        btnPanel.add(closeBtn);
        mainPanel.add(btnPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createInfoLabel(String label, String value) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        p.setBackground(Color.WHITE);
        JLabel l1 = new JLabel(label);
        l1.setFont(new Font("Arial", Font.BOLD, 15));
        JLabel l2 = new JLabel(value);
        l2.setFont(new Font("Arial", Font.PLAIN, 15));
        p.add(l1);
        p.add(l2);
        return p;
    }
}
