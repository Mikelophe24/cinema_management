//package View;
//
//import java.awt.BorderLayout;
//import java.awt.Font;
//import java.util.List;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.JTextField;
//import javax.swing.SwingConstants;
//import javax.swing.table.DefaultTableModel;
//
//import Dao.MovieDao;
//import Model.Account;
//import Model.Movie;
//
//public class MainUserView extends JFrame {
//	private static final long serialVersionUID = -8612786092250355724L;
//	private JTable movieTable;
//	private JTextField txtSearch;
//	private JButton btnSearch, btnBooking, btnLogout;
//	private Account currentAccount;
//
//	public MainUserView(Account account) {
//		this.currentAccount = account;
//		setTitle("Rạp chiếu phim - Xin chào " + account.getUsername());
//		setSize(800, 500);
//		setLocationRelativeTo(null);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//
//		initComponents();
//		loadMovieData();
//	}
//
//	private void initComponents() {
//		JPanel panelMain = new JPanel(new BorderLayout());
//
//		JLabel lblTitle = new JLabel("Danh sách phim đang chiếu", SwingConstants.CENTER);
//		lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
//		panelMain.add(lblTitle, BorderLayout.NORTH);
//
//		movieTable = new JTable();
//		panelMain.add(new JScrollPane(movieTable), BorderLayout.CENTER);
//
//		JPanel panelBottom = new JPanel();
//		txtSearch = new JTextField(20);
//		btnSearch = new JButton("Tìm kiếm");
//		btnBooking = new JButton("Đặt vé");
//		JButton btnMyTickets = new JButton("Xem vé đã đặt");
//		btnLogout = new JButton("Đăng xuất");
//
//		panelBottom.add(new JLabel("Tìm tên phim:"));
//		panelBottom.add(txtSearch);
//		panelBottom.add(btnSearch);
//		panelBottom.add(btnBooking);
//		panelBottom.add(btnLogout);
//		panelBottom.add(btnMyTickets);
//
//		panelMain.add(panelBottom, BorderLayout.SOUTH);
//		setContentPane(panelMain);
//
//		btnSearch.addActionListener(e -> loadMovieData(txtSearch.getText().trim()));
//
//		btnBooking.addActionListener(e -> {
//			int row = movieTable.getSelectedRow();
//			if (row >= 0) {
//				int movieId = Integer.parseInt(movieTable.getValueAt(row, 0).toString());
//				String movieTitle = movieTable.getValueAt(row, 1).toString();
////				new ShowtimeBookingView(account, movieId, movieTitle).setVisible(true);
//			} else {
//				JOptionPane.showMessageDialog(this, "Vui lòng chọn một bộ phim để đặt vé.");
//			}
//		});
//
//		btnMyTickets.addActionListener(e -> {
////			new UserTicketsView(account.getId()).setVisible(true);
//		});
//
//		btnLogout.addActionListener(e -> {
//			int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận",
//					JOptionPane.YES_NO_OPTION);
//			if (confirm == JOptionPane.YES_OPTION) {
//				dispose();
//				new LoginView().setVisible(true);
//			}
//		});
//
//	}
//
//	private void loadMovieData() {
//		loadMovieData("");
//	}
//
//	private void loadMovieData(String keyword) {
//		MovieDao dao = new MovieDao();
//		List<Movie> movies = dao .getMoviesByKeyword(keyword);
//
//		String[] columns = { "STT", "Tên phim", "Thể loại", "Thời lượng" };
//		String[][] data = new String[movies.size()][4];
//
//		for (int i = 0; i < movies.size(); i++) {
//			Movie m = movies.get(i);
//			data[i][0] = String.valueOf(i);
//			data[i][1] = m.getTitle();
//			data[i][2] = m.getGenre();
//			data[i][3] = m.getDuration() + " phút";
//		}
//
//		movieTable.setModel(new DefaultTableModel(data, columns));
//	}
//}
