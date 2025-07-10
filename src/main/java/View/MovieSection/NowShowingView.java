package View.MovieSection;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class NowShowingView extends JPanel {
	private static final long serialVersionUID = 3884301657752189949L;
	private List<NowShowingMovieInfo> movies;
	private JPanel listPanel;

	public NowShowingView(List<NowShowingMovieInfo> movieList) {
		this.movies = movieList;
		setBackground(Color.WHITE);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 20, 10, 20);
		gbc.gridy = 0;

		// Panel chứa các hàng phim
		listPanel = new JPanel();
		listPanel.setBackground(Color.WHITE);
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(listPanel, gbc);

		// Load & render phim
		loadMovies(movieList);
		renderMovies();
	}

	private void loadMovies(List<NowShowingMovieInfo> movieList) {
		this.movies = movieList;
	}

	private void renderMovies() {
		listPanel.removeAll();

		int filmsPerRow = 4;
		for (int i = 0; i < movies.size(); i += filmsPerRow) {
			JPanel rowPanel = new JPanel();
			rowPanel.setBackground(Color.WHITE);
			rowPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));
			rowPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
			for (int j = i; j < i + filmsPerRow && j < movies.size(); j++) {
				rowPanel.add(createMovieCard(movies.get(j)));
			}
			listPanel.add(rowPanel);
		}

		listPanel.revalidate();
		listPanel.repaint();
	}

	private JPanel createMovieCard(NowShowingMovieInfo movie) {
		JPanel card = new JPanel();
		card.setPreferredSize(new Dimension(240, 440));
		card.setBackground(Color.WHITE);
		card.setLayout(null);
		card.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
						BorderFactory.createEmptyBorder(10, 10, 10, 10) // padding trong card
				));

		// Poster
		JLabel poster = new JLabel();
		try {
			ImageIcon icon = new ImageIcon(new java.net.URL(movie.imageUrl));
			Image img = icon.getImage().getScaledInstance(220, 310, Image.SCALE_SMOOTH);
			poster.setIcon(new ImageIcon(img));
		} catch (Exception e) {
			poster.setText("Không tải được ảnh");
		}
		poster.setBounds(10, 10, 220, 310);
		card.add(poster);

		// Tên phim
		JLabel name = new JLabel("<html><b>" + movie.name + "</b></html>");
		name.setFont(new Font("Arial", Font.BOLD, 16));
		name.setForeground(new Color(33, 150, 243));
		name.setBounds(15, 330, 210, 22);
		card.add(name);

		// Thể loại
		JLabel genre = new JLabel("<html><b>Thể loại:</b> " + movie.genre + "</html>");
		genre.setFont(new Font("Arial", Font.PLAIN, 13));
		genre.setBounds(15, 355, 210, 18);
		card.add(genre);

		// Thời lượng
		JLabel duration = new JLabel("<html><b>Thời lượng:</b> " + movie.duration + "</html>");
		duration.setFont(new Font("Arial", Font.PLAIN, 13));
		duration.setBounds(15, 375, 210, 18);
		card.add(duration);

		// Nút mua vé
		JButton buyBtn = new JButton("MUA VÉ");
		buyBtn.setBackground(new Color(33, 150, 243));
		buyBtn.setForeground(Color.WHITE);
		buyBtn.setFont(new Font("Arial", Font.BOLD, 15));
		buyBtn.setFocusPainted(false);
		buyBtn.setBounds(45, 415, 150, 30);
		buyBtn.addActionListener(e -> {
			showBookingModal(movie);
		});
		card.add(buyBtn);
		return card;
	}

	private void showBookingModal(NowShowingMovieInfo movie) {
		JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Đặt Vé - " + movie.name, true);
		dialog.setSize(500, 300);
		dialog.setLocationRelativeTo(null); // căn giữa màn hình
		dialog.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Map tỉnh -> danh sách rạp
		java.util.Map<String, String[]> rapMap = new java.util.HashMap<>();
		rapMap.put("Hà Nội", new String[] { "Beta Thanh Xuân", "Beta Mỹ Đình", "Beta Đan Phượng" });
		rapMap.put("TP. Hồ Chí Minh", new String[] { "Beta Quang Trung", "Beta Hồ Tràm" });
		rapMap.put("Đồng Nai", new String[] { "Beta Biên Hòa", "Beta Long Khánh" });
		rapMap.put("Khánh Hòa", new String[] { "Beta Nha Trang" });
		rapMap.put("Thái Nguyên", new String[] { "Beta Thái Nguyên" });

		// Label chọn tỉnh
		gbc.gridx = 0;
		gbc.gridy = 0;
		dialog.add(new JLabel("Chọn tỉnh:"), gbc);

		// ComboBox tỉnh
		String[] provinces = rapMap.keySet().toArray(new String[0]);
		JComboBox<String> provinceCombo = new JComboBox<>(provinces);
		gbc.gridx = 1;
		dialog.add(provinceCombo, gbc);

		// Label cụm rạp
		gbc.gridx = 0;
		gbc.gridy = 1;
		dialog.add(new JLabel("Chọn rạp:"), gbc);

		// ComboBox rạp
		JComboBox<String> cinemaCombo = new JComboBox<>();
		gbc.gridx = 1;
		dialog.add(cinemaCombo, gbc);

		// Cập nhật rạp ban đầu
		String selectedProvince = (String) provinceCombo.getSelectedItem();
		if (selectedProvince != null) {
			for (String rap : rapMap.getOrDefault(selectedProvince, new String[] {})) {
				cinemaCombo.addItem(rap);
			}
		}

		// Sự kiện khi chọn tỉnh → cập nhật danh sách rạp
		provinceCombo.addActionListener(e -> {
			String province = (String) provinceCombo.getSelectedItem();
			cinemaCombo.removeAllItems();
			for (String rap : rapMap.getOrDefault(province, new String[] {})) {
				cinemaCombo.addItem(rap);
			}
		});

		// Nút xác nhận
		JButton confirmBtn = new JButton("Tiếp tục");
		confirmBtn.setBackground(new Color(33, 150, 243));
		confirmBtn.setForeground(Color.WHITE);
		confirmBtn.setFont(new Font("Arial", Font.BOLD, 14));
		confirmBtn.setFocusPainted(false);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		dialog.add(confirmBtn, gbc);

		confirmBtn.addActionListener(e -> {
			String province = (String) provinceCombo.getSelectedItem();
			String cinema = (String) cinemaCombo.getSelectedItem();
			if (province != null && cinema != null) {
				dialog.dispose();
				// Truyền ngày khởi chiếu, thể loại, thời lượng, imageUrl vào ShowtimeDialogs
				new View.ModalViewNowShowing.ShowtimeDialogs((Frame) SwingUtilities.getWindowAncestor(this), movie.name,
						cinema, movie.date, movie.genre, movie.duration, movie.imageUrl).setVisible(true);
			}
		});

		dialog.setVisible(true);
	}

	public static class NowShowingMovieInfo {
		String imageUrl, name, genre, duration, date;

		public NowShowingMovieInfo(String imageUrl, String name, String genre, String duration, String date) {
			this.imageUrl = imageUrl;
			this.name = name;
			this.genre = genre;
			this.duration = duration;
			this.date = date;
		}
	}
}
