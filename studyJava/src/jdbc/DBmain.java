package jdbc;

import java.util.Scanner;

public class DBmain {

	public static void main(String[] args) {

		// 메뉴
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		ScoreDAO dao = new ScoreDAO();

		while (flag) {
			System.out.print("[1] 입력 [2] 학번 조건 출력 [0] 종료 : ");

			int menu = sc.nextInt();
			if (menu == 1) {
				System.out.println("학번 : ");
				int idx = dao.countIdx();
				System.out.println(idx);
				System.out.println("이름 : ");
				String name = sc.next();
				System.out.println("국어 : ");
				int kor = sc.nextInt();
				System.out.println("영어 : ");
				int eng = sc.nextInt();
				System.out.println("수학 : ");
				int mat = sc.nextInt();

				ScoreDTO dto = new ScoreDTO();
				dto.setIdx(idx);
				dto.setName(name);
				dto.setKor(kor);
				dto.setEng(eng);
				dto.setMat(mat);

				dao.setInsert(dto);

			} else if (menu == 2) {

				System.out.println("학번 입력 : ");
				int idx = sc.nextInt();
				ScoreDTO dto = dao.getCondition(idx);
				System.out.print(dto.getIdx() + ", ");
				System.out.print(dto.getName() + ", ");
				System.out.print(dto.getKor() + ", ");
				System.out.print(dto.getEng() + ", ");
				System.out.print(dto.getMat() + "\n");
				int tot = dto.getKor() + dto.getEng() + dto.getMat();
				double ave = (double) tot / 3;
				System.out.println("총점 : " + tot);
				System.out.println("평균 : " + ave);

			} else if (menu == 3) {
				
				dao.getScore();
				
			} else if (menu == 0) {
				flag = false;
				System.out.println("종료");
			}
		}

	}

}
