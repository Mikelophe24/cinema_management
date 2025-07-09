package View.Admin;

import Dao.MovieDao;
import Dao.MovieGenreDao;
import Model.Movie;
import Model.MovieGenre;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class MoviesPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    private JTextField txtTitle, txtCountry, txtYear, txtDuration, txtDirector, txtRating, txtVotes, txtPoster;
    private JTextArea txtDescription;
    private JList<String> lstGenres;

    private JComboBox<String> cmbGenres, cmbSearchGenres;
    private JButton btnAdd, btnEdit, btnDelete, btnRefresh;
    private Map<String, Integer> genreNameToId = new HashMap<>();

    private static final String POSTER_FOLDER_PATH = "assets/img/posters/";

    public MoviesPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 248, 255));

        JLabel titleLabel = new JLabel("Quản lý phim", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(33, 111, 255));
        titleLabel.setBorder(new EmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Tên phim", "Quốc gia", "Năm", "Thời lượng", "Đạo diễn", "Rating", "Vote", "Poster", "Mô tả", "Thể loại"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(24);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(new Color(245, 248, 255));

        JTextField txtSearchTitle = new JTextField(12);
        JTextField txtSearchCountry = new JTextField(12);
        cmbSearchGenres = new JComboBox<>();

        cmbSearchGenres.removeAllItems();
        cmbSearchGenres.addItem("Tất cả");
        for (MovieGenre g : MovieGenreDao.queryList()) {
            cmbSearchGenres.addItem(g.getGenreName());
        }


        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(200, 220, 255));
        btnSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        btnSearch.addActionListener(e -> {
            String title = txtSearchTitle.getText().trim();
            String country = txtSearchCountry.getText().trim();
            String genre = cmbSearchGenres.getSelectedItem().toString();
            searchMovies(title, country, genre);
        });

        searchPanel.add(new JLabel("Tên phim:"));
        searchPanel.add(txtSearchTitle);
        searchPanel.add(new JLabel("Quốc gia:"));
        searchPanel.add(txtSearchCountry);
        searchPanel.add(new JLabel("Thể loại:"));
        searchPanel.add(cmbSearchGenres);
        searchPanel.add(btnSearch);

        add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);

        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.setBorder(new EmptyBorder(10, 20, 10, 20));
        add(scrollTable, BorderLayout.CENTER);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new CompoundBorder(
                new EmptyBorder(20, 20, 20, 20),
                new TitledBorder(new LineBorder(new Color(200, 200, 255)), "Thông tin phim", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14))
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        txtTitle = new JTextField(); txtCountry = new JTextField(); txtYear = new JTextField();
        txtDuration = new JTextField(); txtDirector = new JTextField(); txtRating = new JTextField();
        txtVotes = new JTextField(); txtPoster = new JTextField();
        txtDescription = new JTextArea(3, 20);
        txtDescription.setLineWrap(true); txtDescription.setWrapStyleWord(true);
        List<MovieGenre> genreObjs = MovieGenreDao.queryList();
        List<String> genreNames = new ArrayList<>();
        for (MovieGenre g : genreObjs) {
            genreNames.add(g.getGenreName());
        }
        String[] genres = genreNames.toArray(new String[0]); // ✅ An toàn!

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("Tên phim:"), gbc);
        gbc.gridx = 1; formPanel.add(txtTitle, gbc);
        gbc.gridx = 2; formPanel.add(new JLabel("Quốc gia:"), gbc);
        gbc.gridx = 3; formPanel.add(txtCountry, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("Năm phát hành:"), gbc);
        gbc.gridx = 1; formPanel.add(txtYear, gbc);
        gbc.gridx = 2; formPanel.add(new JLabel("Thời lượng (phút):"), gbc);
        gbc.gridx = 3; formPanel.add(txtDuration, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("Đạo diễn:"), gbc);
        gbc.gridx = 1; formPanel.add(txtDirector, gbc);
        gbc.gridx = 2; formPanel.add(new JLabel("Rating:"), gbc);
        gbc.gridx = 3; formPanel.add(txtRating, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("Số lượt vote:"), gbc);
        gbc.gridx = 1; formPanel.add(txtVotes, gbc);
        gbc.gridx = 2; formPanel.add(new JLabel("Thể loại:"), gbc);
        cmbGenres = new JComboBox<>();
        List<MovieGenre> genreList = MovieGenreDao.queryList();
        for (MovieGenre genre : genreList) {
            cmbGenres.addItem(genre.getGenreName());
            genreNameToId.put(genre.getGenreName(), genre.getId());  // <--- BỔ SUNG DÒNG NÀY
        }
        gbc.gridx = 3; formPanel.add(cmbGenres, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("Mô tả:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        formPanel.add(new JScrollPane(txtDescription), gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        formPanel.add(new JLabel("Poster (file name):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        formPanel.add(txtPoster, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xoá");
        btnRefresh = new JButton("Làm mới");

        for (JButton btn : new JButton[]{btnAdd, btnEdit, btnDelete, btnRefresh}) {
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            btn.setBackground(new Color(225, 235, 255));
            btn.setFocusPainted(false);
            buttonPanel.add(btn);
        }

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Events
        loadMovies();

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) showSelectedMovie();
        });

        btnRefresh.addActionListener(e -> clearForm());
        btnAdd.addActionListener(e -> openAddMovieDialog());
        btnEdit.addActionListener(e -> updateSelectedMovie());
        btnDelete.addActionListener(e -> deleteSelectedMovie());
    }

    private void loadMovies() {
        model.setRowCount(0);
        List<MovieDao.MovieWithGenres> list = MovieDao.queryList();
        for (MovieDao.MovieWithGenres m : list) {
            Movie mv = m.getMovie();
            MovieGenre[] genreArray = m.getGenres();
            String[] genreNames = Arrays.stream(genreArray)
                    .map(MovieGenre::getName)
                    .toArray(String[]::new);
            String genres = String.join(", ", genreNames);
            String shortDescription = mv.getDescription();
            if (shortDescription != null && shortDescription.length() > 50) {
                shortDescription = shortDescription.substring(0, 47) + "...";
            }
            model.addRow(new Object[]{
                    mv.getId(), mv.getTitle(), mv.getCountry(), mv.getReleaseYear(),
                    mv.getDuration(), mv.getDirector(), mv.getRating(), mv.getVoteCount(),
                    mv.getPoster(), shortDescription, genres
            });
        }
    }

    private void showSelectedMovie() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtTitle.setText(model.getValueAt(row, 1).toString());
            txtCountry.setText(model.getValueAt(row, 2).toString());
            txtYear.setText(model.getValueAt(row, 3).toString());
            txtDuration.setText(model.getValueAt(row, 4).toString());
            txtDirector.setText(model.getValueAt(row, 5).toString());
            txtRating.setText(model.getValueAt(row, 6).toString());
            txtVotes.setText(model.getValueAt(row, 7).toString());
            txtPoster.setText(model.getValueAt(row, 8).toString());
            txtDescription.setText(model.getValueAt(row, 9).toString());
            cmbGenres.setSelectedItem(model.getValueAt(row, 10).toString().split(", ")[0]);
        }
    }

    private void searchMovies(String title, String country, String genreFilter) {
        model.setRowCount(0);
        List<MovieDao.MovieWithGenres> list = MovieDao.queryList();

        for (MovieDao.MovieWithGenres m : list) {
            Movie mv = m.getMovie();
            Set<String> genreNames = Arrays.stream(m.getGenres())
                    .map(MovieGenre::getName)
                    .collect(Collectors.toSet());


            boolean matchTitle = title.isEmpty() || mv.getTitle().toLowerCase().contains(title.toLowerCase());
            boolean matchCountry = country.isEmpty() || mv.getCountry().toLowerCase().contains(country.toLowerCase());
            boolean matchGenre = genreFilter.equals("Tất cả") || genreNames.contains(genreFilter);

            if (matchTitle && matchCountry && matchGenre) {
                String shortDesc = mv.getDescription();
                if (shortDesc != null && shortDesc.length() > 50) {
                    shortDesc = shortDesc.substring(0, 47) + "...";
                }
                model.addRow(new Object[]{
                        mv.getId(), mv.getTitle(), mv.getCountry(), mv.getReleaseYear(),
                        mv.getDuration(), mv.getDirector(), mv.getRating(), mv.getVoteCount(),
                        mv.getPoster(), shortDesc, String.join(", ", genreNames)
                });
            }
        }
    }


    private void updateSelectedMovie() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phim để sửa.");
            return;
        }

        try {
            int id = Integer.parseInt(model.getValueAt(row, 0).toString());

            Map<String, Object> updates = new java.util.HashMap<>();
            updates.put("title", txtTitle.getText());
            updates.put("country", txtCountry.getText());
            updates.put("release_year", Integer.parseInt(txtYear.getText()));
            updates.put("duration", Integer.parseInt(txtDuration.getText()));
            updates.put("director", txtDirector.getText());
            updates.put("rating", Double.parseDouble(txtRating.getText()));
            updates.put("vote_count", Integer.parseInt(txtVotes.getText()));
            updates.put("description", txtDescription.getText());

            String posterFile = txtPoster.getText().trim();
            if (!posterFile.isEmpty()) {
                updates.put("poster", posterFile);
            }

            String selectedGenre = cmbGenres.getSelectedItem().toString();
            Integer genreId = genreNameToId.get(selectedGenre);
            int[] genreIds = genreId != null ? new int[]{genreId} : new int[]{};

            boolean success = MovieDao.update(id, updates, genreIds);



            if (success) {
                JOptionPane.showMessageDialog(this, "Cập nhật phim thành công!");
                loadMovies(); // reload bảng
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    private void clearForm() {
        txtTitle.setText("");
        txtCountry.setText("");
        txtYear.setText("");
        txtDuration.setText("");
        txtDirector.setText("");
        txtRating.setText("");
        txtVotes.setText("");
        txtDescription.setText("");
        table.clearSelection();
        txtPoster.setText("");

    }

    private void deleteSelectedMovie() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phim để xoá.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xoá phim này?", "Xác nhận xoá",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(model.getValueAt(row, 0).toString());
                boolean success = MovieDao.delete(id);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Xoá phim thành công!");
                    loadMovies();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Xoá thất bại.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xoá: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }


    private void openAddMovieDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm phim", true);
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JTextField tfTitle = new JTextField(), tfCountry = new JTextField(), tfYear = new JTextField(),
                tfDuration = new JTextField(), tfDirector = new JTextField(),
                tfRating = new JTextField(), tfVote = new JTextField(), tfPoster = new JTextField();
        JTextArea tfDesc = new JTextArea(3, 20);
        tfDesc.setLineWrap(true); tfDesc.setWrapStyleWord(true);

        JComboBox<String> cmbAddGenre = new JComboBox<>();
        Map<String, Integer> genreNameToIdLocal = new HashMap<>();
        for (MovieGenre g : MovieGenreDao.queryList()) {
            cmbAddGenre.addItem(g.getGenreName());
            genreNameToIdLocal.put(g.getGenreName(), g.getId());
        }

        panel.add(new JLabel("Tên phim:")); panel.add(tfTitle);
        panel.add(new JLabel("Quốc gia:")); panel.add(tfCountry);
        panel.add(new JLabel("Năm phát hành:")); panel.add(tfYear);
        panel.add(new JLabel("Thời lượng:")); panel.add(tfDuration);
        panel.add(new JLabel("Đạo diễn:")); panel.add(tfDirector);
        panel.add(new JLabel("Rating:")); panel.add(tfRating);
        panel.add(new JLabel("Vote count:")); panel.add(tfVote);
        panel.add(new JLabel("Tên file poster:")); panel.add(tfPoster); // ví dụ: batman.jpg
        panel.add(new JLabel("Mô tả:")); panel.add(new JScrollPane(tfDesc));
        panel.add(new JLabel("Thể loại:"));
        panel.add(cmbAddGenre);

        JButton btnSave = new JButton("Lưu");

        btnSave.addActionListener(e -> {
            try {
                Movie m = new Movie();
                m.setTitle(tfTitle.getText());
                m.setCountry(tfCountry.getText());
                m.setReleaseYear(Integer.parseInt(tfYear.getText()));
                m.setDuration(Integer.parseInt(tfDuration.getText()));
                m.setDirector(tfDirector.getText());
                m.setRating(Double.parseDouble(tfRating.getText()));
                m.setVoteCount(Integer.parseInt(tfVote.getText()));
                m.setDescription(tfDesc.getText());

                String fileName = tfPoster.getText().trim();
                m.setPoster(fileName.isEmpty() ? null : POSTER_FOLDER_PATH + fileName);

                String selectedGenre = cmbAddGenre.getSelectedItem().toString();
                Integer genreId = genreNameToIdLocal.get(selectedGenre);
                int[] genres = genreId != null ? new int[]{genreId} : new int[]{};
                MovieDao.MovieWithGenres created = MovieDao.create(m, genres);
                if (created != null) {
                    JOptionPane.showMessageDialog(dialog, "Thêm thành công!");
                    dialog.dispose();
                    loadMovies();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Thêm thất bại!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Lỗi: " + ex.getMessage());
            }
        });

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.add(btnSave);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(footer, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
