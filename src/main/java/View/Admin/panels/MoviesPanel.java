package View.Admin.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Dao.MovieDao;
import Dao.MovieDao.MovieWithGenres;
import Dao.MovieGenreDao;
import Model.Movie;
import Model.MovieGenre;
import View.Admin.common.BasePanel;
import View.Admin.common.ButtonPanel;
import View.Admin.common.FormPanel;
import View.Admin.common.SearchPanel;

public class MoviesPanel extends BasePanel {
	private JTextField genreTextField;
	private JComboBox<String> genreComboBox;

	public MoviesPanel() {
		super("Quản lý phim", "🎬");
	}

	@Override
	protected void createComponents() {
		String[] fieldSearchLabels = { "Tên phim", "Nghệ sĩ" };
		String[] comboGenreLabels = { "Thể loại" };
		List<MovieGenre> movieGenres = MovieGenreDao.queryList();
		List<String> movieGenreName = new ArrayList<>();
		movieGenreName.add("Tất cả");
		for (MovieGenre genre : movieGenres) {
			movieGenreName.add(genre.getName());
		}
		String[] genreArray = movieGenreName.toArray(new String[0]);
		String[][] comboGenreOptions = { genreArray };
		searchPanel = new SearchPanel(fieldSearchLabels, comboGenreLabels, comboGenreOptions);

		String[] columns = { "STT", "Tên phim", "Quốc gia", "Đạo diễn", "Thể loại", "Thời lượng", "Phát hành" };
		table = createStyledTable(columns);

		String[] formFieldLabels = { "Tên phim", "Quốc gia", "Đạo diễn", "Poster" };
		String[] formFieldTypes = { "text", "text", "text", "file" };

		formPanel = new FormPanel("Thông tin phim", formFieldLabels, formFieldTypes, new String[] {},
				new String[][] {});

		JPanel genrePanel = new JPanel(new BorderLayout(5, 0));
		genreTextField = new JTextField();
		genreTextField.setEditable(false);
		genrePanel.add(genreTextField, BorderLayout.CENTER);

		genreComboBox = new JComboBox<>(genreArray);
		genrePanel.add(genreComboBox, BorderLayout.EAST);

		genreComboBox.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				String current = genreTextField.getText();
				java.util.List<String> selectedGenres = java.util.Arrays.asList(current.split(",\\s*"));
				if (index != -1 && selectedGenres.contains(value)) {
					c.setBackground(new Color(135, 206, 250));
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

		buttonPanel = new ButtonPanel(new String[] {});
	}

	@Override
	protected void addSampleData() {
		List<MovieWithGenres> movies = MovieDao.queryList();
		int no = 1;
		for (MovieWithGenres item : movies) {
			Movie movie = item.getMovie();
			MovieGenre[] genres = item.getGenres();
			StringBuilder genreNames = new StringBuilder();
			for (int i = 0; i < genres.length; i++) {
				genreNames.append(genres[i].getName());
				if (i != genres.length - 1) {
					genreNames.append(", ");
				}
			}
			Object[] row = { no, movie.getTitle(), movie.getCountry(), movie.getDirector(), genreNames.toString(),
					movie.getDuration() + " phút", movie.getReleaseYear() };
			tableModel.addRow(row);
			no++;
		}
	}

	@Override
	protected void displaySelectedRowData() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			formPanel.setTextFieldValue(0, table.getValueAt(selectedRow, 1).toString());
			formPanel.setTextFieldValue(1, table.getValueAt(selectedRow, 2).toString());
			formPanel.setTextFieldValue(2, table.getValueAt(selectedRow, 3).toString());
			genreTextField.setText(table.getValueAt(selectedRow, 4).toString());
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
		data.put("country", formPanel.getTextFieldValue(1));
		data.put("director", formPanel.getTextFieldValue(2));
		data.put("posterPath", formPanel.getTextFieldValue(3));
		data.put("genre", genreTextField.getText());
		return data;
	}

	@Override
	protected void handleEdit(int selectedRow) {
		Map<String, String> data = getFormData();
		tableModel.setValueAt(data.get("movieName"), selectedRow, 1);
		tableModel.setValueAt(data.get("country"), selectedRow, 2);
		tableModel.setValueAt(data.get("director"), selectedRow, 3);
		tableModel.setValueAt(data.get("genre"), selectedRow, 4);
	}

	@Override
	protected void handleAdd() {
		Map<String, String> data = getFormData();
		Object[] row = { tableModel.getRowCount() + 1, data.get("movieName"), data.get("country"), data.get("director"),
				data.get("genre"), "N phút", "Năm phát hành" };
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
