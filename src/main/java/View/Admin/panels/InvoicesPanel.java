package View.Admin.panels;

import View.Admin.common.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import Dao.InvoiceDao;
import Dao.AccountDao;
import Model.Invoice;
import Model.Account;

public class InvoicesPanel extends BasePanel {
    
    public InvoicesPanel() {
        super("Quản lý hóa đơn", "🧾");
    }
    
    @Override
    protected void createComponents() {
        // Tạo search panel - chỉ có mã HD và khách hàng
        String[] fieldLabels = {"Mã HD", "Khách hàng"};
        String[] comboLabels = {};
        String[][] comboOptions = {};
        
        searchPanel = new SearchPanel(fieldLabels, comboLabels, comboOptions);
        
        // Tạo table - bỏ qua phương thức và trạng thái
        String[] columns = {"ID", "Mã HD", "Khách hàng", "Tổng tiền", "Ngày tạo"};
        table = createStyledTable(columns);
        
        // Tạo form panel - chỉ có mã HD, khách hàng (text field), tổng tiền
        String[] formFieldLabels = {"Mã HD", "Khách hàng", "Tổng tiền"};
        String[] formComboLabels = {};
        String[][] formComboOptions = {};
        
        formPanel = new FormPanel("Thông tin hóa đơn", formFieldLabels, formComboLabels, formComboOptions);
        
        // Tạo button panel
        buttonPanel = new ButtonPanel(new String[]{});
    }
    
