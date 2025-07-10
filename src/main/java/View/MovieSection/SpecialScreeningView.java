// Suất chiếu đặc biệt 


package View.MovieSection;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpecialScreeningView extends JPanel {
    public SpecialScreeningView() {
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.gridy = 0;

        // Thông tin phim
        MovieInfo[] movies = new MovieInfo[] {
            new MovieInfo(
                "https://files.betacorp.vn/media%2fimages%2f2025%2f07%2f01%2f400wx633h%2D6%2D102751%2D010725%2D80.jpg",
                "Đàn Cá Gỗ",
                "Tình cảm, Tâm lý",
                "30 phút",
                "11/07/2025"
            ),
            new MovieInfo(
                "https://files.betacorp.vn/media%2fimages%2f2025%2f06%2f30%2fjwr%2Dposter%2Dsj%2Dmuta%2Dlab%2Dartwork%2D4x5%2D140052%2D300625%2D38.jpg",
                "Đợi Gì, Mơ Đi!",
                "Tâm lý, Hài hước",
                "116 phút",
                "11/07/2025"
            ),
            new MovieInfo(
                "https://files.betacorp.vn/media%2fimages%2f2025%2f05%2f30%2futlan%2Dteaser%2Dv2%2D1080x1350%2D101042%2D300525%2D81.jpg",
                "Siêu Sao Nguyên Thủy",
                "Âm Nhạc",
                "135 phút",
                "11/07/2025"
            ),
            new MovieInfo(
                "https://files.betacorp.vn/media%2fimages%2f2025%2f07%2f08%2f400x633%2D154305%2D080725%2D69.png",
                "Wolfoo Và Cuộc Đua Tam Giới",
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
        card.add(buyBtn);

        return card;
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