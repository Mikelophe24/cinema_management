package View;

import Dao.MovieDao;
import Dao.ShowtimeDao;
import Model.Movie;
import Model.Showtime;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShowtimeManagementView extends JFrame {
    private JTable table;
    private JButton btnAdd, btnDelete, btnRefresh, btnEdit;
    private JComboBox<Movie> comboMovies;
    private JTextField txtDate, txtTime, txtRoom;

    public ShowtimeManagementView() {
        setTitle("Quản lý lịch chiếu");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        loadShowtimes();
        loadMovies();
    }

    private void initComponents() {
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panelInput = new JPanel(new GridLayout(2, 4, 10, 10));
        comboMovies = new JComboBox<>();
        txtDate = new JTextField("2025-06-15");
        txtTime = new JTextField("18:00:00");
        txtRoom = new JTextField();

        panelInput.add(new JLabel("Phim:"));
        panelInput.add(comboMovies);
        panelInput.add(new JLabel("Ngày (yyyy-MM-dd):"));
        panelInput.add(txtDate);
        panelInput.add(new JLabel("Giờ (HH:mm:ss):"));
        panelInput.add(txtTime);
        panelInput.add(new JLabel("Phòng:"));
        panelInput.add(txtRoom);

        JPanel panelButtons = new JPanel();
        btnAdd = new JButton("Thêm");
        btnDelete = new JButton("Xóa");
        btnRefresh = new JButton("Làm mới");
        btnEdit = new JButton("Sửa");


        panelButtons.add(btnAdd);
        panelButtons.add(btnDelete);
        panelButtons.add(btnRefresh);
        panelButtons.add(btnEdit);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panelInput, BorderLayout.NORTH);
        add(panelButtons, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addShowtime());
        btnDelete.addActionListener(e -> deleteShowtime());
        btnRefresh.addActionListener(e -> loadShowtimes());
        btnEdit.addActionListener(e -> editShowtime());
        
        
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0 && table.getSelectedRowCount() == 1) {
                // Gán các ô nhập
                txtDate.setText(table.getValueAt(row, 2).toString());
                txtTime.setText(table.getValueAt(row, 3).toString());
                txtRoom.setText(table.getValueAt(row, 4).toString());

                // Tìm phim theo tên và set vào comboMovies
                String movieTitle = table.getValueAt(row, 1).toString();
                for (int i = 0; i < comboMovies.getItemCount(); i++) {
                    Movie m = comboMovies.getItemAt(i);
                    if (m.getTitle().equalsIgnoreCase(movieTitle)) {
                        comboMovies.setSelectedIndex(i);
                        break;
                    }
                }
            }
        });

    }

    private void loadMovies() {
        MovieDao movieDao = new MovieDao();
        List<Movie> movies = movieDao.getMoviesByKeyword(""); // all
        comboMovies.removeAllItems();
        for (Movie m : movies) {
            comboMovies.addItem(m);
        }
    }

    private void loadShowtimes() {
        List<Showtime> list = new ShowtimeDao().getAllShowtimes();
        String[] cols = {"ID", "Phim", "Ngày", "Giờ", "Phòng"};
        String[][] data = new String[list.size()][5];

        for (int i = 0; i < list.size(); i++) {
            Showtime s = list.get(i);
            data[i][0] = String.valueOf(s.getId());
            data[i][1] = s.getMovieTitle();
            data[i][2] = s.getDate();
            data[i][3] = s.getTime();
            data[i][4] = s.getRoom();
        }

        table.setModel(new DefaultTableModel(data, cols));
    }
            private void editShowtime() {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = Integer.parseInt(table.getValueAt(row, 0).toString());
                Movie selectedMovie = (Movie) comboMovies.getSelectedItem();

                if (selectedMovie == null) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn phim.");
                    return;
                }

                Showtime s = new Showtime();
                s.setId(id);
                s.setMovieId(selectedMovie.getId());
                s.setDate(txtDate.getText().trim());
                s.setTime(txtTime.getText().trim());
                s.setRoom(txtRoom.getText().trim());

                boolean success = new ShowtimeDao().updateShowtime(s);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công.");
                    loadShowtimes();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại.");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn lịch chiếu cần sửa.");
            }
        }


    private void addShowtime() {
        Movie selected = (Movie) comboMovies.getSelectedItem();
        if (selected == null) return;

        Showtime s = new Showtime();
        s.setMovieId(selected.getId());
        s.setDate(txtDate.getText().trim());
        s.setTime(txtTime.getText().trim());
        s.setRoom(txtRoom.getText().trim());

        boolean success = new ShowtimeDao().addShowtime(s);
        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            loadShowtimes();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }
    
    

    private void deleteShowtime() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = Integer.parseInt(table.getValueAt(row, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = new ShowtimeDao().deleteShowtime(id);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    loadShowtimes();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa.");
        }
    }
}
