package View.ModalViewNowShowing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import Model.Theater;
import View.MovieSection.ComingSoonView.MovieInfo;

public class ConfirmBookingDialogs extends JDialog {
	private String genre;
	private String duration;
	private String imageUrl;

	public ConfirmBookingDialogs(Frame owner, MovieInfo movie, Theater theater, String showTime, int scheduleId,
			Double price) {
		super(owner, "Xác nhận đặt vé", true);
		this.genre = genre;
		this.duration = duration;
		this.imageUrl = imageUrl;
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
		JLabel movieLabel = new JLabel(movie.name);
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
		JLabel rapValue = new JLabel(theater.getName(), SwingConstants.CENTER);
		rapValue.setFont(new Font("Arial", Font.BOLD, 20));
		JLabel dateValue = new JLabel(movie.releaseDate, SwingConstants.CENTER);
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
		// Nếu có genre, duration truyền vào thì truyền tiếp, nếu không thì để mẫu
		agreeBtn.addActionListener(e -> {
			dispose();
			new View.SeatSelectionView(movie, theater, showTime, scheduleId, price).setVisible(true);
		});
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(Color.WHITE);
		btnPanel.add(agreeBtn);
		content.add(btnPanel);

		add(content, BorderLayout.CENTER);
	}

}
