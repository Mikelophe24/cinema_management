//package View.Admin.panels;
//
//import View.Admin.common.*;
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import javax.swing.table.TableRowSorter;
//import java.util.HashMap;
//import java.util.Map;
//
//public class SchedulePanel extends BasePanel {
//    public SchedulePanel() {
//        super("Quản lý lịch chiếu phim", "🗓️");
//    }
//
//    @Override
//    protected void createComponents() {
//        // SearchPanel: chỉ có trường text
//        String[] searchFieldLabels = {"Phim", "Phòng"};
//        String[] searchComboLabels = {};
//        String[][] searchComboOptions = {};
//        searchPanel = new SearchPanel(searchFieldLabels, searchComboLabels, searchComboOptions);
//
//        // Table
//        String[] columns = {"Ngày", "Giờ", "Phim", "Phòng"};
//        table = createStyledTable(columns);
//
//        // FormPanel: chỉ có trường text
//        String[] formFieldLabels = {"Ngày", "Giờ", "Phim", "Phòng"};
//        String[] formComboLabels = {};
//        String[][] formComboOptions = {};
//        formPanel = new FormPanel("Thông tin lịch chiếu", formFieldLabels, formComboLabels, formComboOptions);
//
//        // ButtonPanel: không có button đặc biệt
//        buttonPanel = new ButtonPanel(new String[]{});
//    }
//
//    @Override
//    protected void addSampleData() {
//        Object[][] sampleData = {
//            {"2024-05-20", "10:00", "Avengers: Endgame", "Phòng 1"},
//            {"2024-05-20", "13:00", "Titanic", "Phòng 2"},
//            {"2024-05-20", "15:30", "The Dark Knight", "Phòng 3"},
//            {"2024-05-21", "09:00", "Frozen", "Phòng 5"},
//            {"2024-05-21", "11:00", "Inception", "Phòng 4"},
//            {"2024-05-21", "14:00", "The Matrix", "Phòng 1"}
//        };
//        for (Object[] row : sampleData) {
//            tableModel.addRow(row);
//        }
//    }
//
//    @Override
//    protected void displaySelectedRowData() {
//        int row = table.getSelectedRow();
//        if (row != -1) {
//            for (int i = 0; i < 4; i++) {
//                formPanel.setTextFieldValue(i, tableModel.getValueAt(row, i).toString());
//            }
//        }
//    }
//
//    @Override
//    protected void clearForm() {
//        formPanel.clearForm();
//    }
//
//    @Override
//    protected Map<String, String> getFormData() {
//        Map<String, String> data = new HashMap<>();
//        data.put("Ngày", formPanel.getTextFieldValue(0));
//        data.put("Giờ", formPanel.getTextFieldValue(1));
//        data.put("Phim", formPanel.getTextFieldValue(2));
//        data.put("Phòng", formPanel.getTextFieldValue(3));
//        return data;
//    }
//
//    @Override
//    protected void handleEdit(int selectedRow) {
//        Map<String, String> data = getFormData();
//        for (int i = 0; i < 4; i++) {
//            tableModel.setValueAt(data.values().toArray()[i], selectedRow, i);
//        }
//    }
//
//    @Override
//    protected void handleAdd() {
//        Map<String, String> data = getFormData();
//        tableModel.addRow(new Object[]{
//            data.get("Ngày"),
//            data.get("Giờ"),
//            data.get("Phim"),
//            data.get("Phòng")
//        });
//        clearForm();
//    }
//
//    @Override
//    protected void handleSearch() {
//        JTextField[] searchFields = searchPanel.getSearchFields();
//        String movie = searchFields[0].getText().trim().toLowerCase();
//        String room = searchFields[1].getText().trim().toLowerCase();
//        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
//        table.setRowSorter(sorter);
//        RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter("(?i)" + movie, 2);
//        if (!room.isEmpty()) {
//            filter = RowFilter.andFilter(java.util.Arrays.asList(
//                RowFilter.regexFilter("(?i)" + movie, 2),
//                RowFilter.regexFilter("(?i)" + room, 3)
//            ));
//        }
//        sorter.setRowFilter(filter);
//    }
//}