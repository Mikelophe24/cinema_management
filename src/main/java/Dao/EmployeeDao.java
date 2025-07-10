package Dao;

import Model.Employee;
import Helper.DatabaseExecutor;
import util.MyConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class EmployeeDao {

    // SQL Statements
    private static final String SQL_CREATE = "INSERT INTO employees (full_name, birthday, address, gender, email, phone_number, hire_date, account_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_QUERY_ONE = "SELECT * FROM employees WHERE id = ?";
    private static final String SQL_QUERY_LIST = "SELECT * FROM employees";
    private static final String SQL_DELETE = "DELETE FROM employees WHERE id = ?";

    private static final String SQL_CHECK_EXIST_BY_ID = "SELECT 1 FROM employees WHERE id = ?";
    private static final String SQL_CHECK_UNIQUE_PHONE = "SELECT 1 FROM employees WHERE phone_number = ?";

    private static final String[] ALLOWED_FIELDS = {
            "full_name", "birthday", "address", "gender", "email", "phone_number", "hire_date", "account_id"
    };

    // Tạo mới nhân viên
    public static Employee create(Employee emp) {
        boolean exists = DatabaseExecutor.exists(SQL_CHECK_UNIQUE_PHONE, emp.getPhoneNumber());
        if (exists) {
            throw new RuntimeException("Số điện thoại đã tồn tại.");
        }

        long id = DatabaseExecutor.insert(SQL_CREATE,
                emp.getFullName(),
                emp.getBirthday() != null ? Date.valueOf(emp.getBirthday()) : null,
                emp.getAddress(),
                emp.getGender(),
                emp.getEmail(),
                emp.getPhoneNumber(),
                emp.getHireDate() != null ? Date.valueOf(emp.getHireDate()) : null,
                emp.getAccountId()
        );

        return id > 0 ? queryOne((int) id) : null;
    }

    // Truy vấn 1 nhân viên theo ID
    public static Employee queryOne(int id) {
        Employee emp = DatabaseExecutor.queryOne(SQL_QUERY_ONE, Employee.class, id);
        if (emp == null) {
            throw new RuntimeException("Không tìm thấy nhân viên với ID = " + id);
        }
        return emp;
    }

    // Truy vấn toàn bộ danh sách nhân viên
    public static List<Employee> queryList() {
        return DatabaseExecutor.queryList(SQL_QUERY_LIST, Employee.class);
    }

    // Cập nhật nhân viên theo id và danh sách trường cập nhật
    public static boolean update(int id, Map<String, Object> updateFields) {
        if (updateFields == null || updateFields.isEmpty()) {
            throw new IllegalArgumentException("Không có trường nào để cập nhật.");
        }

        Employee emp = queryOne(id); // kiểm tra tồn tại

        StringBuilder sql = new StringBuilder("UPDATE employees SET ");
        List<Object> params = new ArrayList<>();
        int count = 0;

        for (Map.Entry<String, Object> entry : updateFields.entrySet()) {
            String key = entry.getKey();
            boolean allowed = Arrays.stream(ALLOWED_FIELDS).anyMatch(f -> f.equalsIgnoreCase(key));
            if (!allowed) {
                throw new RuntimeException("Trường không hợp lệ: " + key);
            }

            if (count > 0) sql.append(", ");
            sql.append(key).append(" = ?");
            params.add(entry.getValue());
            count++;
        }

        sql.append(" WHERE id = ?");
        params.add(id);

        return DatabaseExecutor.update(sql.toString(), params.toArray()) > 0;
    }

    // Xóa nhân viên
    public static boolean delete(int id) {
        boolean exists = DatabaseExecutor.exists(SQL_CHECK_EXIST_BY_ID, id);
        if (!exists) {
            throw new RuntimeException("Không tìm thấy nhân viên với ID = " + id);
        }

        return DatabaseExecutor.delete(SQL_DELETE, id) > 0;
    }

    // Tìm kiếm theo tên và số điện thoại
    public static List<Employee> search(String fullName, String phone) {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE full_name LIKE ? AND phone_number LIKE ?";

        try (Connection conn = MyConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + fullName + "%");
            stmt.setString(2, "%" + phone + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setFullName(rs.getString("full_name"));
                emp.setPhoneNumber(rs.getString("phone_number"));
                emp.setEmail(rs.getString("email"));
                emp.setGender(rs.getInt("gender"));
                emp.setHireDate(rs.getDate("hire_date").toLocalDate());

                list.add(emp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean add(Employee emp) {
        String sql = "INSERT INTO employees (full_name, phone_number, email, gender, hire_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emp.getFullName());
            stmt.setString(2, emp.getPhoneNumber());
            stmt.setString(3, emp.getEmail());
            stmt.setInt(4, emp.getGender());
            stmt.setDate(5, java.sql.Date.valueOf(emp.getHireDate()));

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




    // Test phương thức
    public static void main(String[] args) {
        try {
            List<Employee> employees = queryList();
            employees.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
