package View.Admin.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class FormPanel extends JPanel {
	private JTextField[] textFields;
	private JComboBox<String>[] comboBoxes;
	private JLabel[] previewLabels;

	// Constructor cũ
	public FormPanel(String title, String[] fieldLabels, String[] comboLabels, String[][] comboOptions) {
		this(title, fieldLabels, defaultTypes(fieldLabels.length), comboLabels, comboOptions);
	}


	// Constructor rút gọn: cho employee
	public FormPanel(String title, String[] fieldLabels) {
		this(title, fieldLabels, new String[] {}, new String[][] {});
	}

	// Constructor mới
	public FormPanel(String title, String[] fieldLabels, String[] fieldTypes, String[] comboLabels,
			String[][] comboOptions) {

		setLayout(new GridBagLayout());
		setBackground(Color.WHITE);
		setBorder(new CompoundBorder(new EmptyBorder(20, 20, 20, 20),
				new TitledBorder(new LineBorder(new Color(200, 200, 255)), title, TitledBorder.LEFT, TitledBorder.TOP,
						new Font("Segoe UI", Font.BOLD, 14))));

		textFields = new JTextField[fieldLabels.length];
		previewLabels = new JLabel[fieldLabels.length];
		comboBoxes = new JComboBox[comboLabels.length];

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 10, 8, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;

		for (int i = 0; i < fieldLabels.length; i++) {
			int row = i / 2;
			int col = (i % 2) * 2;

			gbc.gridx = col;
			gbc.gridy = row;
			add(new JLabel(fieldLabels[i] + ":"), gbc);
			gbc.gridx = col + 1;

			if ("file".equalsIgnoreCase(fieldTypes[i])) {
				JPanel filePanel = new JPanel(new BorderLayout(5, 0));
				JTextField fileField = new JTextField();
				fileField.setEditable(false);
				JButton browseButton = new JButton("Browse");

				JLabel previewLabel = new JLabel();
				previewLabel.setPreferredSize(new Dimension(120, 120));
				previewLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				previewLabel.setHorizontalAlignment(JLabel.CENTER);

				browseButton.addActionListener(e -> {
					JFileChooser fileChooser = new JFileChooser();
					int result = fileChooser.showOpenDialog(this);
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fileChooser.getSelectedFile();
						fileField.setText(selectedFile.getAbsolutePath());

						if (isImageFile(selectedFile)) {
							ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
							// Scale icon to fit preview size
							ImageIcon scaledIcon = new ImageIcon(
									icon.getImage().getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH));
							previewLabel.setIcon(scaledIcon);
						} else {
							previewLabel.setIcon(null);
						}
					}
				});

				filePanel.add(fileField, BorderLayout.CENTER);
				filePanel.add(browseButton, BorderLayout.EAST);

				JPanel wrapper = new JPanel(new BorderLayout(0, 5));
				wrapper.add(filePanel, BorderLayout.NORTH);
				wrapper.add(previewLabel, BorderLayout.SOUTH);

				textFields[i] = fileField;
				previewLabels[i] = previewLabel;

				add(wrapper, gbc);
			} else {
				JTextField textField = new JTextField();
				textFields[i] = textField;
				add(textField, gbc);
			}
		}

		for (int i = 0; i < comboLabels.length; i++) {
			int row = (fieldLabels.length + i) / 2;
			int col = ((fieldLabels.length + i) % 2) * 2;

			gbc.gridx = col;
			gbc.gridy = row;
			add(new JLabel(comboLabels[i] + ":"), gbc);
			gbc.gridx = col + 1;

			String[] options = (comboOptions[i] != null) ? comboOptions[i] : new String[0];
			comboBoxes[i] = new JComboBox<>(options);
			add(comboBoxes[i], gbc);
		}
	}

	private boolean isImageFile(File file) {
		String name = file.getName().toLowerCase();
		return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif");
	}

	private static String[] defaultTypes(int n) {
		String[] types = new String[n];
		for (int i = 0; i < n; i++)
			types[i] = "text";
		return types;
	}

	public JTextField[] getTextFields() {
		return textFields;
	}

	public JComboBox<String>[] getComboBoxes() {
		return comboBoxes;
	}

	public void clearForm() {
		for (JTextField field : textFields)
			field.setText("");
		for (JComboBox<String> combo : comboBoxes)
			combo.setSelectedIndex(0);
		for (JLabel preview : previewLabels) {
			if (preview != null)
				preview.setIcon(null);
		}
	}

	public void setTextFieldValue(int index, String value) {
		if (index >= 0 && index < textFields.length) {
			textFields[index].setText(value);
		}
	}

	public void setComboBoxValue(int index, String value) {
		if (index >= 0 && index < comboBoxes.length) {
			comboBoxes[index].setSelectedItem(value);
		}
	}

	public String getTextFieldValue(int index) {
		if (index >= 0 && index < textFields.length) {
			return textFields[index].getText();
		}
		return "";
	}

	public String getComboBoxValue(int index) {
		if (index >= 0 && index < comboBoxes.length) {
			return comboBoxes[index].getSelectedItem().toString();
		}
		return "";
	}
}
