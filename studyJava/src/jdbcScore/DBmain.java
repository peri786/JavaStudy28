package jdbcScore;

import java.util.Scanner;

public class DBmain {

	public static void main(String[] args) {

		// 메뉴
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		ScoreDAO dao = new ScoreDAO();

		while (flag) {
			System.out.print("[1] 입력 [2] 학번 조건 출력 [3] 모두 출력 [4] 서브쿼리 출력 [5] 오라클 총점 평균 학점 출력 [6] 수정 [7] 삭제 [0] 종료 : ");

			int menu = sc.nextInt();
			if (menu == 1) {

				dao.viewInsert();

			} else if (menu == 2) {

				dao.findByIdx();

			} else if (menu == 3) {

				dao.printAll();

			} else if (menu == 4) {

				dao.printMaxIdxName();

			} else if (menu == 5) {

				dao.printTotAve();

			} else if (menu == 6) {

				dao.updateMain();

			} else if (menu == 7) {

				dao.deleteMain();

			} else if (menu == 0) {
				flag = false;
				System.out.println("종료");
			}
		}

	}

}