    @Override
    protected void addSampleData() {
        try {
            tableModel.setRowCount(0); // Clear existing data
            List<Invoice> invoices = InvoiceDao.queryList();
            
            if (invoices != null && !invoices.isEmpty()) {
                for (Invoice invoice : invoices) {
                    Account account = AccountDao.getById(invoice.getAccountId());
                    String customerName = account != null ? account.getDisplayName() : "Không xác định";
                    
                    System.out.println("Invoice ID: " + invoice.getId() + ", Account ID: " + invoice.getAccountId() + ", Customer Name: '" + customerName + "'");
                    
                    Object[] row = {
                        invoice.getId(),
                        "HD" + String.format("%03d", invoice.getId()),
                        customerName,
                        String.format("%,.0f VNĐ", invoice.getTotalAmount()),
                        invoice.getBookingDate() != null ? invoice.getBookingDate().toString() : ""
                    };
                    tableModel.addRow(row);
                }
                System.out.println("Đã tải " + invoices.size() + " hóa đơn từ database");
            } else {
                System.out.println("Không có hóa đơn nào trong database");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tải danh sách hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    protected void displaySelectedRowData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Lấy mã HD từ cột 1
            String invoiceCode = table.getValueAt(selectedRow, 1).toString();
            formPanel.setTextFieldValue(0, invoiceCode);
            
            // Lấy tên khách hàng từ cột 2 và set vào text field
            String customerName = table.getValueAt(selectedRow, 2).toString();
            System.out.println("=== DEBUG: Hiển thị dữ liệu từ bảng ===");
            System.out.println("Tên khách hàng từ bảng: '" + customerName + "'");
            formPanel.setTextFieldValue(1, customerName);
            
            // Lấy tổng tiền từ cột 3 (loại bỏ " VNĐ")
            String totalAmountText = table.getValueAt(selectedRow, 3).toString();
            String totalAmount = totalAmountText.replace(" VNĐ", "").replace(",", "");
            formPanel.setTextFieldValue(2, totalAmount);
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
        
        System.out.println("=== DEBUG: Lấy dữ liệu từ form ===");
        System.out.println("Tên khách hàng từ form: '" + data.get("customer") + "'");
        
        return data;
    }
    
    @Override
    protected void handleEdit(int selectedRow) {
        // Tạm thời ẩn chức năng sửa invoice
        JOptionPane.showMessageDialog(this, "Chức năng sửa hóa đơn đang được bảo trì!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        
        /*
        try {
            Map<String, String> data = getFormData();
            
            // Lấy ID của invoice từ bảng
            Object invoiceIdObj = tableModel.getValueAt(selectedRow, 0);
            if (invoiceIdObj == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy ID của hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int invoiceId = Integer.parseInt(invoiceIdObj.toString());
            
            // Parse tổng tiền
            double totalAmount = 0.0;
            try {
                totalAmount = Double.parseDouble(data.get("totalAmount"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tổng tiền không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Tìm account ID từ tên khách hàng
            List<Account> accounts = AccountDao.getAll();
            int accountId = -1;
            String customerName = data.get("customer");
            
            System.out.println("=== DEBUG: Tìm khách hàng ===");
            System.out.println("Tên khách hàng cần tìm: '" + customerName + "'");
            System.out.println("Tổng số accounts: " + accounts.size());
            
            for (Account account : accounts) {
                String accountDisplayName = account.getDisplayName();
                System.out.println("Account ID: " + account.getAccountId() + ", Display Name: '" + accountDisplayName + "'");
                
                if (accountDisplayName != null && accountDisplayName.equals(customerName)) {
                    accountId = account.getAccountId();
                    System.out.println("Tìm thấy! Account ID: " + accountId);
                    break;
                } else if (accountDisplayName != null && accountDisplayName.trim().equals(customerName.trim())) {
                    accountId = account.getAccountId();
                    System.out.println("Tìm thấy (sau trim)! Account ID: " + accountId);
                    break;
                } else if (accountDisplayName != null) {
                    System.out.println("So sánh: '" + accountDisplayName + "' vs '" + customerName + "'");
                    System.out.println("Length: " + accountDisplayName.length() + " vs " + customerName.length());
                    System.out.println("Equals: " + accountDisplayName.equals(customerName));
                    System.out.println("Trim equals: " + accountDisplayName.trim().equals(customerName.trim()));
                }
            }
            
            if (accountId == -1) {
                System.out.println("Không tìm thấy khách hàng với tên: '" + customerName + "'");
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng: " + customerName, "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Tạo Map để update
            Map<String, Object> updateFields = new HashMap<>();
            updateFields.put("account_id", accountId);
            updateFields.put("total_amount", totalAmount);
            
            System.out.println("Update fields: " + updateFields);
            
            // Gọi API update
            boolean updated = InvoiceDao.update(invoiceId, updateFields);
            
            if (updated) {
                // Reload lại bảng
                tableModel.setRowCount(0);
                addSampleData();
                clearForm();
                JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        */
    }
    
    @Override
    protected void handleAdd() {
        try {
            Map<String, String> data = getFormData();
            
            // Parse tổng tiền
            double totalAmount = 0.0;
            try {
                totalAmount = Double.parseDouble(data.get("totalAmount"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Tổng tiền không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Tìm account ID từ tên khách hàng
            List<Account> accounts = AccountDao.getAll();
            int accountId = -1;
            String customerName = data.get("customer");
            
            System.out.println("=== DEBUG: Thêm hóa đơn ===");
            System.out.println("Tên khách hàng cần tìm: '" + customerName + "'");
            System.out.println("Tổng số accounts: " + accounts.size());
            
            for (Account account : accounts) {
                String accountDisplayName = account.getDisplayName();
                System.out.println("Account ID: " + account.getAccountId() + ", Display Name: '" + accountDisplayName + "'");
                
                if (accountDisplayName != null && accountDisplayName.equals(customerName)) {
                    accountId = account.getAccountId();
                    System.out.println("Tìm thấy! Account ID: " + accountId);
                    break;
                }
            }
            
            if (accountId == -1) {
                System.out.println("Không tìm thấy khách hàng với tên: '" + customerName + "'");
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng: " + customerName, "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Tạo Invoice và lưu vào DB - sử dụng constructor đúng
            Invoice invoice = new Invoice(0, accountId, 0, totalAmount, null);
            InvoiceDao.create(invoice);
            
            // Reload lại bảng
            tableModel.setRowCount(0);
            addSampleData();
            clearForm();
            JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    @Override
    protected void handleSearch() {
        try {
            // Lấy giá trị từ search fields
            JTextField[] searchFields = searchPanel.getSearchFields();
            String invoiceCode = searchFields[0].getText().trim();
            String customer = searchFields[1].getText().trim();
            
            System.out.println("=== DEBUG: Tìm kiếm ===");
            System.out.println("Mã HD: '" + invoiceCode + "'");
            System.out.println("Khách hàng: '" + customer + "'");
            
            // Lấy danh sách hóa đơn từ database
            List<Invoice> invoices = InvoiceDao.queryList();
            List<Account> accounts = AccountDao.getAll();
            
            // Lọc kết quả tìm kiếm
            List<Invoice> searchResults = new java.util.ArrayList<>();
            
            for (Invoice invoice : invoices) {
                Account account = AccountDao.getById(invoice.getAccountId());
                String customerName = account != null ? account.getDisplayName() : "";
                String currentInvoiceCode = "HD" + String.format("%03d", invoice.getId());
                
                boolean matchInvoiceCode = invoiceCode.isEmpty() || currentInvoiceCode.toLowerCase().contains(invoiceCode.toLowerCase());
                boolean matchCustomer = customer.isEmpty() || customerName.toLowerCase().contains(customer.toLowerCase());
                
                if (matchInvoiceCode && matchCustomer) {
                    searchResults.add(invoice);
                }
            }
            
            // Hiển thị kết quả tìm kiếm
            displaySearchResults(searchResults, accounts);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void displaySearchResults(List<Invoice> invoices, List<Account> accounts) {
        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);
        
        if (invoices != null && !invoices.isEmpty()) {
            for (Invoice invoice : invoices) {
                Account account = AccountDao.getById(invoice.getAccountId());
                String customerName = account != null ? account.getDisplayName() : "Không xác định";
                Object[] row = {
                    invoice.getId(),
                    "HD" + String.format("%03d", invoice.getId()),
                    customerName,
                    String.format("%,.0f VNĐ", invoice.getTotalAmount()),
                    invoice.getBookingDate() != null ? invoice.getBookingDate().toString() : ""
                };
                tableModel.addRow(row);
            }
            System.out.println("Tìm thấy " + invoices.size() + " hóa đơn");
        } else {
            System.out.println("Không tìm thấy hóa đơn nào");
        }
    }
    
    @Override
    protected void handleDelete(int selectedRow) {
        try {
            // Lấy ID của invoice từ bảng
            Object invoiceIdObj = tableModel.getValueAt(selectedRow, 0);
            if (invoiceIdObj == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy ID của hóa đơn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int invoiceId = Integer.parseInt(invoiceIdObj.toString());
            
            // Hiển thị dialog xác nhận
            int confirm = JOptionPane.showConfirmDialog(
                this, 
                "Bạn có chắc chắn muốn xóa hóa đơn này?", 
                "Xác nhận xóa", 
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Gọi API delete
                boolean deleted = InvoiceDao.deleteById(invoiceId);
                
                if (deleted) {
                    // Reload lại bảng
                    tableModel.setRowCount(0);
                    addSampleData();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Xóa hóa đơn thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa hóa đơn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa hóa đơn: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    @Override
    protected void setupListeners() {
        super.setupListeners();
        
        // Ẩn nút Edit
        if (buttonPanel != null && buttonPanel.getBtnEdit() != null) {
            buttonPanel.getBtnEdit().setVisible(false);
        }
        
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
                    JTextField[] searchFields = searchPanel.getSearchFields();
                    for (JTextField field : searchFields) {
                        field.setText("");
                    }
                    
                    // Reset selection
                    table.clearSelection();
                    buttonPanel.getBtnEdit().setEnabled(false);
                    buttonPanel.getBtnDelete().setEnabled(false);
                    buttonPanel.getBtnAdd().setVisible(true);
                    for (JButton btn : buttonPanel.getSpecialButtons()) {
                        btn.setEnabled(false);
                    }
                    
                    System.out.println("Đã làm mới danh sách hóa đơn");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi làm mới: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            });
        }
    }
} 