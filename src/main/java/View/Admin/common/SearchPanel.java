package View.Admin.common;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchPanel extends JPanel {
    private JTextField[] searchFields;
    private JComboBox<String>[] searchCombos;
    private JButton searchButton;
    
    public SearchPanel(String[] fieldLabels, String[] comboLabels, String[][] comboOptions) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setBackground(new Color(245, 248, 255));
        
        searchFields = new JTextField[fieldLabels.length];
        searchCombos = new JComboBox[comboLabels.length];
        
        // Tạo search fields
        for (int i = 0; i < fieldLabels.length; i++) {
            add(new JLabel(fieldLabels[i] + ":"));
            searchFields[i] = new JTextField(12);
            add(searchFields[i]);
        }
        
        // Tạo search combos
        for (int i = 0; i < comboLabels.length; i++) {
            add(new JLabel(comboLabels[i] + ":"));
            searchCombos[i] = new JComboBox<>(comboOptions[i]);
            add(searchCombos[i]);
        }
        
        // Tạo search button
        searchButton = new JButton("Tìm kiếm");
        searchButton.setBackground(new Color(200, 220, 255));
        searchButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        add(searchButton);
    }
    
    public void setSearchAction(ActionListener action) {
        searchButton.addActionListener(action);
    }
    
    // Getters cho các field và combo
    public JTextField[] getSearchFields() { 
        return searchFields; 
    }
    
    public JComboBox<String>[] getSearchCombos() { 
        return searchCombos; 
    }
    
    public JButton getSearchButton() {
        return searchButton;
    }
} 