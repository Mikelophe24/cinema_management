package View.Admin.panels;

import View.Admin.common.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class StaffPanel extends BasePanel {
    
    public StaffPanel() {
        super("Quản lý nhân viên", "👥");
    }
    
    @Override
    protected void createComponents() {
        // Tạo search panel
        String[] fieldLabels = {"Họ tên", "SĐT"};
        String[] comboLabels = {"Chức vụ"};
        String[][] comboOptions = {{"Tất cả", "Quản lý", "Nhân viên bán hàng", "Nhân viên vệ sinh", "Bảo vệ", "Kỹ thuật viên"}};
        
        searchPanel = new SearchPanel(fieldLabels, comboLabels, comboOptions);
        
        // Tạo table
        String[] columns = {"ID", "Họ tên", "SĐT", "Email", "Chức vụ", "Trạng thái", "Ngày vào làm"};
        table = createStyledTable(columns);
        
        // Tạo form panel
        String[] formFieldLabels = {"Họ tên", "SĐT", "Email", "Địa chỉ"};
        String[] formComboLabels = {"Chức vụ"};
        String[][] formComboOptions = {{"Quản lý", "Nhân viên bán hàng", "Nhân viên vệ sinh", "Bảo vệ", "Kỹ thuật viên"}};
        
        formPanel = new FormPanel("Thông tin nhân viên", formFieldLabels, formComboLabels, formComboOptions);
        
        // Tạo button panel
        buttonPanel = new ButtonPanel(new String[]{});
    }
    
    @Override
    protected void addSampleData() {
        Object[][] sampleData = {
            {1, "Nguyễn Văn A", "0901234567", "nguyenvana@cinema.com", "Quản lý", "Đang làm việc", "2023-01-15"},
            {2, "Trần Thị B", "0901234568", "tranthib@cinema.com", "Nhân viên bán hàng", "Đang làm việc", "2023-02-20"},
            {3, "Lê Văn C", "0901234569", "levanc@cinema.com", "Nhân viên vệ sinh", "Đang làm việc", "2023-03-10"},
            {4, "Phạm Thị D", "0901234570", "phamthid@cinema.com", "Bảo vệ", "Đang làm việc", "2023-04-05"},
            {5, "Hoàng Văn E", "0901234571", "hoangvane@cinema.com", "Kỹ thuật viên", "Nghỉ việc", "2023-05-12"},
            {6, "Vũ Thị F", "0901234572", "vuthif@cinema.com", "Nhân viên bán hàng", "Đang làm việc", "2023-06-18"},
            {7, "Đặng Văn G", "0901234573", "dangvang@cinema.com", "Quản lý", "Đang làm việc", "2023-07-25"},
            {8, "Bùi Thị H", "0901234574", "buithih@cinema.com", "Nhân viên vệ sinh", "Đang làm việc", "2023-08-30"},
            {9, "Lý Văn I", "0901234575", "lyvani@cinema.com", "Bảo vệ", "Đang làm việc", "2023-09-14"},
            {10, "Hồ Thị K", "0901234576", "hothik@cinema.com", "Kỹ thuật viên", "Đang làm việc", "2023-10-22"}
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
            formPanel.setTextFieldValue(2, table.getValueAt(selectedRow, 3).toString());
            formPanel.setTextFieldValue(3, "Địa chỉ mẫu");
            formPanel.setComboBoxValue(0, table.getValueAt(selectedRow, 4).toString());
        }
    }
    
    @Override
    protected void clearForm() {
        formPanel.clearForm();
    }
    
    @Override
    protected Map<String, String> getFormData() {
        Map<String, String> data = new HashMap<>();
        data.put("fullName", formPanel.getTextFieldValue(0));
        data.put("phone", formPanel.getTextFieldValue(1));
        data.put("email", formPanel.getTextFieldValue(2));
        data.put("address", formPanel.getTextFieldValue(3));
        data.put("position", formPanel.getComboBoxValue(0));
        return data;
    }
    
    @Override
    protected void handleEdit(int selectedRow) {
        System.out.println("Sửa nhân viên ở hàng: " + selectedRow);
        System.out.println("Dữ liệu form: " + getFormData());
    }
    
    @Override
    protected void handleAdd() {
        System.out.println("Thêm nhân viên mới");
        System.out.println("Dữ liệu form: " + getFormData());
    }
    
    @Override
    protected void handleSearch() {
        JTextField[] searchFields = searchPanel.getSearchFields();
        JComboBox<String>[] searchCombos = searchPanel.getSearchCombos();
        
        String fullName = searchFields[0].getText().trim();
        String phone = searchFields[1].getText().trim();
        String position = searchCombos[0].getSelectedItem().toString();
        
        System.out.println("Tìm kiếm: " + fullName + ", " + phone + ", " + position);
    }

	@Override
	protected void handleDelete(int selectedRow) {
		// TODO Auto-generated method stub
		
	}
} 