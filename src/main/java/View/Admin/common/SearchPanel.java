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
    private JComboBox<String>[] comboBoxes;
    private JButton searchButton;

    /**
     * Constructor: Chỉ có các TextField (không có comboBox)
     */
    public SearchPanel(String[] fieldLabels) {
        // Gọi constructor đầy đủ với comboBox rỗng
        this(fieldLabels, new String[0], new String[0][0]);
    }

    /**
     * Constructor: Có cả TextField và ComboBox
     */
    public SearchPanel(String[] fieldLabels, String[] comboBoxLabels, String[][] comboBoxOptions) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setBackground(new Color(245, 248, 255));

        // Text fields
        searchFields = new JTextField[fieldLabels.length];
        for (int i = 0; i < fieldLabels.length; i++) {
            add(new JLabel(fieldLabels[i] + ":"));
            searchFields[i] = new JTextField(12);
            add(searchFields[i]);
        }

        // Combo boxes
        comboBoxes = new JComboBox[comboBoxLabels.length];
        for (int i = 0; i < comboBoxLabels.length; i++) {
            add(new JLabel(comboBoxLabels[i] + ":"));
            comboBoxes[i] = new JComboBox<>(comboBoxOptions[i]);
            add(comboBoxes[i]);
        }

        // Nút tìm kiếm
        searchButton = new JButton("Tìm kiếm");
        searchButton.setBackground(new Color(200, 220, 255));
        searchButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        add(searchButton);
    }

    /** Gán hành động cho nút tìm kiếm */
    public void setSearchAction(ActionListener action) {
        searchButton.addActionListener(action);
    }

    /** Trả về các ô nhập liệu */
    public JTextField[] getSearchFields() {
        return searchFields;
    }

    /** Trả về các comboBox */
    public JComboBox<String>[] getComboBoxes() {
        return comboBoxes;
    }

    /** Trả về nút tìm kiếm */
    public JButton getSearchButton() {
        return searchButton;
    }

    /** Trả về các comboBox (alias) */
    public JComboBox<String>[] getSearchCombos() {
        return comboBoxes;
    }
}
