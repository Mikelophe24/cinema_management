package View.Admin.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;
import java.time.LocalDateTime;
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

import Context.AppContext;
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
	private static final long serialVersionUID = 2536346615809271517L;
	private JTextField genreTextField;
	private JComboBox<String> genreComboBox;
	private Map<String, Integer> genreMap = new HashMap<>();
	private Map<Integer, Integer> movieIdMap = new HashMap<>();

	public MoviesPanel() {
		super("Quản lý phim", "🎬");
	}

	@Override
	protected void createComponents() {
		genreMap = new HashMap<>();
		String[] fieldSearchLabels = { "Tên phim" };
		String[] comboGenreLabels = { "Thể loại" };
		List<MovieGenre> movieGenres = MovieGenreDao.queryList();
		List<String> movieGenreName = new ArrayList<>();
		for (MovieGenre genre : movieGenres) {
			movieGenreName.add(genre.getName());
		}
		String[] genreArray = movieGenreName.toArray(new String[0]);
		String[][] comboGenreOptions = { genreArray };
		searchPanel = new SearchPanel(fieldSearchLabels, comboGenreLabels, comboGenreOptions);

		String[] columns = { "STT", "Tên phim", "Quốc gia", "Đạo diễn", "Thể loại", "Thời lượng", "Phát hành",
				"Mô tả" };
		table = createStyledTable(columns);

		String[] formFieldLabels = { "Tên phim", "Quốc gia", "Đạo diễn", "Poster", "Thời lượng", "Phát hành", "Mô tả" };
		String[] formFieldTypes = { "text", "text", "text", "file", "text", "text", "text" };

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
					movie.getDuration() + " phút", movie.getReleaseYear(), movie.getDescription() };
			tableModel.addRow(row);
