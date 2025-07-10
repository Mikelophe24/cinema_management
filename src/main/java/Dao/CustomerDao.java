package Dao;

import Helper.DatabaseExecutor;
import Model.Customer;
import util.MyConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class CustomerDao {

    private static final String SQL_CREATE = "INSERT INTO customers (account_id, full_name, email, phone_number, address, birthday, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_QUERY_ONE = "SELECT * FROM customers WHERE id = ?";
    private static final String SQL_QUERY_LIST = "SELECT * FROM customers";
    private static final String SQL_DELETE = "DELETE FROM customers WHERE id = ?";
    private static final String SQL_CHECK_EXIST_BY_ID = "SELECT 1 FROM customers WHERE id = ?";
    private static final String SQL_CHECK_UNIQUE_PHONE = "SELECT 1 FROM customers WHERE phone_number = ?";

    private static final String[] ALLOWED_FIELDS = {
            "full_name", "email", "phone_number", "address", "birthday", "gender", "account_id"
    };

    // Tạo khách hàng mới
    public static Customer create(Customer customer) {
        boolean exists = DatabaseExecutor.exists(SQL_CHECK_UNIQUE_PHONE, customer.getPhoneNumber());
        if (exists) {
            throw new RuntimeException("Số điện thoại đã tồn tại.");
        }

        long id = DatabaseExecutor.insert(SQL_CREATE,
                customer.getAccountId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getAddress(),
                customer.getBirthday() != null ? Date.valueOf(customer.getBirthday()) : null,
                customer.getGender()
        );

        return id > 0 ? queryOne((int) id) : null;
    }

    // Truy vấn 1 khách hàng
    public static Customer queryOne(int id) {
        Customer customer = DatabaseExecutor.queryOne(SQL_QUERY_ONE, Customer.class, id);
        if (customer == null) {
            throw new RuntimeException("Không tìm thấy khách hàng với ID = " + id);
        }
        return customer;
    }

    // Truy vấn danh sách khách hàng
    public static List<Customer> queryList() {
        return DatabaseExecutor.queryList(SQL_QUERY_LIST, Customer.class);
    }


    // Cập nhật theo ID và Map fields
    public static boolean update(int id, Map<String, Object> updateFields) {
        if (updateFields == null || updateFields.isEmpty()) {
            throw new IllegalArgumentException("Không có trường nào để cập nhật.");
        }

        Customer customer = queryOne(id); // kiểm tra tồn tại

        StringBuilder sql = new StringBuilder("UPDATE customers SET ");
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

    // Xóa khách hàng
    public static boolean delete(int id) {
        boolean exists = DatabaseExecutor.exists(SQL_CHECK_EXIST_BY_ID, id);
        if (!exists) {
            throw new RuntimeException("Không tìm thấy khách hàng với ID = " + id);
        }

        return DatabaseExecutor.delete(SQL_DELETE, id) > 0;
    }

    // Tìm kiếm theo họ tên và SĐT
    public static List<Customer> search(String fullName, String phone) {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE full_name LIKE ? AND phone_number LIKE ?";

        try (Connection conn = MyConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + fullName + "%");
            stmt.setString(2, "%" + phone + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setAccountId(rs.getInt("account_id"));
                c.setFullName(rs.getString("full_name"));
                c.setEmail(rs.getString("email"));
                c.setPhoneNumber(rs.getString("phone_number"));
                c.setAddress(rs.getString("address"));
                c.setBirthday(rs.getDate("birthday") != null ? rs.getDate("birthday").toLocalDate() : null);
                c.setGender(rs.getInt("gender"));

                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Test thử
    public static void main(String[] args) {
        try {
            List<Customer> customers = queryList();
            customers.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
