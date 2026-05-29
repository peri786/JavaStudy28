package studyJava;

import java.util.Arrays;
import java.util.Scanner;

public class Java56_array2 {

	public static void main(String[] args) {
//		 좌석현황 (O=빈자리, X=예매)
//		   1열 2열 3열 4열 5열
//		A행 O   O   O   O   O
//		B행 O   O   O   O   O
//		C행 O   O   O   O   O
//		D행 O   O   O   O   O
//		E행 O   O   O   O   O
		// 행 입력(A-E) : B
		// 열 입력(1-5) : 3
		// B3예약 완료!

		// 좌석현황 (O=빈자리, X=예매)
		// 1열 2열 3열 4열 5열
		// A행 O O O O O
		// B행 O O X O O

		Scanner sc = new Scanner(System.in);
		boolean[][] seats = new boolean[5][5]; // false 빈자리
		String[] rows = { "A", "B", "C", "D", "E" };

		// 예매는 3회 실행시킨다
//		
//		for (int k = 0; k < 3; k++) {
//			System.out.println("좌석현황 (O=빈자리, X=예매)");
//			System.out.print("    ");
////			System.out.println("1열  2열  3열  4열  5열");
//			for (int i = 1; i <= 5; i++) {
//				System.out.print(i + "열 ");
//			}
//			System.out.println();
//			for (int i = 0; i < 5; i++) { // i = 0,1,2,3,4
//				System.out.print(rows[i] + "행");
//				for (int j = 0; j < 5; j++) { // j = 0,1,2,3,4
////					if(seats[i][j] == false) {
////						System.out.print("O");
////					} else {
////						System.out.print("X");
////					}
//					System.out.print(seats[i][j] ? "  X " :"  O ");
//				}
//				System.out.println();
//			} //for
//			System.out.println();
//			System.out.print("행 입력(A-E) : ");
//			String row = sc.next().toUpperCase(); // 문자열을 입력받아서 대문자로 변경해줘
//			System.out.print("열 입력(1-5) : ");
//			int col = sc.nextInt() - 1; // 숫자 3을 입력하면 배열은 2열을 의미하기 때문에 -1 한다
//			
//			int rowIndex = row.charAt(0) - 'A';  // A값은 ASCII 코드 값으로 65 
		// charAt(0) 행 입력한 첫번째 문자를 가져와라
		// row.charAt(0) => B니깐 66 - 65 = 1
		// 배열의 인덱스 번호를 구하여 아래 if문에 적용하기 위해
//			
//			if (rowIndex < 0 || rowIndex > 4 || col < 0 || col > 4) {
//				System.out.println("없는 좌석번호 입니다");
//			} else if (seats[rowIndex][col] == true) {
//				System.out.println("이미 예약된 좌석입니다");
//			} else {
//				seats[rowIndex][col] = true;
//				System.out.println(rows[rowIndex] + (col + 1) + "예약이 완료 되었습니다");
//			}
//		}

		String[][] seatPrint = new String[5][5];
		String rowInput = " ";
		int colInput = 0;
		int reserveCnt = 3;

		// seatPrint 일괄초기화
		for (int i = 0; i < seatPrint.length; i++) {
			for (int j = 0; j < seatPrint[i].length; j++) {
				seatPrint[i][j] = "O";
			}
		}

		// 좌석 출력
		System.out.println("\t좌석현황 (O=빈자리, X=예매)");
		System.out.println("\t1열\t2열\t3열\t4열\t5열");
		for (int i = 0; i < seatPrint.length; i++) {
			System.out.print(rows[i] + "행\t");
			for (int j = 0; j < seatPrint[i].length; j++) {
				System.out.print(seatPrint[i][j] + "\t");
			}
			System.out.println();
		}

		// 예매 3회
		while (true) {
			if (reserveCnt == 0)
				break; // 예약 횟수가 0이 되면 종료

			int rowInt = 0; // A-E행 정수로 변환해서 저장하는 변수

			System.out.print("행 입력(A-E) : ");
			rowInput = sc.next();
			System.out.print("열 입력(1-5) : ");
			colInput = sc.nextInt();

			rowInt = (int) (rowInput.charAt(0)) - 65; // 각각 65를 빼서 0-4의 정수로 변환
			
			if (rowInt < 0 || rowInt > 4 || colInput < 1 || colInput > 5) {
				System.out.println("범위를 다시 지정해주세요.");
				System.out.println();
				continue;
			}

			if (seats[rowInt][colInput - 1] == true) {
				System.out.println("이미 예약된 자리입니다.");
				System.out.println();
				continue;
			}

			seats[rowInt][colInput - 1] = true; // 입력 받은 자리 true. 1-5열이므로 colInput - 1

			for (int i = 0; i < seats.length; i++) {
				for (int j = 0; j < seats[i].length; j++) {
					if (seats[i][j] == true) {
						seatPrint[i][j] = "X";
					}
				}
			}

			System.out.println(rowInput + colInput + "예약 완료!");
			System.out.println();
			
			// 좌석 출력
			System.out.println("\t좌석현황 (O=빈자리, X=예매)");
			System.out.println("\t1열\t2열\t3열\t4열\t5열");
			for (int i = 0; i < seatPrint.length; i++) {
				System.out.print(rows[i] + "행\t");
				for (int j = 0; j < seatPrint[i].length; j++) {
					System.out.print(seatPrint[i][j] + "\t");
				}
				System.out.println();
			}
			
			reserveCnt--;
		}

	}

}
