# Hệ thống quản lý rạp chiếu phim

## Mô tả cơ sở dữ liệu

1. **account**: Quản lý tài khoản người dùng (khách hàng, nhân viên, quản trị viên) với thông tin đăng nhập, trạng thái, và ảnh đại diện.
2. **customer**: Lưu thông tin cá nhân của khách hàng như tên, email, số điện thoại.
3. **employee**: Quản lý thông tin nhân viên như ngày sinh, địa chỉ, ngày vào làm.
4. **theater**: Lưu thông tin phòng chiếu như tên, sức chứa, trạng thái, và mô tả.
5. **seat**: Quản lý ghế trong mỗi phòng chiếu.
6. **movie**: Lưu thông tin phim như tên, đạo diễn, năm sản xuất, đánh giá.
7. **genre**: Quản lý các thể loại phim (hành động, tình cảm, kinh dị, v.v.).
8. **movie_genre**: Liên kết phim với các thể loại (quan hệ nhiều-nhiều).
9. **movie_schedule**: Quản lý lịch chiếu với ngày, giờ, giá vé, và số vé tối đa.
10. **ticket**: Lưu thông tin vé xem phim, bao gồm khách hàng, lịch chiếu, số ghế, và tổng tiền.
11. **ticket_detail**: Ghi lại chi tiết ghế được đặt trong mỗi vé.
12. **seat_schedule**: Theo dõi trạng thái ghế (trống/đã đặt) trong mỗi lịch chiếu.
13. **invoice**: Quản lý hóa đơn thanh toán cho vé hoặc sản phẩm.
14. **supplier**: Lưu thông tin nhà cung cấp (đồ ăn, thức uống).
15. **product**: Quản lý sản phẩm bán tại rạp như bỏng ngô, nước ngọt.
16. **order_detail**: Ghi lại chi tiết sản phẩm được mua trong mỗi hóa đơn.
17. **feedback**: Lưu phản hồi, ý kiến của khách hàng về dịch vụ.
18. **customer_vote**: Quản lý điểm đánh giá của khách hàng cho từng phim.
19. **bank_account**: Lưu thông tin tài khoản ngân hàng để hỗ trợ thanh toán trực tuyến.

## Chức năng chính của hệ thống
Dựa trên cơ sở dữ liệu, bạn có thể xây dựng phần mềm với các chức năng sau:
1. **Quản lý người dùng**:
   - Đăng ký, đăng nhập, phân quyền (khách hàng, nhân viên, quản trị viên).
   - Quản lý thông tin khách hàng và nhân viên.
2. **Quản lý phim và lịch chiếu**:
   - Thêm, sửa, xóa phim và lịch chiếu.
   - Phân loại phim theo thể loại và hiển thị thông tin chi tiết.
3. **Đặt vé và quản lý ghế**:
   - Cho phép khách hàng đặt vé, chọn ghế, và kiểm tra trạng thái ghế.
   - Hủy vé và giải phóng ghế.
4. **Thanh toán và hóa đơn**:
   - Tạo hóa đơn cho vé và sản phẩm.
   - Hỗ trợ thanh toán trực tuyến qua tài khoản ngân hàng.
<!-- 5. **Quản lý sản phẩm**:
   - Bán đồ ăn, thức uống tại rạp.
   - Quản lý nhà cung cấp và danh mục sản phẩm. -->
6. **Phản hồi và đánh giá**:
   - Thu thập phản hồi khách hàng và điểm đánh giá phim.
   - Phân tích ý kiến để cải thiện dịch vụ.
7. **Báo cáo và thống kê**:
   - Thống kê doanh thu vé và sản phẩm.
   - Phân tích tỷ lệ lấp đầy ghế và đánh giá phim.
8. **Quản trị hệ thống**:
   - Quản lý phòng chiếu, nhân viên, và dữ liệu.
   <!-- - Sao lưu và khôi phục cơ sở dữ liệu. -->


# Phân công nhiệm vụ
1. Vũ: view admin
2. Dũng: view user
3. Minh: logic User
4. Trận: support V & D
5. Khôi: logic Admin


## Role
- **Quản trị viên**:
  - Thêm phim, lịch chiếu, và sản phẩm qua các bảng `movie`, `movie_schedule`, `product`.
  - Quản lý nhân viên và tài khoản qua `employee` và `account`.
- **Khách hàng**:
  - Đăng ký tài khoản (`account`, `customer`) và đặt vé qua `ticket`, `ticket_detail`.
  - Mua sản phẩm và thanh toán qua `invoice`, `order_detail`.
  - Gửi phản hồi và đánh giá phim qua `feedback`, `customer_vote`.
- **Nhân viên**:
  - Kiểm tra vé, xử lý hóa đơn, và hỗ trợ khách hàng.

