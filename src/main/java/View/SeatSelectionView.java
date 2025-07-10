package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Dao.SeatDao;
import Dao.SeatScheduleDao;
import Enum.SeatEnum;
import Enum.SeatScheduleEnum;
import Model.Seat;
import Model.SeatSchedule;
import Model.Theater;
import View.MovieSection.ComingSoonView.ComingSoonMovieInfo;

public class SeatSelectionView extends JDialog {
	private static final Color COLOR_AVAILABLE = new Color(220, 220, 220);
	private static final Color COLOR_SELECTED = new Color(0, 102, 204);
	private static final Color COLOR_SOLD = new Color(220, 53, 69);
	private static final Color COLOR_RESERVED = new Color(255, 193, 7);
	private static final Color COLOR_HELD = new Color(0, 153, 255);
	private static final Color COLOR_COUPLE = new Color(153, 102, 255);

	private Set<String> selectedSeats = new HashSet<>();
	private List<Integer> selectedSeatIds = new ArrayList<Integer>();
	private int totalPrice = 0;
	private JLabel totalLabel;
	private JLabel selectedSeatsLabel;
	private int scheduleId;
	private Double price;

	public SeatSelectionView(ComingSoonMovieInfo movie, Theater theater, String showTime, int scheduleId,
			Double price) {
		super((Frame) null, "Chọn ghế", true);
		this.scheduleId = scheduleId;
		this.price = price;

		List<Seat> seats = SeatDao.queryListByTheaterId(theater.getId());
		List<SeatSchedule> seatSchedules = SeatScheduleDao.queryListByScheduleId(scheduleId);
		System.out.println(seats);
		System.out.println(seatSchedules);

		setSize(1200, 720);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Color.WHITE);
		add(mainPanel, BorderLayout.CENTER);

		// LEFT - Sơ đồ ghế
		JPanel seatPanel = new JPanel();
		seatPanel.setLayout(new BoxLayout(seatPanel, BoxLayout.Y_AXIS));
		seatPanel.setBackground(Color.WHITE);

		JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 5));
		legendPanel.setBackground(Color.WHITE);
		legendPanel.add(createLegend(COLOR_AVAILABLE, "Ghế trống"));
		legendPanel.add(createLegend(COLOR_SELECTED, "Ghế đang chọn"));
		legendPanel.add(createLegend(COLOR_SOLD, "Ghế đã bán"));
		legendPanel.add(createLegend(COLOR_COUPLE, "Ghế đôi"));
		seatPanel.add(legendPanel);

		JLabel screenLabel = new JLabel("MÀN HÌNH CHIẾU", SwingConstants.CENTER);
		screenLabel.setFont(new Font("Arial", Font.BOLD, 16));
		screenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		seatPanel.add(Box.createVerticalStrut(10));
		seatPanel.add(screenLabel);

		JPanel gridPanel = new JPanel(new GridBagLayout());
		gridPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);

		Map<String, List<Seat>> seatMap = new TreeMap<String, List<Seat>>();
		for (Seat seat : seats) {
			String row = seat.getName().substring(0, 1);
			seatMap.computeIfAbsent(row, k -> new java.util.ArrayList<>()).add(seat);
		}
		// Sắp xếp ghế trong mỗi row
		for (List<Seat> rowSeats : seatMap.values()) {
			rowSeats.sort(java.util.Comparator.comparing(Seat::getName));
		}

		int rowIndex = 0;
		for (String row : seatMap.keySet()) {
			List<Seat> rowSeats = seatMap.get(row);
			int colIndex = 0;

			for (Seat seat : rowSeats) {
				boolean isSold = seatSchedules.stream()
						.anyMatch(seatSchedule -> seatSchedule.getSeatId() == seat.getId()
								&& seatSchedule.getStatus().getValue() == SeatScheduleEnum.Status.SOLD.getValue());
				String seatCode = seat.getName();
				JButton seatBtn = new JButton(seatCode);
				seatBtn.setPreferredSize(new Dimension(44, 36));
				seatBtn.setFont(new Font("Arial", Font.PLAIN, 12));
				seatBtn.setFocusPainted(false);
				seatBtn.setBackground(isSold ? COLOR_SOLD : COLOR_AVAILABLE);
				seatBtn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				if (seat.getType().getValue() == SeatEnum.Type.COUPLE.getValue()) {
					seatBtn.setBackground(isSold ? COLOR_SOLD : COLOR_COUPLE);
					seatBtn.setPreferredSize(new Dimension(88, 36));
					seatBtn.addActionListener(e -> {
						if (isSold)
							return;
						boolean isUp = true;
						if (selectedSeats.contains(seatCode)) {
							selectedSeatIds.remove(Integer.valueOf(seat.getId()));
							selectedSeats.remove(seatCode);
							seatBtn.setBackground(COLOR_COUPLE);
							isUp = false;
						} else {
							selectedSeatIds.add(Integer.valueOf(seat.getId()));
							selectedSeats.add(seatCode);
							seatBtn.setBackground(COLOR_SELECTED);
						}
						updateTotal(isUp, true);
					});
					gbc.gridx = colIndex;
					gbc.gridy = rowIndex;
					gbc.gridwidth = 2;
					gridPanel.add(seatBtn, gbc);
					colIndex += 2;
					continue;
				}

				// Ghế thường
				seatBtn.addActionListener(e -> {
					if (isSold)
						return;
					boolean isUp = true;
					if (selectedSeats.contains(seatCode)) {
						isUp = false;
						selectedSeatIds.remove(Integer.valueOf(seat.getId()));
						selectedSeats.remove(seatCode);
						seatBtn.setBackground(COLOR_AVAILABLE);
					} else {
						selectedSeatIds.add(Integer.valueOf(seat.getId()));
						selectedSeats.add(seatCode);
						seatBtn.setBackground(COLOR_SELECTED);
					}
					updateTotal(isUp, false);
				});

				gbc.gridx = colIndex;
				gbc.gridy = rowIndex;
				gbc.gridwidth = 1;
				gridPanel.add(seatBtn, gbc);

				colIndex++;
			}
			rowIndex++;
		}

		seatPanel.add(Box.createVerticalStrut(10));
		seatPanel.add(gridPanel);
		mainPanel.add(seatPanel, BorderLayout.CENTER);

		// RIGHT - Thông tin phim
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setBackground(Color.WHITE);
		infoPanel.setPreferredSize(new Dimension(320, 0));
		infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

		JLabel poster = new JLabel();
		poster.setPreferredSize(new Dimension(180, 260));
		try {
			ImageIcon icon = new ImageIcon(new java.net.URL(movie.imageUrl));
			Image img = icon.getImage().getScaledInstance(180, 260, Image.SCALE_SMOOTH);
			poster.setIcon(new ImageIcon(img));
		} catch (Exception e) {
			poster.setIcon(new ImageIcon(new BufferedImage(180, 260, BufferedImage.TYPE_INT_RGB)));
		}
		infoPanel.add(poster);
		infoPanel.add(Box.createVerticalStrut(10));
		JLabel nameLabel = new JLabel("<html><b>Tên phim: " + movie.name + "</b></html>");
		nameLabel.setFont(new Font("Arial", Font.BOLD, 22));
		infoPanel.add(nameLabel);
		JLabel genreLabel = new JLabel("Thể loại: " + movie.genre);
		genreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		infoPanel.add(genreLabel);
		JLabel durationLabel = new JLabel("Thời lượng: " + movie.duration);
		durationLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		infoPanel.add(durationLabel);
		JLabel cinemaLabel = new JLabel("Rạp chiếu: " + theater.getName());
		cinemaLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		infoPanel.add(cinemaLabel);
		JLabel dateLabel = new JLabel("Ngày chiếu: " + movie.releaseDate);
		dateLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		infoPanel.add(dateLabel);
		JLabel timeLabel = new JLabel("Giờ chiếu: " + showTime);
		timeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		infoPanel.add(timeLabel);
		JLabel roomLabel = new JLabel("Phòng chiếu: P2");
		roomLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		infoPanel.add(roomLabel);
		selectedSeatsLabel = new JLabel("Ghế ngồi: ...");
		selectedSeatsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		infoPanel.add(selectedSeatsLabel);
		infoPanel.add(Box.createVerticalStrut(20));
		JButton nextBtn = new JButton("TIẾP TỤC");
		nextBtn.setBackground(new Color(33, 150, 243));
		nextBtn.setForeground(Color.WHITE);
		nextBtn.setFont(new Font("Arial", Font.BOLD, 20));
		nextBtn.setFocusPainted(false);
		nextBtn.setPreferredSize(new Dimension(180, 40));
		nextBtn.addActionListener(e -> {
			java.util.List<String> sortedSeats = new java.util.ArrayList<>(selectedSeats);
			java.util.Collections.sort(sortedSeats);
			double total = selectedSeats.size() * price;
			new View.PaymentView(movie, theater, showTime, sortedSeats, total, scheduleId, selectedSeatIds)
					.setVisible(true);
		});
		infoPanel.add(nextBtn);
		mainPanel.add(infoPanel, BorderLayout.EAST);

		// BOTTOM - Tổng tiền
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
		bottomPanel.setBackground(Color.WHITE);
		totalLabel = new JLabel("Tổng tiền: 0 VND");
		totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
		bottomPanel.add(new JLabel("Ghế thường"));
		bottomPanel.add(new JLabel("Ghế đôi"));
		bottomPanel.add(totalLabel);
		bottomPanel.setFont(new Font("Arial", Font.BOLD, 16));
		add(bottomPanel, BorderLayout.SOUTH);
	}

	private JPanel createLegend(Color color, String text) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
		panel.setBackground(Color.WHITE);
		JLabel icon = new JLabel();
		icon.setPreferredSize(new Dimension(28, 18));
		icon.setOpaque(true);
		icon.setBackground(color);
		icon.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		panel.add(icon);
		JLabel label = new JLabel(text);
		label.setFont(new Font("Arial", Font.PLAIN, 13));
		panel.add(label);
		return panel;
	}

	private void updateTotal(boolean isUp, boolean isCoupleSeat) {
		int multiplier = isCoupleSeat ? 2 : 1;
		this.totalPrice += (isUp ? price * multiplier : price * (-1) * multiplier);
		totalLabel.setText("Tổng tiền: " + this.totalPrice + " VND");
		if (selectedSeats.isEmpty()) {
			selectedSeatsLabel.setText("Ghế ngồi: ...");
		} else {
			java.util.List<String> sorted = new java.util.ArrayList<>(selectedSeats);
			java.util.Collections.sort(sorted);
			selectedSeatsLabel.setText("Ghế ngồi: " + String.join(", ", sorted));
		}
	}
}
