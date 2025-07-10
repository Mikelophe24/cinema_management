package View.Admin.panels;

import View.Admin.common.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import Dao.MovieDao;
import Model.Movie;
import Dao.TheaterDao;
import Model.Theater;


public class SchedulePanel extends BasePanel {
    public SchedulePanel() {
        super("Quản lý lịch chiếu phim", "🗓️");
    }

    @Override
    protected void createComponents() {
        // SearchPanel: có combobox cho phim và phòng
        String[] searchFieldLabels = {};
        String[] searchComboLabels = {"Phim", "Phòng"};
        
        // Lấy danh sách phim và phòng từ API
        List<String> movieTitles = getMovieTitles();
        List<String> theaterNames = getTheaterNames();
        
        String[][] searchComboOptions = {
            movieTitles.toArray(new String[0]),  // Options cho combobox Phim
            theaterNames.toArray(new String[0])  // Options cho combobox Phòng
        };
        searchPanel = new SearchPanel(searchFieldLabels, searchComboLabels, searchComboOptions);

        // Table
        String[] columns = {"Ngày", "Giờ", "Phim", "Phòng", "Giá", "ID"};
        table = createStyledTable(columns);

        // FormPanel: có combobox cho ngày, giờ, phim, phòng và text field cho giá
        String[] formFieldLabels = {"Giá"};
        String[] formComboLabels = {"Ngày", "Giờ", "Phim", "Phòng"};
        
        // Tạo danh sách ngày (7 ngày tới)
        List<String> dates = new java.util.ArrayList<>();
        java.time.LocalDate today = java.time.LocalDate.now();
        for (int i = 0; i < 7; i++) {
            dates.add(today.plusDays(i).toString());
        }
        
        // Tạo danh sách giờ (từ 9:00 đến 23:00)
        List<String> times = new java.util.ArrayList<>();
        for (int hour = 9; hour <= 23; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                times.add(String.format("%02d:%02d:00", hour, minute));
            }
        }
        
        // Lấy danh sách phim và phòng từ API cho form
        List<String> formMovieTitles = getMovieTitles();
        List<String> formTheaterNames = getTheaterNames();
        
        String[][] formComboOptions = {
            dates.toArray(new String[0]),        // Options cho combobox Ngày
            times.toArray(new String[0]),        // Options cho combobox Giờ
            formMovieTitles.toArray(new String[0]),  // Options cho combobox Phim
            formTheaterNames.toArray(new String[0])  // Options cho combobox Phòng
        };
        
        formPanel = new FormPanel("Thông tin lịch chiếu", formFieldLabels, formComboLabels, formComboOptions);

