package View.Admin.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

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

		// Title
		createTitle(title, icon);

		// Sub components
		createComponents();

		// Title + search
		JPanel northPanel = new JPanel(new BorderLayout());
		northPanel.setBackground(getBackground());
		northPanel.add(titleLabel, BorderLayout.NORTH);
		if (searchPanel != null) {
			northPanel.add(searchPanel, BorderLayout.SOUTH);
		}
		add(northPanel, BorderLayout.NORTH);

		// Table scroll center
		JScrollPane scrollTable = new JScrollPane(table);
		scrollTable.setBorder(new EmptyBorder(10, 20, 10, 20));
		add(scrollTable, BorderLayout.CENTER);

		// Bottom (form + button)
		buildBottomPanel();

		// Listeners
		setupListeners();

		// Sample data
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

	private void buildBottomPanel() {
		if (formPanel == null && buttonPanel == null)
			return;

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBackground(Color.WHITE);

		if (formPanel != null) {
			formPanel.setBackground(Color.WHITE);
			formPanel.setPreferredSize(new Dimension(500, 250));
			JScrollPane formScroll = new JScrollPane(formPanel);
			formScroll.setBorder(new EmptyBorder(10, 20, 10, 20));
			bottomPanel.add(formScroll, BorderLayout.CENTER);
		}

		if (buttonPanel != null) {
			buttonPanel.setBackground(Color.WHITE);
			bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
		}

		add(bottomPanel, BorderLayout.SOUTH);
	}

	protected void loadDataFromDatabase() {
		// Mặc định: không làm gì cả
	}

	protected void setupListeners() {
		if (buttonPanel != null) {
			table.getSelectionModel().addListSelectionListener(e -> {
				if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
					buttonPanel.getBtnEdit().setEnabled(true);
					buttonPanel.getBtnDelete().setEnabled(true);
					buttonPanel.getBtnAdd().setVisible(false);
					for (JButton btn : buttonPanel.getSpecialButtons()) {
						btn.setEnabled(true);
					}
					displaySelectedRowData();
				}
			});

			buttonPanel.getBtnRefresh().addActionListener(e -> {
				table.clearSelection();
				buttonPanel.getBtnEdit().setEnabled(false);
				buttonPanel.getBtnDelete().setEnabled(false);
				buttonPanel.getBtnAdd().setVisible(true);
				for (JButton btn : buttonPanel.getSpecialButtons()) {
					btn.setEnabled(false);
				}
				clearForm();
				addSampleData();
			});

			buttonPanel.getBtnEdit().addActionListener(e -> {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn sửa bản ghi này?",
							"Xác nhận sửa", JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						handleEdit(selectedRow);
					}
				}
			});

			buttonPanel.getBtnDelete().addActionListener(e -> {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa bản ghi này?",
							"Xác nhận xóa", JOptionPane.YES_NO_OPTION);
					if (confirm == JOptionPane.YES_OPTION) {
						handleDelete(selectedRow);
						tableModel.removeRow(selectedRow);
						clearForm();
						buttonPanel.getBtnEdit().setEnabled(false);
						buttonPanel.getBtnDelete().setEnabled(false);
						buttonPanel.getBtnAdd().setVisible(true);
						for (JButton btn : buttonPanel.getSpecialButtons()) {
							btn.setEnabled(false);
						}
					}
				}
			});


			buttonPanel.getBtnAdd().addActionListener(e -> {
				handleAdd();
			});
		}

		if (searchPanel != null) {
			searchPanel.setSearchAction(e -> handleSearch());
		}
	}

	// Abstracts
	protected abstract void createComponents();

	protected abstract void addSampleData();

	protected abstract void displaySelectedRowData();

	protected abstract void clearForm();

	protected abstract Map<String, String> getFormData();

	protected abstract void handleEdit(int selectedRow);

	protected abstract void handleAdd();

	protected abstract void handleSearch();

	protected abstract void handleDelete(int selectedRow);
}
