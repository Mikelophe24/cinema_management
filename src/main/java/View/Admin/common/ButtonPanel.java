package View.Admin.common;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {
    private JButton btnAdd, btnEdit, btnDelete, btnRefresh;
    private JButton[] specialButtons;
    
    public ButtonPanel(String[] specialButtonLabels) {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        setBackground(Color.WHITE);
        
        // Tạo các button cơ bản
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xoá");
        btnRefresh = new JButton("Làm mới");
        
        // Tạo các button đặc biệt
        specialButtons = new JButton[specialButtonLabels.length];
        for (int i = 0; i < specialButtonLabels.length; i++) {
            specialButtons[i] = new JButton(specialButtonLabels[i]);
            specialButtons[i].setEnabled(false);
        }
        
        // Style tất cả buttons
        JButton[] allButtons = new JButton[4 + specialButtons.length];
        System.arraycopy(new JButton[]{btnAdd, btnEdit, btnDelete, btnRefresh}, 0, allButtons, 0, 4);
        System.arraycopy(specialButtons, 0, allButtons, 4, specialButtons.length);
        
        for (JButton btn : allButtons) {
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            btn.setBackground(new Color(225, 235, 255));
            btn.setFocusPainted(false);
            add(btn);
        }
        
        // Set trạng thái ban đầu
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
    }
    
    // Getters
    public JButton getBtnAdd() {
        return btnAdd;
    }
    
    public JButton getBtnEdit() {
        return btnEdit;
    }
    
    public JButton getBtnDelete() {
        return btnDelete;
    }
    
    public JButton getBtnRefresh() {
        return btnRefresh;
    }
    
    public JButton[] getSpecialButtons() { 
        return specialButtons; 
    }
    
    public JButton getSpecialButton(int index) {
        if (index >= 0 && index < specialButtons.length) {
            return specialButtons[index];
        }
        return null;
    }
}