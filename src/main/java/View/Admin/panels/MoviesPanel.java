package View.Admin.panels;

import View.Admin.common.*;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MoviesPanel extends BasePanel {
    // Thêm biến cho custom genre
    private JTextField genreTextField;
    private JComboBox<String> genreComboBox;

    public MoviesPanel() {
        super("Quản lý phim", "🎬");
    }
    
    @Override
    protected void createComponents() {
        // Tạo search panel
        String[] fieldLabels = {"Tên phim", "Nghệ sĩ"};
        String[] comboLabels = {"Thể loại"};
        String[][] comboOptions = {{"Tất cả", "Hành động", "Tình cảm", "Kinh dị", "Hài hước", "Viễn tưởng", "Hoạt hình", "Phim ngắn"}};
        
        searchPanel = new SearchPanel(fieldLabels, comboLabels, comboOptions);
        
        // Tạo table
        String[] columns = {"ID", "Tên phim", "Nghệ sĩ", "Thể loại", "Thời lượng", "Đánh giá", "Trạng thái"};
        table = createStyledTable(columns);
        
        // Tạo form panel (không có combo cho thể loại)
        String[] formFieldLabels = {"Tên phim", "Nghệ sĩ", "Thời lượng", "Đánh giá"};
        formPanel = new FormPanel("Thông tin phim", formFieldLabels, new String[]{}, new String[][]{});

        // Thêm custom cho thể loại
        JPanel genrePanel = new JPanel(new BorderLayout(5, 0));
        genreTextField = new JTextField();
        genreTextField.setEditable(false);
        genrePanel.add(genreTextField, BorderLayout.CENTER);

        String[] genres = {"Hành động", "Tình cảm", "Kinh dị", "Hài hước", "Viễn tưởng", "Hoạt hình", "Phim ngắn"};
        genreComboBox = new JComboBox<>(genres);
        genrePanel.add(genreComboBox, BorderLayout.EAST);

        // Custom renderer để highlight các mục đã chọn
        genreComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                String current = genreTextField.getText();
                java.util.List<String> selectedGenres = java.util.Arrays.asList(current.split(",\\s*"));
                if (index != -1 && selectedGenres.contains(value)) {
                    c.setBackground(new Color(135, 206, 250)); // Màu xanh nhạt
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                } else if (isSelected) {
                    c.setBackground(list.getSelectionBackground());
                } else {
                    c.setBackground(list.getBackground());
                    c.setFont(c.getFont().deriveFont(Font.PLAIN));
                }
                return c;
            }
        });

        // Sự kiện chọn thể loại: toggle chọn/bỏ chọn
        genreComboBox.addActionListener(e -> {
            String selected = (String) genreComboBox.getSelectedItem();
            String current = genreTextField.getText();
            if (selected != null && !selected.isEmpty()) {
                String[] arr = current.isEmpty() ? new String[0] : current.split(",\\s*");
                java.util.List<String> genresList = new java.util.ArrayList<>(java.util.Arrays.asList(arr));
                if (genresList.contains(selected)) {
                    genresList.remove(selected);
                } else {
                    genresList.add(selected);
                }
                genreTextField.setText(String.join(", ", genresList));
                genreComboBox.repaint();
            }
        });

        // Thêm genrePanel vào formPanel (dưới các trường khác)
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.gridy = 4;
        gbcLabel.anchor = GridBagConstraints.WEST;
        gbcLabel.insets = new Insets(8, 10, 8, 10);
        formPanel.add(new JLabel("Thể loại:"), gbcLabel);

        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.gridx = 1;
        gbcField.gridy = 4;
        gbcField.fill = GridBagConstraints.HORIZONTAL;
        gbcField.weightx = 1;
        gbcField.insets = new Insets(8, 10, 8, 10);
        formPanel.add(genrePanel, gbcField);

        // Tạo button panel (không có button đặc biệt)
        buttonPanel = new ButtonPanel(new String[]{});
    }
    
    @Override
    protected void addSampleData() {
        // Thêm dữ liệu mẫu vào table
        Object[][] sampleData = {
            {1, "Avengers: Endgame", "Robert Downey Jr.", "Hành động, Viễn tưởng", "181 phút", "9.0/10", "Đang chiếu"},
            {2, "Titanic", "Leonardo DiCaprio", "Tình cảm", "194 phút", "8.9/10", "Đã kết thúc"},
            {3, "The Dark Knight", "Christian Bale", "Hành động, Kinh dị", "152 phút", "9.0/10", "Đã kết thúc"},
            {4, "Inception", "Leonardo DiCaprio", "Viễn tưởng, Hành động", "148 phút", "8.8/10", "Đã kết thúc"},
            {5, "Frozen", "Kristen Bell", "Hoạt hình", "102 phút", "7.4/10", "Đang chiếu"},
            {6, "The Shawshank Redemption", "Tim Robbins", "Tình cảm", "142 phút", "9.3/10", "Đã kết thúc"},
            {7, "Pulp Fiction", "John Travolta", "Hành động", "154 phút", "8.9/10", "Đã kết thúc"},
            {8, "The Matrix", "Keanu Reeves", "Viễn tưởng, Hành động", "136 phút", "8.7/10", "Đã kết thúc"},
            {9, "Forrest Gump", "Tom Hanks", "Tình cảm", "142 phút", "8.8/10", "Đã kết thúc"},
            {10, "The Lion King", "Matthew Broderick", "Hoạt hình", "88 phút", "8.5/10", "Đang chiếu"}
        };
        
        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
    }
    
    @Override
    protected void displaySelectedRowData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            formPanel.setTextFieldValue(0, table.getValueAt(selectedRow, 1).toString());
            formPanel.setTextFieldValue(1, table.getValueAt(selectedRow, 2).toString());
            formPanel.setTextFieldValue(2, table.getValueAt(selectedRow, 4).toString());
            formPanel.setTextFieldValue(3, table.getValueAt(selectedRow, 5).toString());
            // Hiển thị thể loại lên ô text
            genreTextField.setText(table.getValueAt(selectedRow, 3).toString());
        }
    }
    
    @Override
    protected void clearForm() {
        formPanel.clearForm();
        genreTextField.setText("");
    }
    
    @Override
    protected Map<String, String> getFormData() {
        Map<String, String> data = new HashMap<>();
        data.put("movieName", formPanel.getTextFieldValue(0));
        data.put("artist", formPanel.getTextFieldValue(1));
        data.put("duration", formPanel.getTextFieldValue(2));
        data.put("rating", formPanel.getTextFieldValue(3));
        data.put("genre", genreTextField.getText());
        return data;
    }
    
    @Override
    protected void handleEdit(int selectedRow) {
        // Cập nhật dữ liệu trong bảng
        Map<String, String> data = getFormData();
        tableModel.setValueAt(data.get("movieName"), selectedRow, 1);
        tableModel.setValueAt(data.get("artist"), selectedRow, 2);
        tableModel.setValueAt(data.get("genre"), selectedRow, 3);
        tableModel.setValueAt(data.get("duration"), selectedRow, 4);
        tableModel.setValueAt(data.get("rating"), selectedRow, 5);
        // Trạng thái giữ nguyên
    }
    
    @Override
    protected void handleAdd() {
        Map<String, String> data = getFormData();
        Object[] row = {
            tableModel.getRowCount() + 1,
            data.get("movieName"),
            data.get("artist"),
            data.get("genre"),
            data.get("duration"),
            data.get("rating"),
            "Đang chiếu"
        };
        tableModel.addRow(row);
        clearForm();
    }
    
    @Override
    protected void handleSearch() {
        JTextField[] searchFields = searchPanel.getSearchFields();
        JComboBox<String>[] searchCombos = searchPanel.getSearchCombos();
        
        String movieName = searchFields[0].getText().trim();
        String artist = searchFields[1].getText().trim();
        String genre = searchCombos[0].getSelectedItem().toString();
        
        System.out.println("Tìm kiếm: " + movieName + ", " + artist + ", " + genre);
    }
} 