//			movieIdMap.put(no, movie.getId());
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
//			formPanel.setTextFieldValue(3, table.getValueAt(selectedRow, 3).toString());
			formPanel.setTextFieldValue(4, table.getValueAt(selectedRow, 5).toString());
			formPanel.setTextFieldValue(5, table.getValueAt(selectedRow, 6).toString());
			formPanel.setTextFieldValue(6, table.getValueAt(selectedRow, 7).toString());

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
		data.put("name", formPanel.getTextFieldValue(0));
		data.put("country", formPanel.getTextFieldValue(1));
		data.put("director", formPanel.getTextFieldValue(2));
		data.put("poster", formPanel.getTextFieldValue(3));
		data.put("duration", formPanel.getTextFieldValue(4));
		data.put("releaseYear", formPanel.getTextFieldValue(5));
		data.put("description", formPanel.getTextFieldValue(6));
		data.put("genre", genreTextField.getText());
		return data;
	}

	private int[] generateGenreIds(Map<String, String> data) {
		if (genreMap == null || data == null || data.size() == 0) {
			return new int[] {};
		}

		String[] genreNames = data.get("genre").split(",\\s*");
		List<Integer> genreIds = new ArrayList<>();
		for (String name : genreNames) {
			if (genreMap.containsKey(name)) {
				genreIds.add(genreMap.get(name));
			}
		}
		int[] genreIdArray = genreIds.stream().mapToInt(Integer::intValue).toArray();
		return genreIdArray;
	}

	private void loadMap() {
		List<MovieWithGenres> movies = MovieDao.queryList();
		List<MovieGenre> movieGenres = MovieGenreDao.queryList();
		List<String> movieGenreName = new ArrayList<>();
		for (MovieGenre genre : movieGenres) {
			movieGenreName.add(genre.getName());
			genreMap.put(genre.getName(), genre.getId());
		}

		int no = 0;
		for (MovieWithGenres item : movies) {
			Movie movie = item.getMovie();
			movieIdMap.put(no, movie.getId());
			no++;
		}
	}

	@Override
	protected void handleEdit(int selectedRow) {
		loadMap();
		if (genreMap == null) {
			return;
		}
		Map<String, String> data = getFormData();
		int[] genreIdArray = generateGenreIds(data);
		if (genreIdArray.length == 0 || genreIdArray == null) {
			return;
		}

		Map<String, Object> updatedFields = new HashMap<>();
		updatedFields.put("title", data.get("name"));
		updatedFields.put("country", data.get("country"));
		updatedFields.put("director", data.get("director"));
		String durationStr = data.get("duration");
		int duration = 0;
		String numberPart = durationStr.split(" ")[0];
		duration = Integer.parseInt(numberPart);
		updatedFields.put("duration", duration);
		updatedFields.put("release_year", Integer.parseInt(data.get("releaseYear")));
		updatedFields.put("description", data.get("description"));

		boolean resUpdated = MovieDao.update(movieIdMap.get(selectedRow), updatedFields, genreIdArray);
		if (resUpdated) {
			tableModel.setValueAt(data.get("name"), selectedRow, 1);
			tableModel.setValueAt(data.get("country"), selectedRow, 2);
			tableModel.setValueAt(data.get("director"), selectedRow, 3);
			tableModel.setValueAt(data.get("genre"), selectedRow, 4);
			tableModel.setValueAt(data.get("duration") + " phút", selectedRow, 5);
			tableModel.setValueAt(data.get("releaseYear"), selectedRow, 6);
			tableModel.setValueAt(data.get("description"), selectedRow, 7);
		}
	}

	@Override
	protected void handleAdd() {
		loadMap();
		Map<String, String> data = getFormData();
		if (genreMap == null || data == null || data.size() == 0) {
			return;
		}

		String[] genreNames = data.get("genre").split(",\\s*");
		List<Integer> genreIds = new ArrayList<>();
		for (String name : genreNames) {
			if (genreMap.containsKey(name)) {
				genreIds.add(genreMap.get(name));
			}
		}
		int[] genreIdArray = genreIds.stream().mapToInt(Integer::intValue).toArray();
		if (genreIdArray.length == 0) {
			return;
		}

		String fullPosterPath = data.get("poster");
		String posterName = new File(fullPosterPath).getName();

		try {
			File source = new File(fullPosterPath);

//			String rootPath = System.getProperty("user.dir");

			File destDir = new File(AppContext.posterPath);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}

			File dest = new File(destDir, posterName);
			java.nio.file.Files.copy(source.toPath(), dest.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}

		Movie movie = new Movie(0, data.get("name"), data.get("country"), Integer.parseInt(data.get("releaseYear")),
				Integer.parseInt(data.get("duration")), data.get("director"), data.get("description"), posterName, 0.0,
				0, LocalDateTime.now(), LocalDateTime.now());

		MovieWithGenres resCreated = MovieDao.create(movie, genreIdArray);
		if (resCreated != null) {
			System.out.println(resCreated);
			Object[] row = { resCreated.getMovie().getId(), data.get("name"), data.get("country"), data.get("director"),
					data.get("genre"), data.get("duration") + " phút", data.get("releaseYear"),
					data.get("description") };
			tableModel.addRow(row);
			clearForm();
		}
	}

	@Override
	protected void handleSearch() {
		JTextField[] searchFields = searchPanel.getSearchFields();
		JComboBox<String>[] searchCombos = searchPanel.getSearchCombos();

		String name = searchFields[0].getText().trim();
		String genre = searchCombos[0].getSelectedItem().toString();

		if (genre.equalsIgnoreCase("Tất cả")) {
			genre = "";
		}

		System.out.println("Search name: " + name);
		System.out.println("Search genre: " + genre);

		List<MovieWithGenres> searchRes = MovieDao.search(name, genre);
		System.out.println("Search result: " + searchRes.size());

		tableModel.setRowCount(0);
		movieIdMap.clear();

		int no = 1;
		for (MovieWithGenres item : searchRes) {
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
					movie.getDuration() + " phút", movie.getReleaseYear(), movie.getDescription() };
			tableModel.addRow(row);
			movieIdMap.put(no - 1, movie.getId());
			no++;
		}
	}

	@Override
	protected void handleDelete(int selectedRow) {
		if (selectedRow < 0)
			return;
		loadMap();
		Integer movieId = movieIdMap.get(selectedRow);
		System.out.println(movieId);
		if (movieId == null) {
			System.out.println("Movie ID not found for row: " + selectedRow);
			return;
		}

		boolean resDeleted = MovieDao.delete(movieId);
		if (resDeleted) {
			System.out.println("Xoá thành công ID: " + movieId);

			tableModel.removeRow(selectedRow);

			movieIdMap.remove(selectedRow);

			Map<Integer, Integer> newMap = new HashMap<>();
			for (int i = 0; i < tableModel.getRowCount(); i++) {
				Integer shiftedId = movieIdMap.get(i >= selectedRow ? i + 1 : i);
				if (shiftedId != null) {
					newMap.put(i, shiftedId);
				}
			}
			movieIdMap = newMap;

		} else {
			System.out.println("Xoá thất bại");
		}
	}

}
