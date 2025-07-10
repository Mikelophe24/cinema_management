package View.Admin.panels;

import View.Admin.common.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;

public class CustomersPanel extends BasePanel {
    
    public CustomersPanel() {
        super("Quản lý khách hàng", "👥");
    }
    
    @Override
    protected void createComponents() {
        // Tạo search panel
        String[] fieldLabels = {"Họ tên", "SĐT"};
        String[] comboLabels = {"Loại thành viên"};
        String[][] comboOptions = {{"Tất cả", "Thành viên đồng", "Thành viên bạc", "Thành viên vàng", "Thành viên kim cương"}};
        
        searchPanel = new SearchPanel(fieldLabels, comboLabels, comboOptions);
        
        // Tạo table
        String[] columns = {"ID", "Họ tên", "SĐT", "Email", "Loại thành viên", "Điểm tích lũy", "Ngày đăng ký"};
        table = createStyledTable(columns);
        
        // Tạo form panel
        String[] formFieldLabels = {"Họ tên", "SĐT", "Email", "Địa chỉ"};
        String[] formComboLabels = {"Loại thành viên"};
        String[][] formComboOptions = {{"Thành viên đồng", "Thành viên bạc", "Thành viên vàng", "Thành viên kim cương"}};
        
        formPanel = new FormPanel("Thông tin khách hàng", formFieldLabels, formComboLabels, formComboOptions);
        
        // Tạo button panel
        buttonPanel = new ButtonPanel(new String[]{});
    }
    
    @Override
    protected void addSampleData() {
        Object[][] sampleData = {
            {1, "Nguyễn Văn A", "0901234567", "nguyenvana@email.com", "Thành viên vàng", 1500, "2023-01-15"},
            {2, "Trần Thị B", "0901234568", "tranthib@email.com", "Thành viên đồng", 200, "2023-02-20"},
            {3, "Lê Văn C", "0901234569", "levanc@email.com", "Thành viên bạc", 800, "2023-03-10"},
            {4, "Phạm Thị D", "0901234570", "phamthid@email.com", "Thành viên kim cương", 3000, "2023-04-05"},
            {5, "Hoàng Văn E", "0901234571", "hoangvane@email.com", "Thành viên đồng", 150, "2023-05-12"},
            {6, "Vũ Thị F", "0901234572", "vuthif@email.com", "Thành viên bạc", 600, "2023-06-18"},
            {7, "Đặng Văn G", "0901234573", "dangvang@email.com", "Thành viên vàng", 1200, "2023-07-25"},
            {8, "Bùi Thị H", "0901234574", "buithih@email.com", "Thành viên đồng", 300, "2023-08-30"},
            {9, "Lý Văn I", "0901234575", "lyvani@email.com", "Thành viên kim cương", 2500, "2023-09-14"},
            {10, "Hồ Thị K", "0901234576", "hothik@email.com", "Thành viên bạc", 900, "2023-10-22"}
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
        data.put("memberType", formPanel.getComboBoxValue(0));
        return data;
    }
    
    @Override
    protected void handleEdit(int selectedRow) {
        System.out.println("Sửa khách hàng ở hàng: " + selectedRow);
        System.out.println("Dữ liệu form: " + getFormData());
    }
    
    @Override
    protected void handleAdd() {
        System.out.println("Thêm khách hàng mới");
        System.out.println("Dữ liệu form: " + getFormData());
    }
    
    @Override
    protected void handleSearch() {
        JTextField[] searchFields = searchPanel.getSearchFields();
        JComboBox<String>[] searchCombos = searchPanel.getSearchCombos();
        
        String fullName = searchFields[0].getText().trim();
        String phone = searchFields[1].getText().trim();
        String memberType = searchCombos[0].getSelectedItem().toString();
        
        System.out.println("Tìm kiếm: " + fullName + ", " + phone + ", " + memberType);
    }

	@Override
	protected void handleDelete(int selectedRow) {
		// TODO Auto-generated method stub
		
	}
} 