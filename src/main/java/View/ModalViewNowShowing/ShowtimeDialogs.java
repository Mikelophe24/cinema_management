package View.ModalViewNowShowing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import Dao.MovieScheduleDao;
import Dao.TheaterDao;
import Model.MovieSchedule;
import Model.Theater;
import View.MovieSection.ComingSoonView.MovieInfo;

public class ShowtimeDialogs extends JDialog {
	private String movieName;
	private Theater theater;
	private String showDate;
	private JPanel showtimePanel;
	private MovieInfo movie;

	private static List<MovieSchedule> schedules = new ArrayList<>();

	public ShowtimeDialogs(Frame owner, MovieInfo movie) {
		super(owner, "LỊCH CHIẾU - " + movie.name, true);
		this.movie = movie;
		this.movieName = movie.name;
		this.showDate = movie.releaseDate;

		setSize(900, 480);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		getContentPane().setBackground(Color.WHITE);

		// Header
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(Color.WHITE);
		JLabel title = new JLabel("LỊCH CHIẾU - " + movieName);
		title.setFont(new Font("Arial", Font.BOLD, 24));
		title.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));
		header.add(title, BorderLayout.WEST);

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

		// Main content
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.setBackground(Color.WHITE);
		content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

		// Rạp
		JLabel cinemaLabel = new JLabel("");
		cinemaLabel.setFont(new Font("Arial", Font.BOLD, 28));
		cinemaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		content.add(cinemaLabel);
		content.add(Box.createVerticalStrut(16));

		// Ngày chiếu
		JButton dateBtn = new JButton(showDate);
		dateBtn.setFont(new Font("Arial", Font.BOLD, 18));
		dateBtn.setForeground(new Color(33, 150, 243));
		dateBtn.setBackground(Color.WHITE);
		dateBtn.setFocusPainted(false);
		dateBtn.setBorder(BorderFactory.createLineBorder(new Color(33, 150, 243), 2, true));
		dateBtn.setContentAreaFilled(false);
		dateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		dateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dateBtn.addActionListener(e -> {
		});
		content.add(dateBtn);
		content.add(Box.createVerticalStrut(20));

		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
		separator.setForeground(Color.LIGHT_GRAY);
		content.add(separator);
		content.add(Box.createVerticalStrut(20));

		JLabel subLabel = new JLabel("2D PHỤ ĐỀ");
		subLabel.setFont(new Font("Arial", Font.BOLD, 18));
		subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		content.add(subLabel);
		content.add(Box.createVerticalStrut(10));

		showtimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		showtimePanel.setBackground(Color.WHITE);
		content.add(showtimePanel);

		add(content, BorderLayout.CENTER);

		updateShowtimes();
	}

	private void updateShowtimes() {
		showtimePanel.removeAll();
		schedules = MovieScheduleDao.queryListByMovieId(movie.id);
		for (MovieSchedule schedule : schedules) {
			String time = schedule.getStartTime().toString();
			theater = TheaterDao.queryOneWithoutSeat(schedule.getTheaterId());
			JButton btn = new JButton("<html><div style='text-align:center;'><b>" + time + "</b><br/>Phòng "
					+ theater.getName() + "</div></html>");
			btn.setPreferredSize(new Dimension(110, 60));
			btn.setBackground(Color.WHITE);
			btn.setFocusPainted(false);
			btn.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
			btn.setFont(new Font("Arial", Font.PLAIN, 15));
			btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
			btn.addActionListener(e -> {
				new ConfirmBookingDialogs((Frame) SwingUtilities.getWindowAncestor(this), movie, theater, time,
						schedule.getId(), schedule.getPrice()).setVisible(true);
			});
			showtimePanel.add(btn);
		}
		showtimePanel.revalidate();
		showtimePanel.repaint();
	}
}
