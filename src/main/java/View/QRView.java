package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QRView extends JDialog {
	public QRView(Window parent, String movieName, String genre, String duration, String cinemaName, String showDate,
			String showTime, java.util.List<String> seats, Double tongTienAmount, Double payAmount, String customerName,
			String customerPhone) {
		super(parent, "Chuyển khoản qua QR", ModalityType.APPLICATION_MODAL);
		setSize(400, 500);
		setLocationRelativeTo(parent);
		setLayout(new BorderLayout());
		getContentPane().setBackground(Color.WHITE);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		JLabel title = new JLabel("Quét mã QR để chuyển khoản");
		title.setFont(new Font("Arial", Font.BOLD, 20));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(title);
		mainPanel.add(Box.createVerticalStrut(20));

		// QR Image
		JLabel qrLabel = new JLabel();
		qrLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		try {
			ImageIcon icon = new ImageIcon(new URL("https://qrcode-gen.com/images/qrcode-default.png"));
			Image img = icon.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
			qrLabel.setIcon(new ImageIcon(img));
		} catch (Exception e) {
			qrLabel.setText("Không tải được mã QR");
		}
		mainPanel.add(qrLabel);
		mainPanel.add(Box.createVerticalStrut(30));

		// Amount
		JLabel amountLabel = new JLabel("Số tiền cần chuyển: " + tongTienAmount + " VND");
		amountLabel.setFont(new Font("Arial", Font.BOLD, 18));
		amountLabel.setForeground(new Color(33, 150, 243));
		amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(amountLabel);
		mainPanel.add(Box.createVerticalStrut(30));

		// Success button
		JButton successBtn = new JButton("Thành công");
		successBtn.setFont(new Font("Arial", Font.BOLD, 16));
		successBtn.setBackground(new Color(33, 150, 243));
		successBtn.setForeground(Color.WHITE);
		successBtn.setFocusPainted(false);
		successBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		successBtn.setPreferredSize(new Dimension(120, 40));
		successBtn.addActionListener(e -> {
			dispose();
			new BillView(parent, movieName, genre, duration, cinemaName, showDate, showTime, seats, tongTienAmount,
					payAmount, "Chuyển khoản", customerName, customerPhone).setVisible(true);
		});
		mainPanel.add(successBtn);

		add(mainPanel, BorderLayout.CENTER);
	}
}
