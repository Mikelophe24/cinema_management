package Dao;

import Helper.DatabaseExecutor;
import Model.OrderDetail;

import java.util.List;

public class OrderDetailDao {

    // Thêm món ăn vào chi tiết đơn hàng
    public long insert(OrderDetail detail) {
        String sql = "INSERT INTO order_details (invoice_id, product_id, quantity) VALUES (?, ?, ?)";
        return DatabaseExecutor.insert(sql, detail.getInvoiceId(), detail.getProductId(), detail.getQuantity());
    }

    // Lấy danh sách món ăn theo invoice_id
    public List<OrderDetail> getByInvoiceId(int invoiceId) {
        String sql = "SELECT * FROM order_details WHERE invoice_id = ?";
        return DatabaseExecutor.queryList(sql, OrderDetail.class, invoiceId);
    }

    // Xoá chi tiết đơn hàng theo invoice_id
    public int deleteByInvoiceId(int invoiceId) {
        String sql = "DELETE FROM order_details WHERE invoice_id = ?";
        return DatabaseExecutor.delete(sql, invoiceId);
    }
}
