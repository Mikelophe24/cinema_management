package util;

import java.util.ArrayList;
import java.util.List;

import Enum.SeatEnum;
import Model.Seat;

public class SeatGenerator {
	public static List<Seat> generateSeats(int theaterId, int coupleSeatCount, int normalSeatCount, int seatsPerRow) {
		List<Seat> seats = new ArrayList<>();

		int couplePairs = coupleSeatCount / 2;
		int couplePerRow = seatsPerRow / 2;
		int coupleRows = (int) Math.ceil((double) couplePairs / couplePerRow);

		int normalRows = (int) Math.ceil((double) normalSeatCount / seatsPerRow);

		char currentRow = 'A';
		int normalCreated = 0;
		for (int i = 0; i < normalRows; i++) {
			for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
				if (normalCreated >= normalSeatCount)
					break;

				String name = currentRow + String.valueOf(seatNum);
				seats.add(new Seat(theaterId, name, SeatEnum.Type.NORMAL));
				normalCreated++;
			}
			currentRow++;
		}

		char coupleRow = currentRow;
		int coupleCreated = 0;
		for (int i = 0; i < coupleRows; i++) {
			for (int seatNum = 1; seatNum <= seatsPerRow; seatNum += 2) {
				if (coupleCreated >= couplePairs)
					break;

//				String name = coupleRow + seatNum + "-" + coupleRow + (seatNum + 1);
				String name = coupleRow + String.valueOf(seatNum) + "-" + coupleRow + (seatNum + 1);

				seats.add(new Seat(theaterId, name, SeatEnum.Type.COUPLE));
				coupleCreated++;
			}
			coupleRow++;
		}

		return seats;
	}

//	public static void main(String[] args) {
//		int theaterId = 1;
//		int coupleSeatCount = 10;
//		int normalSeatCount = 144;
//		int seatsPerRow = 12;
//
//		List<Seat> seats = generateSeats(theaterId, coupleSeatCount, normalSeatCount, seatsPerRow);
//		System.out.println(seats);
//		// Gom theo hàng
//		Map<Character, List<String>> rowMap = new HashMap<>();
//		for (Seat seat : seats) {
//			char rowChar = seat.getName().charAt(0);
//			rowMap.computeIfAbsent(rowChar, k -> new ArrayList<>()).add(seat.getName());
//		}
//
//		// In sơ đồ
//		rowMap.keySet().stream().sorted().forEach(row -> {
//			List<String> seatNames = rowMap.get(row);
//			System.out.print(row + ": ");
//			for (String name : seatNames) {
//				System.out.print(name + "  ");
//			}
//			System.out.println();
//		});
//
//		System.out.println("\nTotal seat records (normal + couple): " + seats.size());
//	}
}
