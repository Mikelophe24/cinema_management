//package Dao;
//import Dao.*;
//import Helper.DatabaseExecutor;
//import Model.OrderDetail;
//import Model.Ticket;
//import Model.TicketDetail;
//
//
//import java.math.BigDecimal;
//import java.sql.Date;
//import java.util.Arrays;
//import java.util.List;
//
//public class BookingService {
//
//    private final TicketDao ticketDao = new TicketDao();
//    private final TicketDetailDao ticketDetailDao = new TicketDetailDao();
//    private final OrderDetailDao orderDetailDao = new OrderDetailDao();
//    private final SeatScheduleDao seatScheduleDao = new SeatScheduleDao();
//    private final ProductDao productDao = new ProductDao(); // cần thêm class này nếu chưa có
//
//    public long createBooking(int accountId, int scheduleId, List<Integer> seatIds, List<OrderDetail> products) {
//        try {
//            int seatCount = seatIds.size();
//
//            // Lấy giá vé từ lịch chiếu
//            int ticketPrice = ticketDao.getTicketPrice(scheduleId);
//            BigDecimal ticketTotal = BigDecimal.valueOf(ticketPrice).multiply(BigDecimal.valueOf(seatCount));
//
//            // Tính tổng giá sản phẩm
//            BigDecimal productTotal = BigDecimal.ZERO;
//            for (OrderDetail od : products) {
//                BigDecimal price = productDao.getProductPriceById(od.getProductId());
//
//                if (price == null) {
//                    throw new RuntimeException("Không tìm thấy giá sản phẩm cho ID: " + od.getProductId());
//                }
//
//                od.setPrice(price);
//                productTotal = productTotal.add(price.multiply(BigDecimal.valueOf(od.getQuantity())));
//            }
//
//
//            // Tổng tiền
//            BigDecimal totalAmount = ticketTotal.add(productTotal);
//
//            // Tạo ticket
//            Ticket ticket = new Ticket(accountId, scheduleId, seatCount, totalAmount, new Date(System.currentTimeMillis()), "sold");
//            long ticketId = ticketDao.insertTicket(ticket);
//            if (ticketId <= 0) throw new RuntimeException("Insert ticket failed");
//
//            String insertInvoiceSQL = "INSERT INTO invoices (invoice_id, account_id, schedule_id, total_amount, booking_date) VALUES (?, ?, ?, ?, ?)";
//            DatabaseExecutor.insert(
//                    insertInvoiceSQL,
//                    ticketId,
//                    accountId,
//                    scheduleId,
//                    totalAmount,
//                    new Date(System.currentTimeMillis())
//            );
//            // Lưu seat -> ticket_detail
//            for (Integer seatId : seatIds) {
//                ticketDetailDao.insert(new TicketDetail((int) ticketId, seatId));
//                seatScheduleDao.bookSeat(seatId, scheduleId, (int) ticketId);
//            }
//
//            // Lưu order details (sản phẩm đi kèm)
//            for (OrderDetail od : products) {
//                od.setInvoiceId((int) ticketId); // dùng ticketId làm invoiceId
//                orderDetailDao.insert(od);
//            }
//
//            return ticketId;
//
//        } catch (Exception e) {
//            System.err.println("Booking failed: " + e.getMessage());
//            e.printStackTrace();
//            return -1;
//        }
//    }
//
//    public static void main(String[] args) {
//        BookingService service = new BookingService();
//
//        // Test đặt vé cho userId = 2, scheduleId = 1, chọn 2 ghế
//        List<Integer> seatIds = Arrays.asList(1, 2);
//
//        // Thêm 1 món ăn có productId = 1, số lượng = 2
//        OrderDetail snack = new OrderDetail(0, 7, 2, null);
//        List<OrderDetail> products = Arrays.asList(snack);
//
//        long ticketId = service.createBooking(2, 1, seatIds, products);
//        System.out.println("Ticket booked successfully with ID: " + ticketId);
//    }
//}
