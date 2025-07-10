package View.Admin.common;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class FormPanel extends JPanel {
    private JTextField[] textFields;
    private JComboBox<String>[] comboBoxes;
    
    public FormPanel(String title, String[] fieldLabels, String[] comboLabels, String[][] comboOptions) {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(new CompoundBorder(
            new EmptyBorder(20, 20, 20, 20),
            new TitledBorder(new LineBorder(new Color(200, 200, 255)), title, 
                           TitledBorder.LEFT, TitledBorder.TOP, 
                           new Font("Segoe UI", Font.BOLD, 14))
        ));
        
        textFields = new JTextField[fieldLabels.length];
        comboBoxes = new JComboBox[comboLabels.length];
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        
        // Create text fields
        for (int i = 0; i < fieldLabels.length; i++) {
            int row = i / 2;
            int col = (i % 2) * 2;
            
            gbc.gridx = col; gbc.gridy = row;
            add(new JLabel(fieldLabels[i] + ":"), gbc);
            gbc.gridx = col + 1;
            textFields[i] = new JTextField();
            add(textFields[i], gbc);
        }
        
        // Create combo boxes
        for (int i = 0; i < comboLabels.length; i++) {
            int row = (fieldLabels.length + i) / 2;
            int col = ((fieldLabels.length + i) % 2) * 2;
            
            gbc.gridx = col; gbc.gridy = row;
            add(new JLabel(comboLabels[i] + ":"), gbc);
            gbc.gridx = col + 1;
            // Use an empty array if comboOptions[i] is null
            String[] options = (comboOptions[i] != null) ? comboOptions[i] : new String[0];
            comboBoxes[i] = new JComboBox<>(options);
            add(comboBoxes[i], gbc);
        }
    }
    
    // Getters và setters
    public JTextField[] getTextFields() { 
        return textFields; 
    }
    
    public JComboBox<String>[] getComboBoxes() { 
        return comboBoxes; 
    }
    
    public void clearForm() {
        for (JTextField field : textFields) {
            field.setText("");
        }
        for (JComboBox<String> combo : comboBoxes) {
            combo.setSelectedIndex(0);
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