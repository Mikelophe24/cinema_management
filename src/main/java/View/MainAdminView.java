package View;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import Dao.MovieDao;
import Model.Account;
import Model.Movie;

public class MainAdminView extends JFrame {
	private JTable movieTable;
	private JTextField txtSearch;
	private JButton btnAdd, btnEdit, btnDelete, btnSearch, btnRefresh, btnLogout;
	private JMenuBar menuBar;
	private JMenu menuManage;
	private JMenuItem menuShowtimes, menuTickets, menuUsers, menuReports;

	public MainAdminView(Account account) {
		setTitle("Hệ thống quản lý rạp chiếu phim - ADMIN");
		setSize(900, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		initMenu();
		initComponents();
		loadMovieData();
	}

	private void initMenu() {
		menuBar = new JMenuBar();
		menuManage = new JMenu("Chức năng");

		menuShowtimes = new JMenuItem("Quản lý lịch chiếu");
		menuTickets = new JMenuItem("Quản lý vé");
		menuUsers = new JMenuItem("Quản lý người dùng");
		menuReports = new JMenuItem("Báo cáo doanh thu");

		menuManage.add(menuShowtimes);
		menuManage.add(menuTickets);
		menuManage.add(menuUsers);
		menuManage.add(menuReports);

		menuBar.add(menuManage);
		setJMenuBar(menuBar);

		// Mỗi chức năng hiển thị thông báo (sẽ thay bằng giao diện thực sau)
		menuShowtimes.addActionListener(e -> new ShowtimeManagementView().setVisible(true));
		menuTickets.addActionListener(e -> new TicketManagementView().setVisible(true));
//        menuUsers.addActionListener(e -> JOptionPane.showMessageDialog(this, "Chuyển đến Quản lý người dùng"));
		menuReports.addActionListener(e -> JOptionPane.showMessageDialog(this, "Chuyển đến Báo cáo doanh thu"));

	}

	private void initComponents() {
		JPanel panelMain = new JPanel(new BorderLayout());

		// Tiêu đề
		JLabel lblTitle = new JLabel("Quản lý phim", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
		panelMain.add(lblTitle, BorderLayout.NORTH);

		// Bảng phim
		movieTable = new JTable();
		panelMain.add(new JScrollPane(movieTable), BorderLayout.CENTER);

		// Panel thao tác
		JPanel panelActions = new JPanel();
		txtSearch = new JTextField(20);
		btnSearch = new JButton("Tìm kiếm");
		btnAdd = new JButton("Thêm");
		btnEdit = new JButton("Sửa");
		btnDelete = new JButton("Xóa");
		btnRefresh = new JButton("Làm mới");
		btnLogout = new JButton("Đăng xuất");

		panelActions.add(new JLabel("Tìm tên phim:"));
		panelActions.add(txtSearch);
		panelActions.add(btnSearch);
		panelActions.add(btnAdd);
		panelActions.add(btnEdit);
		panelActions.add(btnDelete);
		panelActions.add(btnRefresh);
		panelActions.add(btnLogout);

		panelMain.add(panelActions, BorderLayout.SOUTH);
		setContentPane(panelMain);

		// Chức năng tìm kiếm đơn giản
		btnSearch.addActionListener(e -> loadMovieData(txtSearch.getText().trim()));

		btnRefresh.addActionListener(e -> {
			txtSearch.setText("");
			loadMovieData();
		});

		btnAdd.addActionListener(e -> {
			JTextField tfTitle = new JTextField();
			JTextField tfGenre = new JTextField();
			JTextField tfDuration = new JTextField();

			Object[] message = { "Tên phim:", tfTitle, "Thể loại:", tfGenre, "Thời lượng (phút):", tfDuration };

			int option = JOptionPane.showConfirmDialog(this, message, "Thêm phim", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				try {
					Movie newMovie = new Movie(0, tfTitle.getText(), tfGenre.getText(),
							Integer.parseInt(tfDuration.getText()));
					MovieDao dao = new MovieDao();
					if (dao.addMovie(newMovie)) {
						JOptionPane.showMessageDialog(this, "Thêm thành công!");
						loadMovieData();
					} else {
						JOptionPane.showMessageDialog(this, "Thêm thất bại!");
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, "Thời lượng phải là số nguyên.");
				}
			}
		});

		btnEdit.addActionListener(e -> {
			int selectedRow = movieTable.getSelectedRow();
			if (selectedRow >= 0) {
				int id = Integer.parseInt(movieTable.getValueAt(selectedRow, 0).toString());
				String currentTitle = movieTable.getValueAt(selectedRow, 1).toString();
				String currentGenre = movieTable.getValueAt(selectedRow, 2).toString();
				String currentDuration = movieTable.getValueAt(selectedRow, 3).toString();

				JTextField tfTitle = new JTextField(currentTitle);
				JTextField tfGenre = new JTextField(currentGenre);
				JTextField tfDuration = new JTextField(currentDuration);

				Object[] message = { "Tên phim:", tfTitle, "Thể loại:", tfGenre, "Thời lượng (phút):", tfDuration };

				int option = JOptionPane.showConfirmDialog(this, message, "Sửa phim", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					try {
						Movie updatedMovie = new Movie(id, tfTitle.getText(), tfGenre.getText(),
								Integer.parseInt(tfDuration.getText()));
						MovieDao dao = new MovieDao();
						if (dao.updateMovie(updatedMovie)) {
							JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
							loadMovieData();
						} else {
							JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
						}
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(this, "Thời lượng phải là số nguyên.");
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn phim để sửa.");
			}
		});

		btnDelete.addActionListener(e -> {
			int selectedRow = movieTable.getSelectedRow();
			if (selectedRow >= 0) {
				int id = Integer.parseInt(movieTable.getValueAt(selectedRow, 0).toString());
				int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa phim này?", "Xác nhận",
						JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					MovieDao dao = new MovieDao();
					if (dao.deleteMovie(id)) {
						JOptionPane.showMessageDialog(this, "Xóa thành công!");
						loadMovieData();
					} else {
						JOptionPane.showMessageDialog(this, "Xóa thất bại!");
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn phim để xóa.");
			}
		});

		menuUsers.addActionListener(e -> new UserManagementView().setVisible(true));

		btnLogout.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				dispose();
				new LoginView().setVisible(true);
			}
		});

	}

	private void loadMovieData() {
		loadMovieData("");
	}

	private void loadMovieData(String keyword) {
		MovieDao dao = new MovieDao();
		List<Movie> movies = dao.getMoviesByKeyword(keyword);

		String[] columns = { "ID", "Tên phim", "Thể loại", "Thời lượng (phút)" };
		String[][] data = new String[movies.size()][4];

		for (int i = 0; i < movies.size(); i++) {
			Movie m = movies.get(i);
			data[i][0] = String.valueOf(m.getId());
			data[i][1] = m.getTitle();
			data[i][2] = m.getGenre();
			data[i][3] = String.valueOf(m.getDuration());
		}

		movieTable.setModel(new DefaultTableModel(data, columns));
	}
}