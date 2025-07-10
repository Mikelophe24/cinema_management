package View.Admin.common;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public abstract class BasePanel extends JPanel {
    protected JTable table;
    protected JLabel titleLabel;
    protected SearchPanel searchPanel;
    protected FormPanel formPanel;
    protected ButtonPanel buttonPanel;
    protected DefaultTableModel tableModel;
    
    public BasePanel(String title, String icon) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 248, 255));
        
        // Tạo title
        createTitle(title, icon);
        
        // Tạo các component con
        createComponents();

        // Gộp titleLabel và searchPanel vào một JPanel rồi add vào NORTH
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.setBackground(getBackground());
        northPanel.add(titleLabel, BorderLayout.NORTH);
        if (searchPanel != null) {
            northPanel.add(searchPanel, BorderLayout.SOUTH);
        }
        add(northPanel, BorderLayout.NORTH);

        // Table luôn ở CENTER
        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.setBorder(new EmptyBorder(10, 20, 10, 20));
        add(scrollTable, BorderLayout.CENTER);

        // Gộp formPanel và buttonPanel vào bottomPanel, add vào SOUTH nếu có
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        boolean hasBottom = false;
        if (formPanel != null) {
            bottomPanel.add(formPanel, BorderLayout.CENTER);
            hasBottom = true;
        }
        if (buttonPanel != null) {
            bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
            hasBottom = true;
        }
        if (hasBottom) {
            add(bottomPanel, BorderLayout.SOUTH);
        }
        
        // Setup listeners
        setupListeners();
        
        // Thêm dữ liệu mẫu
        addSampleData();
    }
    
    private void createTitle(String title, String icon) {
        titleLabel = new JLabel(icon + " " + title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(30, 111, 255));
        titleLabel.setBorder(new EmptyBorder(20, 0, 10, 0));
    }
    
    protected JTable createStyledTable(String[] columns) {
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 248, 255));
                } else {
                    c.setBackground(new Color(135, 206, 250));
                }
                return c;
            }
        };
        table.setRowHeight(24);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return table;
    }
    
    protected void setupListeners() {
        if (buttonPanel != null) {
            // Table selection listener
            table.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    buttonPanel.getBtnEdit().setEnabled(true);
                    buttonPanel.getBtnDelete().setEnabled(true);
                    buttonPanel.getBtnAdd().setVisible(false);
                    // Enable special buttons
                    for (JButton btn : buttonPanel.getSpecialButtons()) {
                        btn.setEnabled(true);
                    }
                    // Hiển thị dữ liệu từ table vào form
                    displaySelectedRowData();
                }
            });
            // Refresh button listener
            buttonPanel.getBtnRefresh().addActionListener(e -> {
                table.clearSelection();
                buttonPanel.getBtnEdit().setEnabled(false);
                buttonPanel.getBtnDelete().setEnabled(false);
                buttonPanel.getBtnAdd().setVisible(true);
                // Disable special buttons
                for (JButton btn : buttonPanel.getSpecialButtons()) {
                    btn.setEnabled(false);
                }
                clearForm();
            });
            // Edit button listener
            buttonPanel.getBtnEdit().addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int confirm = JOptionPane.showConfirmDialog(this, 
                        "Bạn có chắc chắn muốn sửa bản ghi này?", 
                        "Xác nhận sửa", 
                        JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        handleEdit(selectedRow);
                    }
                }
            });
            // Delete button listener
            buttonPanel.getBtnDelete().addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int confirm = JOptionPane.showConfirmDialog(this, 
                        "Bạn có chắc chắn muốn xóa bản ghi này?", 
                        "Xác nhận xóa", 
                        JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        tableModel.removeRow(selectedRow);
                        clearForm();
                        buttonPanel.getBtnEdit().setEnabled(false);
                        buttonPanel.getBtnDelete().setEnabled(false);
                        buttonPanel.getBtnAdd().setVisible(true);
                        // Disable special buttons
                        for (JButton btn : buttonPanel.getSpecialButtons()) {
                            btn.setEnabled(false);
                        }
                    }
                }
            });
            // Add button listener
            buttonPanel.getBtnAdd().addActionListener(e -> {
                handleAdd();
            });
        }
        if (searchPanel != null) {
            // Search button listener
            searchPanel.setSearchAction(e -> {
                handleSearch();
            });
        }
    }
    
    // Abstract methods that subclasses must implement
    protected abstract void createComponents();
    protected abstract void addSampleData();
    protected abstract void displaySelectedRowData();
    protected abstract void clearForm();
    protected abstract Map<String, String> getFormData();
    protected abstract void handleEdit(int selectedRow);
    protected abstract void handleAdd();
    protected abstract void handleSearch();
} 