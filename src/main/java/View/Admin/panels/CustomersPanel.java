package View.Admin.panels;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Model.Customer;
import View.Admin.common.BasePanel;
import View.Admin.common.ButtonPanel;
import View.Admin.common.FormPanel;
import View.Admin.common.SearchPanel;

public class CustomersPanel extends BasePanel {

	public CustomersPanel() {
		super("Quản lý khách hàng", "👥");
	}

	@Override
	protected void createComponents() {
		String[] fieldLabels = { "Họ tên", "SĐT" };
		searchPanel = new SearchPanel(fieldLabels);

		String[] columns = { "ID", "Họ tên", "SĐT", "Email", "Giới tính", "Ngày sinh", "Địa chỉ" };
		table = createStyledTable(columns);

		String[] formFieldLabels = { "Họ tên", "SĐT", "Email", "Địa chỉ", "Ngày sinh" };
		String[] formComboLabels = { "Giới tính" };
		String[][] formComboOptions = { { "Nam", "Nữ" } };
		formPanel = new FormPanel("Thông tin khách hàng", formFieldLabels, formComboLabels, formComboOptions);

		buttonPanel = new ButtonPanel(new String[] {});
		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				displaySelectedRowData();
			}
		});
	}

	@Override
	protected void addSampleData() {
		try {
			List<Customer> customers = Dao.CustomerDao.queryList();
			for (Customer c : customers) {
				Object[] row = { c.getId(), c.getFullName(), c.getPhoneNumber(), c.getEmail(),
						c.getGender() == 1 ? "Nam" : "Nữ", c.getBirthday() != null ? c.getBirthday().toString() : "",
						c.getAddress() != null ? c.getAddress() : "" };
				tableModel.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu khách hàng: " + e.getMessage());
		}
	}

	@Override
	protected void handleDelete(int selectedRow) {
		int id = (int) table.getValueAt(selectedRow, 0);
		try {
			if (Dao.CustomerDao.delete(id)) {
				tableModel.removeRow(selectedRow);
				JOptionPane.showMessageDialog(this, "Xoá khách hàng thành công!");
			} else {
				JOptionPane.showMessageDialog(this, "Xoá thất bại.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Lỗi khi xoá: " + e.getMessage());
		}
	}

	@Override
	protected void displaySelectedRowData() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			Object fullName = table.getValueAt(selectedRow, 1);
			Object phone = table.getValueAt(selectedRow, 2);
			Object email = table.getValueAt(selectedRow, 3);
			Object gender = table.getValueAt(selectedRow, 4);
			Object birthday = table.getValueAt(selectedRow, 5);
			Object address = table.getValueAt(selectedRow, 6);

			formPanel.setTextFieldValue(0, fullName != null ? fullName.toString() : "");
			formPanel.setTextFieldValue(1, phone != null ? phone.toString() : "");
			formPanel.setTextFieldValue(2, email != null ? email.toString() : "");
			formPanel.setComboBoxValue(0, gender != null ? gender.toString() : "Nam");
			formPanel.setTextFieldValue(3, address != null ? address.toString() : "");
			formPanel.setTextFieldValue(4, birthday != null ? birthday.toString() : "");
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
		data.put("gender", formPanel.getComboBoxValue(0).equals("Nam") ? "1" : "0");
		data.put("address", formPanel.getTextFieldValue(3));
		data.put("birthday", formPanel.getTextFieldValue(4));
		return data;
	}

	@Override
	protected void handleAdd() {
		try {
			Map<String, String> data = getFormData();
			LocalDate birthday = null;
			if (!data.get("birthday").isEmpty()) {
				birthday = LocalDate.parse(data.get("birthday"));
			}

			Customer customer = new Customer();
			customer.setAccountId(12);
			customer.setFullName(data.get("fullName"));
			customer.setPhoneNumber(data.get("phone"));
			customer.setEmail(data.get("email"));
			customer.setAddress(data.get("address"));
			customer.setBirthday(birthday);
			customer.setGender(Integer.parseInt(data.get("gender")));

			Customer created = Dao.CustomerDao.create(customer);
			if (created != null) {
				Object[] row = { created.getId(), created.getFullName(), created.getPhoneNumber(), created.getEmail(),
						created.getGender() == 1 ? "Nam" : "Nữ",
						created.getBirthday() != null ? created.getBirthday().toString() : "", created.getAddress() };
				tableModel.addRow(row);
				JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
				clearForm();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Lỗi khi thêm khách hàng: " + ex.getMessage());
		}
	}

	@Override
	protected void handleEdit(int selectedRow) {
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa.");
			return;
		}

		try {
			int id = (int) table.getValueAt(selectedRow, 0);
			Map<String, String> data = getFormData();

			Map<String, Object> updateFields = new HashMap<>();
			updateFields.put("full_name", data.get("fullName"));
			updateFields.put("phone_number", data.get("phone"));
			updateFields.put("email", data.get("email"));
			updateFields.put("address", data.get("address"));
			updateFields.put("gender", Integer.parseInt(data.get("gender")));

			if (!data.get("birthday").isEmpty()) {
				updateFields.put("birthday", Date.valueOf(data.get("birthday")));
			} else {
				updateFields.put("birthday", null);
			}

			boolean updated = Dao.CustomerDao.update(id, updateFields);
			if (updated) {
				table.setValueAt(data.get("fullName"), selectedRow, 1);
				table.setValueAt(data.get("phone"), selectedRow, 2);
				table.setValueAt(data.get("email"), selectedRow, 3);
				table.setValueAt(Integer.parseInt(data.get("gender")) == 1 ? "Nam" : "Nữ", selectedRow, 4);
				table.setValueAt(data.get("birthday"), selectedRow, 5);
				table.setValueAt(data.get("address"), selectedRow, 6);
				JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!");
			} else {
				JOptionPane.showMessageDialog(this, "Không có gì được cập nhật.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Lỗi khi sửa khách hàng: " + ex.getMessage());
		}
	}

	@Override
	protected void handleSearch() {
		JTextField[] searchFields = searchPanel.getSearchFields();
		String fullName = searchFields[0].getText().trim();
		String phone = searchFields[1].getText().trim();

		try {
			List<Customer> results = Dao.CustomerDao.search(fullName, phone);
			clearTable();

			for (Customer c : results) {
				Object[] row = { c.getId(), c.getFullName(), c.getPhoneNumber(), c.getEmail(),
						c.getGender() == 1 ? "Nam" : "Nữ", c.getBirthday() != null ? c.getBirthday().toString() : "",
						c.getAddress() != null ? c.getAddress() : "" };
				tableModel.addRow(row);
			}

			if (results.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng nào phù hợp.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage());
		}
	}

	private void clearTable() {
		while (tableModel.getRowCount() > 0) {
			tableModel.removeRow(0);
		}
	}
}
