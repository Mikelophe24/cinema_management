package View.Admin.panels;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import View.Admin.common.BasePanel;
import View.Admin.common.ButtonPanel;
import View.Admin.common.FormPanel;
import View.Admin.common.SearchPanel;

public class InvoicesPanel extends BasePanel {

	public InvoicesPanel() {
		super("Quản lý hóa đơn", "🧾");
	}

	@Override
	protected void createComponents() {
		// Tạo search panel
		String[] fieldLabels = { "Mã HD", "Khách hàng" };
		String[] comboLabels = { "Phương thức", "Trạng thái" };
		String[][] comboOptions = { { "Tất cả", "Tiền mặt", "Chuyển khoản", "Thẻ tín dụng", "Ví điện tử" },
				{ "Tất cả", "Đã thanh toán", "Chưa thanh toán", "Đã hủy" } };

		searchPanel = new SearchPanel(fieldLabels, comboLabels, comboOptions);

		// Tạo table
		String[] columns = { "ID", "Mã HD", "Khách hàng", "Tổng tiền", "Phương thức", "Trạng thái", "Ngày tạo" };
		table = createStyledTable(columns);

		// Tạo form panel
		String[] formFieldLabels = { "Mã HD", "Khách hàng", "Tổng tiền" };
		String[] formComboLabels = { "Phương thức" };
		String[][] formComboOptions = { { "Tiền mặt", "Chuyển khoản", "Thẻ tín dụng", "Ví điện tử" } };

		formPanel = new FormPanel("Thông tin hóa đơn", formFieldLabels, formComboLabels, formComboOptions);

		// Tạo button panel
		buttonPanel = new ButtonPanel(new String[] {});
	}

	@Override
	protected void addSampleData() {
		Object[][] sampleData = {
				{ 1, "HD001", "Nguyễn Văn A", "150,000 VNĐ", "Tiền mặt", "Đã thanh toán", "2024-01-15" },
				{ 2, "HD002", "Trần Thị B", "200,000 VNĐ", "Chuyển khoản", "Đã thanh toán", "2024-01-16" },
				{ 3, "HD003", "Lê Văn C", "180,000 VNĐ", "Thẻ tín dụng", "Đã thanh toán", "2024-01-17" },
				{ 4, "HD004", "Phạm Thị D", "120,000 VNĐ", "Ví điện tử", "Chưa thanh toán", "2024-01-18" },
				{ 5, "HD005", "Hoàng Văn E", "250,000 VNĐ", "Tiền mặt", "Đã hủy", "2024-01-19" },
				{ 6, "HD006", "Vũ Thị F", "300,000 VNĐ", "Chuyển khoản", "Đã thanh toán", "2024-01-20" },
				{ 7, "HD007", "Đặng Văn G", "175,000 VNĐ", "Thẻ tín dụng", "Đã thanh toán", "2024-01-21" },
				{ 8, "HD008", "Bùi Thị H", "220,000 VNĐ", "Ví điện tử", "Chưa thanh toán", "2024-01-22" },
				{ 9, "HD009", "Lý Văn I", "160,000 VNĐ", "Tiền mặt", "Đã thanh toán", "2024-01-23" },
				{ 10, "HD010", "Hồ Thị K", "280,000 VNĐ", "Chuyển khoản", "Đã thanh toán", "2024-01-24" } };

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
		data.put("invoiceCode", formPanel.getTextFieldValue(0));
		data.put("customer", formPanel.getTextFieldValue(1));
		data.put("totalAmount", formPanel.getTextFieldValue(2));
		data.put("paymentMethod", formPanel.getComboBoxValue(0));
		return data;
	}

	@Override
	protected void handleEdit(int selectedRow) {
		System.out.println("Sửa hóa đơn ở hàng: " + selectedRow);
		System.out.println("Dữ liệu form: " + getFormData());
	}

	@Override
	protected void handleAdd() {
		System.out.println("Thêm hóa đơn mới");
		System.out.println("Dữ liệu form: " + getFormData());
	}

	@Override
	protected void handleSearch() {
		JTextField[] searchFields = searchPanel.getSearchFields();
		JComboBox<String>[] searchCombos = searchPanel.getSearchCombos();

		String invoiceCode = searchFields[0].getText().trim();
		String customer = searchFields[1].getText().trim();
		String paymentMethod = searchCombos[0].getSelectedItem().toString();
		String status = searchCombos[1].getSelectedItem().toString();

		System.out.println("Tìm kiếm: " + invoiceCode + ", " + customer + ", " + paymentMethod + ", " + status);
	}

	@Override
	protected void handleDelete(int selectedRow) {
		// TODO Auto-generated method stub

	}
}
