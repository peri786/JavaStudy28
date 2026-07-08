package jdbcArtist;

import java.sql.Connection;
import java.util.Scanner;

public class ArtistMain {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		ArtistDAO dao = new ArtistDAO();

		while (flag) {

			System.out.println("[1] 등록 [2] 출력 [3] id로 검색 [0] 종료");
			int menu = sc.nextInt();

			if (menu == 1) {
				ArtistDTO dto = dao.viewInsert();
				dao.insert(dto);

			} else if (menu == 2) {

				dao.viewPrint(dao.print());

			} else if (menu == 3) {

			} else if (menu == 0) {
				flag = false;
			} else {
				System.out.println("번호를 다시 입력해주세요");
			}
		}

	}

}
