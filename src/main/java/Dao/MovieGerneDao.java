//package Dao;
//
//import Enum.AccountEnum;
//import Helper.DatabaseExecutor;
//import Model.Customer;
//import Model.Employee;
//import Model.MovieGerne;
//
//public class MovieGerneDao {
//	// Common SQL
//
//	// Constants
//	private static final String UPDATED_FIELDS[] = { "name", "description" };
//
//	public static MovieGerne create(String name, String description) {
//
//		boolean isExistAccount = DatabaseExecutor.exists(AccountDao.SQL_CHECK_EXIST_BY_ID, accountId);
//		if (!isExistAccount) {
//			throw new RuntimeException("Account not exist");
//		}
//
//		String SQL_CHECK_EXIST_BY_ACCOUNT_ID = "SELECT 1 FROM " + this.table + " WHERE account_id = ?";
//		boolean isExistUser = DatabaseExecutor.exists(SQL_CHECK_EXIST_BY_ACCOUNT_ID, accountId);
//		if (isExistUser) {
//			throw new RuntimeException(this.role.getValue().toUpperCase() + " has exist");
//		}
//
//		boolean isEmployee = this.role == AccountEnum.Role.EMPLOYEE;
//
//		String sqlCreate = "INSERT INTO " + this.table
//				+ " (account_id, full_name, email, phone_number, address, birthday, gender";
//		String sqlQueryOne = "SELECT * FROM " + this.table + " WHERE account_id = ?";
//
//		if (isEmployee) {
//			sqlCreate += ", hire_date)";
//		} else {
//			sqlCreate += ")";
//		}
//
//		sqlCreate += " VALUES (?, ?, ?, ?, ?, ?, ?";
//		if (isEmployee) {
//			sqlCreate += ", ?)";
//		} else {
//			sqlCreate += ")";
//		}
//
//		long rows;
//		if (isEmployee) {
//			rows = DatabaseExecutor.insert(sqlCreate, accountId, fullName, email, phoneNumber, address, birthday,
//					gender, hireDate);
//		} else {
//			rows = DatabaseExecutor.insert(sqlCreate, accountId, fullName, email, phoneNumber, address, birthday,
//					gender);
//		}
//
//		if (rows > 0) {
//			return DatabaseExecutor.queryOne(sqlQueryOne, clazz, accountId);
//		}
//
//		return null;
//	}
//
////	public List<T> getAll() {
////		String SQL_GET_ALL = "SELECT * FROM " + this.table;
////		List<T> list = new ArrayList<>();
////		list = DatabaseExecutor.queryList(SQL_GET_ALL, clazz);
////		return list;
////	}
////
////	public boolean update(int id, Map<String, Object> updateFields) {
////		if (updateFields == null || updateFields.isEmpty()) {
////			throw new IllegalArgumentException("No fields to update");
////		}
////
////		String SQL_CHECK_EXIST_BY_ID = "SELECT 1 FROM " + this.table + " WHERE id = ?";
////		boolean isExist = DatabaseExecutor.exists(SQL_CHECK_EXIST_BY_ID, id);
////		if (!isExist) {
////			throw new RuntimeException(this.role.getValue().toUpperCase() + " not found");
////		}
////
////		StringBuilder sql = new StringBuilder("UPDATE " + this.table + " SET ");
////		List<Object> params = new ArrayList<>();
////		int count = 0;
////
////		for (Map.Entry<String, Object> entry : updateFields.entrySet()) {
////			String key = entry.getKey();
////
////			boolean isAllowed = Arrays.stream(UPDATED_FIELDS).anyMatch(field -> field.equalsIgnoreCase(key));
////			if (!isAllowed) {
////				throw new RuntimeException("Field " + key + " not allowed");
////			}
////
////			if ("hire_date".equalsIgnoreCase(key) && this.role != AccountEnum.Role.EMPLOYEE) {
////				throw new RuntimeException("Field hire_date not allowed for " + this.role.getValue());
////			}
////
////			if (count > 0) {
////				sql.append(", ");
////			}
////			sql.append(key).append(" = ?");
////			params.add(entry.getValue());
////			count++;
////		}
////
////		if (count == 0) {
////			return false;
////		}
////
////		sql.append(" WHERE id = ?");
////		params.add(id);
////
////		return DatabaseExecutor.update(sql.toString(), params.toArray()) > 0;
////	}
////
////	public boolean delete(int id) {
////		if (id < 0) {
////			return false;
////		}
////		String SQL_CHECK_EXIST_BY_ID = "SELECT 1 FROM " + this.table + " WHERE id = ?";
////		boolean isExist = DatabaseExecutor.exists(SQL_CHECK_EXIST_BY_ID, id);
////		if (!isExist) {
////			throw new RuntimeException(this.role.getValue().toUpperCase() + " not found");
////		}
////		String SQL_DELETE_BY_ACCOUNT_ID = "DELETE FROM " + this.table + " WHERE id = ?";
////		return DatabaseExecutor.delete(SQL_DELETE_BY_ACCOUNT_ID, id) > 0;
////	}
//
//	public static void main(String[] args) {
//		try {
//			UserDao<Customer> customerDao = new UserDao<>(AccountEnum.Role.CUSTOMER);
//			UserDao<Employee> employeeDao = new UserDao<>(AccountEnum.Role.EMPLOYEE);
//			// Create Customer
////			Customer customer = customerDao.create(3, "Nguyễn Văn A", "a@example.com", "0123456789", "123 Đường ABC",
////					LocalDate.of(2000, 1, 1), 1, null);
////			System.out.println("Created Customer: " + customer);
//
//			// Create Employee
////			Employee employee = employeeDao.create(2, "Trần Văn B", "b@example.com", "0987654321", "456 Đường XYZ",
////					LocalDate.of(1995, 5, 5), 1, LocalDate.now());
////			System.out.println("Created Employee: " + employee);
////
//			// Get All Customers
////			List<Customer> customers = customerDao.getAll();
////			System.out.println("All Customers: " + customers);
////
////			// Get All Employees
////			List<Employee> employees = employeeDao.getAll();
////			System.out.println("All Employees: " + employees);
//
//			// Update
////			Map<String, Object> updateFields = new HashMap<>();
////			updateFields.put("full_name", "Updated");
////			updateFields.put("email", "updated@example.com");
////			updateFields.put("phone_number", "00000000000");
////
////			boolean updated = customerDao.update(1, updateFields);
////			if (updated) {
////				System.out.println("Update Customer OK");
////			} else {
////				System.out.println("Update Customer failed");
////			}
//
//			// Delete
////			boolean deleted = employeeDao.delete(9);
////			if (deleted) {
////				System.out.println("Delete Customer OK");
////			} else {
////				System.out.println("Delete Customer failed");
////			}
//
//		} catch (Exception e) {
//			System.err.println("UserDao Error: " + e.getMessage());
//			e.printStackTrace();
//		}
//	}
//}