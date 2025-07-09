package View.MovieSection;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;

public class ComingSoonView extends JPanel {
    public ComingSoonView() {
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.gridy = 0;

        // Thông tin phim
        MovieInfo[] movies = new MovieInfo[] {
            new MovieInfo(
                "https://files.betacorp.vn/media/images/2025/06/18/beta-165152-180625-26.jpg",
                "Đàn Cá Gỗ",
                "Tình cảm, Tâm lý",
                "30 phút",
                "15/07/2025"
            ),
            new MovieInfo(
                "https://files.betacorp.vn/media/images/2025/06/25/400x633-1-144619-250625-74.jpg",
                "Đợi Gì, Mơ Đi!",
                "Tâm lý, Hài hước",
                "116 phút",
                "11/07/2025"
            ),
            new MovieInfo(
                "https://files.betacorp.vn/media/images/2025/06/19/better-man-113908-190625-73.jpg",
                "Siêu Sao Nguyên Thủy",
                "Âm Nhạc",
                "135 phút",
                "11/07/2025"
            ),
            new MovieInfo(
                "https://files.betacorp.vn/media%2fimages%2f2025%2f06%2f30%2f400%2Dx%2D633%2D151512%2D300625%2D21.png",
                "Wolfoo Và Cuộc Đua Tam Giới",
                "Phiêu lưu, Hài hước",
                "100 phút",
                "11/07/2025"
            ) , 
            new MovieInfo(
                "https://files.betacorp.vn/media%2fimages%2f2025%2f07%2f08%2f4%2Dpr%2Dteaser%2Dposter%2Dwaktu%2D2%2D152306%2D080725%2D61.jpg",
                "Con Nít Quỷ",
                "Phiêu lưu, Hài hước",
                "100 phút",
                "11/07/2025"
            ) , 
            new MovieInfo(
                "https://files.betacorp.vn/media%2fimages%2f2025%2f06%2f19%2f1080x1350%2D160336%2D190625%2D18.jpg",
                "Mùa Hè Kinh Hãi",
                "Phiêu lưu, Hài hước",
                "100 phút",
                "11/07/2025"
            ),
            new MovieInfo(
                "https://files.betacorp.vn/media%2fimages%2f2025%2f06%2f24%2f21532672%2D43ac%2D41a2%2Daa25%2De06e0cdabcc2%2D114903%2D240625%2D97.jpg",
                "Phim Xì Trum",
                "Phiêu lưu, Hài hước",
                "100 phút",
                "11/07/2025"
            ), 
            new MovieInfo(
                "https://files.betacorp.vn/media%2fimages%2f2025%2f06%2f07%2fscreenshot%2D2025%2D06%2D07%2D092332%2D092530%2D070625%2D65.png",
                "Thám Tử Lừng Danh Conan: Dự án Tận Thế",
                "Phiêu lưu, Hài hước",
                "100 phút",
                "11/07/2025"
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

        // Ngày khởi chiếu
        JLabel release = new JLabel("<html><b>Ngày khởi chiếu:</b> <span style='color:#2196F3;'>" + movie.releaseDate + "</span></html>");
        release.setFont(new Font("Arial", Font.PLAIN, 13));
        release.setBounds(15, 395, 210, 18);
        card.add(release);

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
        Map<String, String[]> rapMap = new HashMap<>();
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
            // Hiển thị giao diện suất chiếu, truyền thêm ngày chiếu
            new View.ModalView.ShowtimeDialog((Frame) SwingUtilities.getWindowAncestor(this), movie.name, cinema, movie.releaseDate).setVisible(true);
        }
    });

    dialog.setVisible(true);
}


    private static class MovieInfo {
        String imageUrl, name, genre, duration, releaseDate;
        MovieInfo(String imageUrl, String name, String genre, String duration, String releaseDate) {
            this.imageUrl = imageUrl;
            this.name = name;
            this.genre = genre;
            this.duration = duration;
            this.releaseDate = releaseDate;
        }
    }
} 