        // ButtonPanel: không có button đặc biệt
        buttonPanel = new ButtonPanel(new String[]{});
        

    }
    
    private List<String> getMovieTitles() {
        try {
            List<MovieDao.MovieWithGenres> moviesWithGenres = MovieDao.queryList();
            List<String> titles = new java.util.ArrayList<>();
            if (moviesWithGenres != null) {
                for (MovieDao.MovieWithGenres movieWithGenres : moviesWithGenres) {
                    titles.add(movieWithGenres.getMovie().getTitle());
                }
            }
            return titles;
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách phim: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
    
    private List<String> getTheaterNames() {
        try {
            List<TheaterDao.TheaterWithSeats> theatersWithSeats = TheaterDao.queryList();
            List<String> names = new java.util.ArrayList<>();
            if (theatersWithSeats != null) {
                for (TheaterDao.TheaterWithSeats theaterWithSeats : theatersWithSeats) {
                    names.add(theaterWithSeats.getTheater().getName());
                }
            }
            return names;
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách phòng: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }
    


    @Override
    protected void addSampleData() {
        try {
            // Lấy danh sách lịch chiếu từ API
            List<Model.MovieSchedule> schedules = Dao.MovieScheduleDao.queryList();
            List<MovieDao.MovieWithGenres> moviesWithGenres = MovieDao.queryList();
            List<TheaterDao.TheaterWithSeats> theatersWithSeats = TheaterDao.queryList();

            if (schedules != null && !schedules.isEmpty() && moviesWithGenres != null && theatersWithSeats != null) {
                Object[][] sampleData = new Object[schedules.size()][6];
                for (int i = 0; i < schedules.size(); i++) {
                    Model.MovieSchedule schedule = schedules.get(i);
                    // Lấy ngày và giờ từ API
                    String showDate = schedule.getShowDate().toString();
                    String startTime = schedule.getStartTime().toString();
                    // Lấy tên phim từ movie_id
                    String movieTitle = "";
                    for (MovieDao.MovieWithGenres m : moviesWithGenres) {
                        if (m.getMovie().getId() == schedule.getMovieId()) {
                            movieTitle = m.getMovie().getTitle();
                            break;
                        }
                    }
                    // Lấy tên phòng từ theater_id
                    String theaterName = "";
                    for (TheaterDao.TheaterWithSeats t : theatersWithSeats) {
                        if (t.getTheater().getId() == schedule.getTheaterId()) {
                            theaterName = t.getTheater().getName();
                            break;
                        }
                    }
                    sampleData[i][0] = showDate;
                    sampleData[i][1] = startTime;
                    sampleData[i][2] = movieTitle;
                    sampleData[i][3] = theaterName;
                    sampleData[i][4] = String.format("%.0f VNĐ", schedule.getPrice()); // Thêm giá
                    sampleData[i][5] = schedule.getId(); // Thêm ID
                }
                for (Object[] row : sampleData) {
                    tableModel.addRow(row);
                }
                System.out.println("Đã tải " + schedules.size() + " lịch chiếu từ database");
            } else {
                System.out.println("Không có lịch chiếu, phim hoặc phòng nào trong database");
                addFallbackData();
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tải danh sách lịch chiếu: " + e.getMessage());
            e.printStackTrace();
            addFallbackData();
        }
    }
    
    private void addFallbackData() {
        // Không thêm dữ liệu cứng nào nữa, chỉ để bảng rỗng
    }

    @Override
    protected void displaySelectedRowData() {
        int row = table.getSelectedRow();
        if (row != -1) {
            // Set combobox values (Ngày, Giờ, Phim, Phòng)
            formPanel.setComboBoxValue(0, tableModel.getValueAt(row, 0).toString());
            formPanel.setComboBoxValue(1, tableModel.getValueAt(row, 1).toString());
            formPanel.setComboBoxValue(2, tableModel.getValueAt(row, 2).toString());
            formPanel.setComboBoxValue(3, tableModel.getValueAt(row, 3).toString());
            
            // Set text field giá
            String priceText = tableModel.getValueAt(row, 4).toString();
            // Loại bỏ " VNĐ" để lấy số
            String priceNumber = priceText.replace(" VNĐ", "");
            formPanel.setTextFieldValue(0, priceNumber);
            
            // ID được lưu ở cột 5 nhưng không hiển thị trong form
        }
    }

    @Override
    protected void clearForm() {
        formPanel.clearForm();
    }

    @Override
    protected Map<String, String> getFormData() {
        Map<String, String> data = new HashMap<>();
        data.put("Ngày", formPanel.getComboBoxValue(0));
        data.put("Giờ", formPanel.getComboBoxValue(1));
        data.put("Phim", formPanel.getComboBoxValue(2));
        data.put("Phòng", formPanel.getComboBoxValue(3));
        data.put("Giá", formPanel.getTextFieldValue(0));
        return data;
    }

    @Override
    protected void handleEdit(int selectedRow) {
        try {
            Map<String, String> data = getFormData();
            
            // Lấy ID của schedule từ bảng (cần thêm cột ID vào bảng)
            Object scheduleIdObj = tableModel.getValueAt(selectedRow, 5); // Cột ID ở vị trí 5
            if (scheduleIdObj == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy ID của lịch chiếu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int scheduleId = Integer.parseInt(scheduleIdObj.toString());
            
            // Lấy danh sách phim và phòng
            List<MovieDao.MovieWithGenres> moviesWithGenres = MovieDao.queryList();
            List<TheaterDao.TheaterWithSeats> theatersWithSeats = TheaterDao.queryList();

            // Tìm movie_id và theater_id từ tên
            int movieId = -1;
            for (MovieDao.MovieWithGenres m : moviesWithGenres) {
                if (m.getMovie().getTitle().equals(data.get("Phim"))) {
                    movieId = m.getMovie().getId();
                    break;
                }
            }
            int theaterId = -1;
            for (TheaterDao.TheaterWithSeats t : theatersWithSeats) {
                if (t.getTheater().getName().equals(data.get("Phòng"))) {
                    theaterId = t.getTheater().getId();
                    break;
                }
            }
            if (movieId == -1 || theaterId == -1) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy phim hoặc phòng tương ứng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Parse ngày và giờ
            java.time.LocalDate showDate = java.time.LocalDate.parse(data.get("Ngày"));
            java.time.LocalTime startTime = java.time.LocalTime.parse(data.get("Giờ"));
            
            // Parse giá từ form
            double price = 0.0;
            try {
                price = Double.parseDouble(data.get("Giá"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Giá không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Lấy duration mặc định từ phim (nếu có)
            int duration = 120;
            for (MovieDao.MovieWithGenres m : moviesWithGenres) {
                if (m.getMovie().getId() == movieId) {
                    duration = m.getMovie().getDuration();
                    break;
                }
            }
            
            // Tạo Map để update
            Map<String, Object> updateFields = new HashMap<>();
            
            // Chỉ update các field thay đổi
            // Lấy schedule hiện tại để so sánh
            Model.MovieSchedule currentSchedule = Dao.MovieScheduleDao.queryOne(scheduleId);
            if (currentSchedule != null) {
                if (currentSchedule.getTheaterId() != theaterId) {
                    updateFields.put("theater_id", theaterId);
                }
                if (currentSchedule.getMovieId() != movieId) {
                    updateFields.put("movie_id", movieId);
                }
                if (!currentSchedule.getShowDate().equals(showDate)) {
                    updateFields.put("show_date", showDate);
                }
                if (!currentSchedule.getStartTime().equals(startTime)) {
                    updateFields.put("start_time", startTime);
                }
                if (currentSchedule.getPrice() != price) {
                    updateFields.put("price", price);
                }
            }
            
            // Nếu không có field nào thay đổi
            if (updateFields.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có thông tin nào thay đổi!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Gọi API update
            boolean updated = Dao.MovieScheduleDao.update(scheduleId, updateFields);
            
            if (updated) {
                // Reload lại bảng
                tableModel.setRowCount(0);
                addSampleData();
                clearForm();
                JOptionPane.showMessageDialog(this, "Cập nhật lịch chiếu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật lịch chiếu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật lịch chiếu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    protected void handleAdd() {
        Map<String, String> data = getFormData();
        try {
            // Lấy danh sách phim và phòng
            List<MovieDao.MovieWithGenres> moviesWithGenres = MovieDao.queryList();
            List<TheaterDao.TheaterWithSeats> theatersWithSeats = TheaterDao.queryList();

            // Tìm movie_id và theater_id từ tên
            int movieId = -1;
            for (MovieDao.MovieWithGenres m : moviesWithGenres) {
                if (m.getMovie().getTitle().equals(data.get("Phim"))) {
                    movieId = m.getMovie().getId();
                    break;
                }
            }
            int theaterId = -1;
            for (TheaterDao.TheaterWithSeats t : theatersWithSeats) {
                if (t.getTheater().getName().equals(data.get("Phòng"))) {
                    theaterId = t.getTheater().getId();
                    break;
                }
            }
            if (movieId == -1 || theaterId == -1) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy phim hoặc phòng tương ứng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Parse ngày và giờ
            java.time.LocalDate showDate = java.time.LocalDate.parse(data.get("Ngày"));
            java.time.LocalTime startTime = java.time.LocalTime.parse(data.get("Giờ"));
            // Lấy duration mặc định từ phim (nếu có)
            int duration = 120;
            for (MovieDao.MovieWithGenres m : moviesWithGenres) {
                if (m.getMovie().getId() == movieId) {
                    duration = m.getMovie().getDuration();
                    break;
                }
            }
            // Parse giá từ form
            double price = 0.0;
            try {
                price = Double.parseDouble(data.get("Giá"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Giá không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Tạo MovieSchedule và lưu vào DB
            Model.MovieSchedule schedule = new Model.MovieSchedule(0, theaterId, movieId, showDate, startTime, duration, price);
            Dao.MovieScheduleDao.create(schedule);
            // Reload lại bảng
            tableModel.setRowCount(0);
            addSampleData();
            clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm lịch chiếu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    protected void handleSearch() {
        try {
            // Lấy giá trị từ combobox search
            JComboBox<String>[] searchCombos = searchPanel.getSearchCombos();
            String selectedMovie = searchCombos[0].getSelectedItem() != null ? searchCombos[0].getSelectedItem().toString() : "";
            String selectedTheater = searchCombos[1].getSelectedItem() != null ? searchCombos[1].getSelectedItem().toString() : "";
            
            // Lấy danh sách phim và phòng để tìm ID
            List<MovieDao.MovieWithGenres> moviesWithGenres = MovieDao.queryList();
            List<TheaterDao.TheaterWithSeats> theatersWithSeats = TheaterDao.queryList();
            
            List<Model.MovieSchedule> searchResults = new java.util.ArrayList<>();
            
            // Tìm kiếm theo điều kiện
            if (!selectedMovie.isEmpty() && !selectedTheater.isEmpty()) {
                // Tìm kiếm theo cả phim và phòng
                int movieId = -1;
                int theaterId = -1;
                
                for (MovieDao.MovieWithGenres m : moviesWithGenres) {
                    if (m.getMovie().getTitle().equals(selectedMovie)) {
                        movieId = m.getMovie().getId();
                        break;
                    }
                }
                
                for (TheaterDao.TheaterWithSeats t : theatersWithSeats) {
                    if (t.getTheater().getName().equals(selectedTheater)) {
                        theaterId = t.getTheater().getId();
                        break;
                    }
                }
                
                if (movieId != -1 && theaterId != -1) {
                    searchResults = Dao.MovieScheduleDao.queryListByMovieAndTheater(movieId, theaterId);
                }
            } else if (!selectedMovie.isEmpty()) {
                // Tìm kiếm theo phim
                int movieId = -1;
                for (MovieDao.MovieWithGenres m : moviesWithGenres) {
                    if (m.getMovie().getTitle().equals(selectedMovie)) {
                        movieId = m.getMovie().getId();
                        break;
                    }
                }
                
                if (movieId != -1) {
                    searchResults = Dao.MovieScheduleDao.queryListByMovieId(movieId);
                }
            } else if (!selectedTheater.isEmpty()) {
                // Tìm kiếm theo phòng
                int theaterId = -1;
                for (TheaterDao.TheaterWithSeats t : theatersWithSeats) {
                    if (t.getTheater().getName().equals(selectedTheater)) {
                        theaterId = t.getTheater().getId();
                        break;
                    }
                }
                
                if (theaterId != -1) {
                    searchResults = Dao.MovieScheduleDao.queryListByTheaterId(theaterId);
                }
            } else {
                // Không có điều kiện tìm kiếm, lấy tất cả
                searchResults = Dao.MovieScheduleDao.queryList();
            }
            
            // Hiển thị kết quả tìm kiếm
            displaySearchResults(searchResults, moviesWithGenres, theatersWithSeats);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void displaySearchResults(List<Model.MovieSchedule> schedules, 
                                   List<MovieDao.MovieWithGenres> moviesWithGenres, 
                                   List<TheaterDao.TheaterWithSeats> theatersWithSeats) {
        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);
        
        if (schedules != null && !schedules.isEmpty()) {
            Object[][] searchData = new Object[schedules.size()][6];
            
            for (int i = 0; i < schedules.size(); i++) {
                Model.MovieSchedule schedule = schedules.get(i);
                
                // Lấy ngày và giờ từ API
                String showDate = schedule.getShowDate().toString();
                String startTime = schedule.getStartTime().toString();
                
                // Lấy tên phim từ movie_id
                String movieTitle = "";
                for (MovieDao.MovieWithGenres m : moviesWithGenres) {
                    if (m.getMovie().getId() == schedule.getMovieId()) {
                        movieTitle = m.getMovie().getTitle();
                        break;
                    }
                }
                
                // Lấy tên phòng từ theater_id
                String theaterName = "";
                for (TheaterDao.TheaterWithSeats t : theatersWithSeats) {
                    if (t.getTheater().getId() == schedule.getTheaterId()) {
                        theaterName = t.getTheater().getName();
                        break;
                    }
                }
                
                searchData[i][0] = showDate;
                searchData[i][1] = startTime;
                searchData[i][2] = movieTitle;
                searchData[i][3] = theaterName;
                searchData[i][4] = String.format("%.0f VNĐ", schedule.getPrice()); // Thêm giá
                searchData[i][5] = schedule.getId();
            }
            
            // Thêm dữ liệu vào bảng
            for (Object[] row : searchData) {
                tableModel.addRow(row);
            }
            
            System.out.println("Tìm thấy " + schedules.size() + " lịch chiếu");
        } else {
            System.out.println("Không tìm thấy lịch chiếu nào");
        }
    }

	@Override
	protected void handleDelete(int selectedRow) {
		try {
			// Lấy ID của schedule từ bảng
			Object scheduleIdObj = tableModel.getValueAt(selectedRow, 5); // Cột ID ở vị trí 5
			if (scheduleIdObj == null) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy ID của lịch chiếu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int scheduleId = Integer.parseInt(scheduleIdObj.toString());
			
			// Hiển thị dialog xác nhận
			int confirm = JOptionPane.showConfirmDialog(
				this, 
				"Bạn có chắc chắn muốn xóa lịch chiếu này?", 
				"Xác nhận xóa", 
				JOptionPane.YES_NO_OPTION
			);
			
			if (confirm == JOptionPane.YES_OPTION) {
				// Gọi API delete
				boolean deleted = Dao.MovieScheduleDao.delete(scheduleId);
				
				if (deleted) {
					// Reload lại bảng
					tableModel.setRowCount(0);
					addSampleData();
					clearForm();
					JOptionPane.showMessageDialog(this, "Xóa lịch chiếu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Xóa lịch chiếu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi xóa lịch chiếu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	@Override
	protected void setupListeners() {
		super.setupListeners();
		
		// Override nút Refresh để reload dữ liệu
		if (buttonPanel != null) {
			buttonPanel.getBtnRefresh().removeActionListener(buttonPanel.getBtnRefresh().getActionListeners()[0]);
			buttonPanel.getBtnRefresh().addActionListener(e -> {
				try {
					// Xóa dữ liệu cũ trong bảng
					tableModel.setRowCount(0);
					
					// Reload lại dữ liệu từ database
					addSampleData();
					
					// Clear form
					clearForm();
					
					// Clear search fields
					JComboBox<String>[] searchCombos = searchPanel.getSearchCombos();
					for (JComboBox<String> combo : searchCombos) {
						combo.setSelectedIndex(0); // Reset về item đầu tiên
					}
					
					// Reset selection
					table.clearSelection();
					buttonPanel.getBtnEdit().setEnabled(false);
					buttonPanel.getBtnDelete().setEnabled(false);
					buttonPanel.getBtnAdd().setVisible(true);
					for (JButton btn : buttonPanel.getSpecialButtons()) {
						btn.setEnabled(false);
					}
					
					System.out.println("Đã làm mới danh sách lịch chiếu");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Lỗi khi làm mới: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			});
		}
	}
} 