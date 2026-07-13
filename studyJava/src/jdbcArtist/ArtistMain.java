package jdbcArtist;

import java.sql.Connection;
import java.util.Scanner;

public class ArtistMain {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		ArtistDAO dao = new ArtistDAO();

		while (flag) {

			System.out.println("[1] 등록 [2] 참가자 목록 출력 [3] id로 검색 [4] 삭제 [5] 멘토 점수 목록 출력 [6] 총점, 평균, 등수 [0] 종료");
			int menu = sc.nextInt();

			if (menu == 1) {

				dao.insert(dao.viewInsert());

			} else if (menu == 2) {

				dao.viewPrint(dao.print());

			} else if (menu == 3) {

				dao.viewFindById();

			} else if (menu == 4) {

				dao.viewDelete();

			} else if (menu == 5) {

				dao.viewPrintJoinArtist(dao.printJoinArtist());

			} else if (menu == 6) {
				
				dao.viewPrintGroupArtist(dao.printGroupArtist());
				
			} else if (menu == 0) {
				flag = false;
			} else {
				System.out.println("번호를 다시 입력해주세요");
			}
		}

	}

}
