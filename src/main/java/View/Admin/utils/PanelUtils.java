package View.Admin.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PanelUtils {
    
    // Colors
    public static final Color BACKGROUND_COLOR = new Color(245, 248, 255);
    public static final Color TITLE_COLOR = new Color(30, 111, 255);
    public static final Color BUTTON_COLOR = new Color(225, 235, 255);
    public static final Color SEARCH_BUTTON_COLOR = new Color(200, 220, 255);
    public static final Color TABLE_ROW_COLOR_1 = Color.WHITE;
    public static final Color TABLE_ROW_COLOR_2 = new Color(240, 248, 255);
    public static final Color TABLE_SELECTION_COLOR = new Color(135, 206, 250);
    
    // Fonts
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font TABLE_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font BORDER_FONT = new Font("Segoe UI", Font.BOLD, 14);
    
    // Common field labels
    public static final String[] COMMON_FIELD_LABELS = {"ID", "Tên", "Mô tả", "Trạng thái"};
    
    // Common combo options
    public static final String[] STATUS_OPTIONS = {"Hoạt động", "Không hoạt động", "Tạm ngưng"};
    public static final String[] PAYMENT_METHODS = {"Tiền mặt", "Chuyển khoản", "Thẻ tín dụng", "Ví điện tử"};
    public static final String[] MEMBERSHIP_TYPES = {"Thành viên đồng", "Thành viên bạc", "Thành viên vàng", "Thành viên kim cương"};
    
    /**
     * Tạo styled button với màu sắc và font chung
     */
    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(BUTTON_COLOR);
        button.setFocusPainted(false);
        return button;
    }
    
    /**
     * Tạo styled label với font chung
     */
    public static JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(BUTTON_FONT);
        return label;
    }
    
    /**
     * Tạo styled text field với font chung
     */
    public static JTextField createStyledTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(TABLE_FONT);
        return textField;
    }
    
    /**
     * Tạo styled combo box với font chung
     */
    public static JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(TABLE_FONT);
        return comboBox;
    }
    
    /**
     * Hiển thị dialog xác nhận
     */
    public static boolean showConfirmDialog(Component parent, String message, String title) {
        int result = JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
    
    /**
     * Hiển thị dialog thông báo
     */
    public static void showMessageDialog(Component parent, String message, String title) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Hiển thị dialog lỗi
     */
    public static void showErrorDialog(Component parent, String message, String title) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Validate text field không được rỗng
     */
    public static boolean validateRequiredField(JTextField field, String fieldName) {
        if (field.getText().trim().isEmpty()) {
            showErrorDialog(field, fieldName + " không được để trống!", "Lỗi nhập liệu");
            field.requestFocus();
            return false;
        }
        return true;
    }
    
    /**
     * Validate phone number format
     */
    public static boolean validatePhoneNumber(JTextField field) {
        String phone = field.getText().trim();
        if (!phone.matches("\\d{10,12}")) {
            showErrorDialog(field, "Số điện thoại phải có 10-12 chữ số!", "Lỗi nhập liệu");
            field.requestFocus();
            return false;
        }
        return true;
    }
    
    /**
     * Validate email format
     */
    public static boolean validateEmail(JTextField field) {
        String email = field.getText().trim();
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showErrorDialog(field, "Email không đúng định dạng!", "Lỗi nhập liệu");
            field.requestFocus();
            return false;
        }
        return true;
    }
    
    /**
     * Tạo Map từ form data
     */
    public static Map<String, String> createFormDataMap(String[] keys, String[] values) {
        Map<String, String> data = new HashMap<>();
        for (int i = 0; i < Math.min(keys.length, values.length); i++) {
            data.put(keys[i], values[i]);
        }
        return data;
    }
    
    /**
     * Format currency
     */
    public static String formatCurrency(double amount) {
        return String.format("%,.0f VNĐ", amount);
    }
    
    /**
     * Parse currency string to double
     */
    public static double parseCurrency(String currencyString) {
        try {
            return Double.parseDouble(currencyString.replaceAll("[^\\d.]", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
} 