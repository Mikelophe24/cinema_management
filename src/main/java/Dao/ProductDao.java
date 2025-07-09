package Dao;

import Helper.DatabaseExecutor;
import util.MyConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductDao {

    public BigDecimal getProductPriceById(int productId) {
        String sql = "SELECT price FROM products WHERE id = ?";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("price");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
