// Phim đang chiếu 

package View.MovieSection;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NowShowingView extends JPanel {
    public NowShowingView() {
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.gridy = 0;

        // Thông tin phim
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String todayStr = today.format(formatter);
        MovieInfo[] movies = new MovieInfo[] {
            new MovieInfo(
                "https://files.betacorp.vn/media%2fimages%2f2025%2f07%2f01%2f400wx633h%2D6%2D102751%2D010725%2D80.jpg",
                "Đàn Cá Gỗ",
                "Tình cảm, Tâm lý",
                "30 phút",
                todayStr
            ),
            new MovieInfo(
                "https://files.betacorp.vn/media%2fimages%2f2025%2f06%2f30%2fjwr%2Dposter%2Dsj%2Dmuta%2Dlab%2Dartwork%2D4x5%2D140052%2D300625%2D38.jpg",
                "Đợi Gì, Mơ Đi!",
                "Tâm lý, Hài hước",
                "116 phút",
                todayStr
            ),
            new MovieInfo(
                "https://files.betacorp.vn/media%2fimages%2f2025%2f05%2f30%2futlan%2Dteaser%2Dv2%2D1080x1350%2D101042%2D300525%2D81.jpg",
                "Siêu Sao Nguyên Thủy",
                "Âm Nhạc",
                "135 phút",
                todayStr
            ),
            new MovieInfo(
                "https://files.betacorp.vn/media%2fimages%2f2025%2f07%2f08%2f400x633%2D154305%2D080725%2D69.png",
                "Wolfoo Và Cuộc Đua Tam Giới",
                "Phiêu lưu, Hài hước",
                "100 phút",
                todayStr
            ),
            new MovieInfo(
                "https://files.betacorp.vn/media%2fimages%2f2025%2f06%2f02%2fposter%2Dsocial%2D4x5%2D142347%2D020625%2D65.png",
                "Con Nít Quỷ",
                "Phiêu lưu, Hài hước",
                "100 phút",
                todayStr
            ),
            new MovieInfo(
                "https://files.betacorp.vn/media%2fimages%2f2025%2f06%2f23%2f400wx633h%2D101957%2D230625%2D34.jpg",
                "Mùa Hè Kinh Hãi",
                "Phiêu lưu, Hài hước",
                "100 phút",
                todayStr
            ),
            new MovieInfo(
                "https://files.betacorp.vn/media%2fimages%2f2025%2f06%2f24%2fdmbv%2D112617%2D240625%2D88.jpg",
                "Phim Xì Trum",
                "Phiêu lưu, Hài hước",
                "100 phút",
                todayStr
            ),
            new MovieInfo(
                "https://files.betacorp.vn/media%2fimages%2f2025%2f03%2f12%2fcopy%2Dof%2D250220%2Ddr25%2Dmain%2Db1%2Dlocalized%2Dembbed%2D164332%2D120325%2D55.jpg",
                "Thám Tử Lừng Danh Conan: Dự án Tận Thế",
                "Phiêu lưu, Hài hước",
                "100 phút",
                todayStr
            )
        };

        // Panel chứa các hàng phim
        JPanel listPanel = new JPanel();
        listPanel.setBackground(Color.WHITE);
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        int filmsPerRow = 4;
        for (int i = 0; i < movies.length; i += filmsPerRow) {
            JPanel rowPanel = new JPanel();
            rowPanel.setBackground(Color.WHITE);
            rowPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));
            rowPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
            for (int j = i; j < i + filmsPerRow && j < movies.length; j++) {
                rowPanel.add(createMovieCard(movies[j]));
            }
            listPanel.add(rowPanel);
        }
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(listPanel, gbc);
    }
    

    private JPanel createMovieCard(MovieInfo movie) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(240, 440));
        card.setBackground(Color.WHITE);
        card.setLayout(null);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
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

    private void showBookingModal(MovieInfo movie) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Đặt Vé - " + movie.name, true);
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(null); // căn giữa màn hình
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Map tỉnh -> danh sách rạp
        java.util.Map<String, String[]> rapMap = new java.util.HashMap<>();
        rapMap.put("Hà Nội", new String[]{"Beta Thanh Xuân", "Beta Mỹ Đình", "Beta Đan Phượng"});
        rapMap.put("TP. Hồ Chí Minh", new String[]{"Beta Quang Trung", "Beta Hồ Tràm"});
        rapMap.put("Đồng Nai", new String[]{"Beta Biên Hòa", "Beta Long Khánh"});
        rapMap.put("Khánh Hòa", new String[]{"Beta Nha Trang"});
        rapMap.put("Thái Nguyên", new String[]{"Beta Thái Nguyên"});

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
            for (String rap : rapMap.getOrDefault(selectedProvince, new String[]{})) {
                cinemaCombo.addItem(rap);
            }
        }

        // Sự kiện khi chọn tỉnh → cập nhật danh sách rạp
        provinceCombo.addActionListener(e -> {
            String province = (String) provinceCombo.getSelectedItem();
            cinemaCombo.removeAllItems();
            for (String rap : rapMap.getOrDefault(province, new String[]{})) {
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
                // Truyền ngày khởi chiếu vào ShowtimeDialogs
                new View.ModalViewNowShowing.ShowtimeDialogs((Frame) SwingUtilities.getWindowAncestor(this), movie.name, cinema, movie.date).setVisible(true);
            }
        });

        dialog.setVisible(true);
    }
     private static class MovieInfo {
        String imageUrl, name, genre, duration, date;
        MovieInfo(String imageUrl, String name, String genre, String duration, String date) {
            this.imageUrl = imageUrl;
            this.name = name;
            this.genre = genre;
            this.duration = duration;
            this.date = date;
        }
    }
} 