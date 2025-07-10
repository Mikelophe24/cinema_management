package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Context.AppContext;
import Dao.InvoiceDao;
import Dao.ProductDao;
import Dao.SeatScheduleDao;
import Dao.TicketDao;
import Enum.SeatScheduleEnum;
import Model.Invoice;
import Model.Product;
import Model.Theater;
import Model.Ticket;
import View.MovieSection.ComingSoonView.MovieInfo;

public class PaymentView extends JDialog {
	private JLabel totalLabel;
	private JLabel discountLabel;
	private JLabel payLabel;
	private Double totalAmount;

	public PaymentView(MovieInfo movie, Theater theater, String showTime, List<String> selectedSeats,
			Double totalAmount, int scheduleId, List<Integer> selectedSeatIds) {
		super((Frame) null, "Thanh Toán", true);
		this.totalAmount = totalAmount;
		setSize(1200, 750);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		getContentPane().setBackground(new Color(245, 245, 245)); // Light gray background for a modern look

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(new Color(245, 245, 245));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		add(mainPanel, BorderLayout.CENTER);

		// Left: Payment info, combo, and payment methods
		JPanel leftContentPanel = new JPanel();
		leftContentPanel.setLayout(new BoxLayout(leftContentPanel, BoxLayout.Y_AXIS));
		leftContentPanel.setBackground(new Color(245, 245, 245));

		// Payment info
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setBackground(Color.WHITE);
		infoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(33, 150, 243)),
				"THÔNG TIN THANH TOÁN", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(33, 150, 243)));
		infoPanel.add(Box.createVerticalStrut(15));

		// User info
		JTextField nameField = new JTextField("", 15);
		JTextField phoneField = new JTextField("", 15);
		JPanel row1 = new JPanel(new GridLayout(1, 3, 20, 0));
		row1.setBackground(Color.WHITE);
		row1.add(createInfoField("Họ Tên:", nameField));
		row1.add(createInfoField("Số điện thoại:", phoneField));
		infoPanel.add(row1);
		infoPanel.add(Box.createVerticalStrut(15));

		// Seat info
		JPanel row2 = new JPanel(new BorderLayout(10, 0));
		row2.setBackground(Color.WHITE);
		JLabel seatType = new JLabel("GHẾ:");
		seatType.setFont(new Font("Arial", Font.BOLD, 16));
		row2.add(seatType, BorderLayout.WEST);
		JLabel seatCalc = new JLabel(selectedSeats.size() + " x " + totalAmount / selectedSeats.size());
		seatCalc.setFont(new Font("Arial", Font.PLAIN, 16));
		row2.add(seatCalc, BorderLayout.CENTER);
		JLabel seatTotal = new JLabel("= " + totalAmount + " vnd");
		seatTotal.setFont(new Font("Arial", Font.BOLD, 16));
		row2.add(seatTotal, BorderLayout.EAST);
		infoPanel.add(row2);
		infoPanel.add(Box.createVerticalStrut(10));
		infoPanel.add(new JSeparator());

		// Combo section
		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.Y_AXIS));
		comboPanel.setBackground(Color.WHITE);
		comboPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(33, 150, 243)),
				"COMBO ƯU ĐÃI", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(33, 150, 243)));

		// Combo header
		JPanel header = new JPanel(new GridLayout(1, 4));
		header.setBackground(Color.WHITE);
		header.add(new JLabel("")); // Image placeholder
		JLabel tenCombo = new JLabel("Tên Combo");
		tenCombo.setFont(new Font("Arial", Font.BOLD, 16));
		header.add(tenCombo);
		JLabel moTa = new JLabel("Mô tả");
		moTa.setFont(new Font("Arial", Font.BOLD, 16));
		header.add(moTa);
		JLabel soLuong = new JLabel("Số lượng");
		soLuong.setFont(new Font("Arial", Font.BOLD, 16));
		header.add(soLuong);
		comboPanel.add(header);
		comboPanel.add(new JSeparator());

		List<Product> products = ProductDao.queryList();
		Double[] comboPrices = new Double[products.size()];
		for (int i = 0; i < products.size(); i++) {
			comboPrices[i] = products.get(i).getPrice().doubleValue();
		}
		final int[] comboQuantities = new int[products.size()];
		int index = 0;
		for (Product product : products) {
			comboPanel.add(createComboRowV2(product.getName(), product.getDescription(), 0, product.getImage(),
					product.getPrice(), comboQuantities, index));
			index++;
		}

		// Total amount
		JPanel totalPanel = new JPanel(new GridBagLayout());
		totalPanel.setBackground(Color.WHITE);
		GridBagConstraints tgbc = new GridBagConstraints();
		tgbc.insets = new Insets(5, 10, 5, 10);
		tgbc.anchor = GridBagConstraints.EAST;
		tgbc.gridx = 0;
		tgbc.gridy = 0;
		JLabel tongTienLabel = new JLabel("Tổng tiền:");
		tongTienLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		totalPanel.add(tongTienLabel, tgbc);
		tgbc.gridx = 1;
		totalLabel = new JLabel(totalAmount + " vnd");
		totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
		totalLabel.setForeground(new Color(200, 0, 0));
		totalPanel.add(totalLabel, tgbc);
		tgbc.gridx = 0;
		tgbc.gridy = 1;
		JLabel giamLabel = new JLabel("Số tiền được giảm:");
		giamLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		totalPanel.add(giamLabel, tgbc);
		tgbc.gridx = 1;
		discountLabel = new JLabel("0 vnd");
		discountLabel.setFont(new Font("Arial", Font.BOLD, 16));
		discountLabel.setForeground(new Color(200, 0, 0));
		totalPanel.add(discountLabel, tgbc);
		tgbc.gridx = 0;
		tgbc.gridy = 2;
		JLabel payLabel1 = new JLabel("Số tiền cần thanh toán:");
		payLabel1.setFont(new Font("Arial", Font.PLAIN, 16));
		totalPanel.add(payLabel1, tgbc);
		tgbc.gridx = 1;
		payLabel = new JLabel(totalAmount + " vnd");
		payLabel.setFont(new Font("Arial", Font.BOLD, 16));
		payLabel.setForeground(new Color(200, 0, 0));
		totalPanel.add(payLabel, tgbc);

		// Payment methods
		JPanel payPanel = new JPanel();
		payPanel.setLayout(new BoxLayout(payPanel, BoxLayout.Y_AXIS));
		payPanel.setBackground(Color.WHITE);
		payPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(33, 150, 243)),
				"PHƯƠNG THỨC THANH TOÁN", 0, 0, new Font("Arial", Font.BOLD, 16), new Color(33, 150, 243)));
		JPanel methodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
		methodPanel.setBackground(Color.WHITE);
		ButtonGroup group = new ButtonGroup();

		String[] methods = { "Tiền mặt", "Chuyển khoản" };
		JRadioButton[] methodButtons = new JRadioButton[methods.length];
		int[] selectedMethod = { 0 }; // 0: Tiền mặt, 1: Chuyển khoản
		for (int i = 0; i < methods.length; i++) {
			JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
			item.setBackground(Color.WHITE);
			JRadioButton rb = new JRadioButton();
			rb.setBackground(Color.WHITE);
			group.add(rb);
			methodButtons[i] = rb;
			if (i == 0)
				rb.setSelected(true);
			int idx = i;
			rb.addActionListener(e -> selectedMethod[0] = idx);
			JLabel label = new JLabel(methods[i]);
			label.setFont(new Font("Arial", Font.PLAIN, 14));
			item.add(rb);
			item.add(label);
			methodPanel.add(item);
		}
		payPanel.add(methodPanel);

		// Continue button
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnPanel.setBackground(new Color(245, 245, 245));
		JButton nextBtn = new JButton("THANH TOÁN");
		nextBtn.setBackground(new Color(33, 150, 243));
		nextBtn.setForeground(Color.WHITE);
		nextBtn.setFont(new Font("Arial", Font.BOLD, 16));
		nextBtn.setPreferredSize(new Dimension(120, 40));
		nextBtn.setFocusPainted(false);
		nextBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// Lưu các tham số truyền vào để dùng khi mở BillView hoặc QRView
		final String _movieName = movie.name;
		final String _genre = movie.genre;
		final String _duration = movie.duration;
		final String _cinemaName = theater != null ? theater.getName() : "";
		final String _showDate = movie.releaseDate;
		final String _showTime = showTime;
		final java.util.List<String> _selectedSeats = selectedSeats;
		final Double _totalAmount = totalAmount;

		// Lấy tham chiếu đến các trường nhập Họ Tên và Số điện thoại
		// JPanel row1 = (JPanel) ((JPanel) ((JPanel) ((JPanel)
		// leftContentPanel.getComponent(0)).getComponent(0)).getComponent(0));
		// JTextField nameField = (JTextField) ((JPanel)
		// row1.getComponent(0)).getComponent(1);
		// JTextField phoneField = (JTextField) ((JPanel)
		// row1.getComponent(1)).getComponent(1);
		nextBtn.addActionListener(e -> {
			String customerName = nameField.getText().trim();
			String customerPhone = phoneField.getText().trim();
			// Kiểm tra nhập tên và số điện thoại
			if (customerName.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập Họ Tên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				nameField.requestFocus();
				return;
			}
			if (customerPhone.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập Số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				phoneField.requestFocus();
				return;
			}
			if (!customerPhone.matches("\\d{10,12}")) {
				JOptionPane.showMessageDialog(this, "Số điện thoại phải là 10 đến 12 chữ số!", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
				phoneField.requestFocus();
				return;
			}
			// Lấy số tiền cần thanh toán thực tế từ payLabel
			Double payAmount = (double) 0;
			try {
				String payText = payLabel.getText().replace(" VND", "").replace(",", "").trim();
				payAmount = Double.parseDouble(payText);
			} catch (Exception ex) {
				payAmount = totalAmount;
			}
			// Tính tổng tiền gốc = tiền ghế + tổng tiền combo
			Double tongTienAmount = totalAmount;
			for (int i = 0; i < comboQuantities.length; i++) {
				tongTienAmount += comboQuantities[i] * comboPrices[i];
			}

			Invoice invoice = InvoiceDao.create(new Invoice(0, AppContext.currentAccount.getAccountId(), scheduleId,
					tongTienAmount, LocalDate.now()));

			if (invoice != null) {
				for (Integer seatId : selectedSeatIds) {
					Ticket ticket = TicketDao.create(new Ticket(0, AppContext.currentAccount.getAccountId(), scheduleId,
							1, invoice.getId(), seatId));

					Map<String, Object> updateFields = new HashMap<>();
					updateFields.put("status", SeatScheduleEnum.Status.SOLD.name());
					SeatScheduleDao.update(seatId, scheduleId, updateFields);
				}

				for (int i = 0; i < products.size(); i++) {
					if (comboQuantities[i] > 0) {
						int comboId = products.get(i).getId();
						int quantity = comboQuantities[i];
						InvoiceDao.createOrderProductDetail(invoice.getId(), comboId, quantity);
					}
				}

				if (selectedMethod[0] == 1) { // Chuyển khoản
					new QRView(this, _movieName, _genre, _duration, _cinemaName, _showDate, _showTime, _selectedSeats,
							tongTienAmount, payAmount, customerName, customerPhone).setVisible(true);
				} else {
					// Hiện hóa đơn khi thanh toán tiền mặt, truyền đúng dữ liệu thực tế
					new BillView(this, _movieName, _genre, _duration, _cinemaName, _showDate, _showTime, _selectedSeats,
							tongTienAmount, payAmount, "Tiền mặt", customerName, customerPhone).setVisible(true);
				}
			}
		});
		btnPanel.add(nextBtn);

		// Add to left content panel
		leftContentPanel.add(infoPanel);
		leftContentPanel.add(Box.createVerticalStrut(20));
		leftContentPanel.add(comboPanel);
		leftContentPanel.add(Box.createVerticalStrut(20));
		leftContentPanel.add(totalPanel);
		leftContentPanel.add(Box.createVerticalStrut(20));
		leftContentPanel.add(payPanel);
		leftContentPanel.add(Box.createVerticalStrut(10));

		// Wrap left content in a scroll pane
		mainPanel.add(leftContentPanel, BorderLayout.CENTER);

		// Right: Movie info
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setBackground(Color.WHITE);
		rightPanel.setPreferredSize(new Dimension(320, 0));
		rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		JLabel poster = new JLabel();
		poster.setPreferredSize(new Dimension(180, 260));
		try {
			ImageIcon icon = new ImageIcon(new URL(movie.imageUrl));
			Image img = icon.getImage().getScaledInstance(180, 260, Image.SCALE_SMOOTH);
			poster.setIcon(new ImageIcon(img));
		} catch (Exception e) {
			BufferedImage placeholder = new BufferedImage(180, 260, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = placeholder.createGraphics();
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fillRect(0, 0, 180, 260);
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("Arial", Font.PLAIN, 14));
			g2d.drawString("No Image", 50, 130);
			g2d.dispose();
			poster.setIcon(new ImageIcon(placeholder));
		}
		rightPanel.add(poster);
		rightPanel.add(Box.createVerticalStrut(15));
		rightPanel.add(createInfoLabel("Tên phim:", movie.name));
		rightPanel.add(createInfoLabel("Thể loại:", movie.genre));
		rightPanel.add(createInfoLabel("Thời lượng:", movie.duration));
		rightPanel.add(createInfoLabel("Rạp chiếu:", theater != null ? theater.getName() : ""));
		rightPanel.add(createInfoLabel("Ngày chiếu:", movie.releaseDate));
		rightPanel.add(createInfoLabel("Giờ chiếu:", showTime));
		rightPanel.add(createInfoLabel("Ghế ngồi:", String.join(", ", selectedSeats)));
		rightPanel.add(Box.createVerticalStrut(20));
		rightPanel.add(btnPanel);
		mainPanel.add(rightPanel, BorderLayout.EAST);

		// Bottom: chỉ hiển thị thời gian còn lại ở góc trái
		// JPanel bottomPanel = new JPanel(new BorderLayout());
		// bottomPanel.setBackground(new Color(245, 245, 245));
		// bottomPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		// JLabel timerLabel = new JLabel("10:00");
		// timerLabel.setFont(new Font("Arial", Font.BOLD, 32));
		// timerLabel.setForeground(new Color(33, 150, 243));
		// bottomPanel.add(timerLabel, BorderLayout.WEST);
		// add(bottomPanel, BorderLayout.SOUTH);

		// Timer logic
		// final int[] timeLeft = {10 * 60};
		// new javax.swing.Timer(1000, e -> {
		// timeLeft[0]--;
		// int min = timeLeft[0] / 60;
		// int sec = timeLeft[0] % 60;
		// timerLabel.setText(String.format("%d:%02d", min, sec));
		// if (timeLeft[0] <= 0) {
		// ((javax.swing.Timer) e.getSource()).stop();
		// timerLabel.setText("0:00");
		// JOptionPane.showMessageDialog(this, "Hết thời gian giữ vé!", "Thông báo",
		// JOptionPane.WARNING_MESSAGE);
		// dispose();
		// }
		// }).start();
	}

	private JPanel createInfoLabel(String label, String value) {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		p.setBackground(Color.WHITE);
		JLabel l1 = new JLabel(label);
		l1.setFont(new Font("Arial", Font.BOLD, 14));
		JLabel l2 = new JLabel(value);
		l2.setFont(new Font("Arial", Font.PLAIN, 14));
		p.add(l1);
		p.add(l2);
		return p;
	}

	private JPanel createInfoField(String label, JTextField field) {
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		p.setBackground(Color.WHITE);
		JLabel l1 = new JLabel(label);
		l1.setFont(new Font("Arial", Font.BOLD, 14));
		field.setFont(new Font("Arial", Font.PLAIN, 14));
		field.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		p.add(l1);
		p.add(field);
		return p;
	}

	private JPanel createComboRowV2(String name, String desc, int qty, String imgUrl, Double price,
			int[] comboQuantities, int comboIndex) {
		JPanel row = new JPanel(new GridLayout(1, 4));
		row.setBackground(Color.WHITE);
		row.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// Image
		JLabel img = new JLabel();
		img.setHorizontalAlignment(SwingConstants.CENTER);
		img.setPreferredSize(new Dimension(60, 60));
		try {
			ImageIcon icon = new ImageIcon(new URL(imgUrl));
			Image image = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			img.setIcon(new ImageIcon(image));
		} catch (Exception e) {
			img.setBackground(Color.LIGHT_GRAY);
			img.setOpaque(true);
		}
		row.add(img);

		// Combo name
		JLabel nameLabel = new JLabel("<html><b>" + name + "</b></html>");
		nameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		row.add(nameLabel);

		// Description
		JLabel descLabel = new JLabel("<html>" + desc + "</html>");
		descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		row.add(descLabel);

		// Quantity
		JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		qtyPanel.setBackground(Color.WHITE);
		JButton minus = new JButton("-");
		minus.setPreferredSize(new Dimension(32, 32));
		JButton plus = new JButton("+");
		plus.setPreferredSize(new Dimension(32, 32));
		JLabel qtyLabel = new JLabel(String.valueOf(qty));
		qtyLabel.setFont(new Font("Arial", Font.BOLD, 16));
		plus.setBackground(new Color(33, 150, 243));
		plus.setForeground(Color.WHITE);
		minus.setBackground(new Color(200, 200, 210));
		minus.setForeground(Color.DARK_GRAY);
		plus.setFocusPainted(false);
		minus.setFocusPainted(false);
		plus.setBorder(BorderFactory.createLineBorder(new Color(33, 150, 243)));
		minus.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 210)));

		plus.addActionListener(e -> {
			int val = Integer.parseInt(qtyLabel.getText());
			qtyLabel.setText(String.valueOf(val + 1));
			comboQuantities[comboIndex] = val + 1;
			updateTotal(price);
		});
		minus.addActionListener(e -> {
			int val = Integer.parseInt(qtyLabel.getText());
			if (val > 0) {
				qtyLabel.setText(String.valueOf(val - 1));
				comboQuantities[comboIndex] = val - 1;
				updateTotal(-price);
			}
		});
		qtyPanel.add(qtyLabel);
		qtyPanel.add(plus);
		qtyPanel.add(minus);
		row.add(qtyPanel);

		return row;
	}

	private void updateTotal(Double amount) {
		totalAmount += amount;
		totalLabel.setText(totalAmount + " vnd");
		payLabel.setText(totalAmount + " vnd");
	}
}