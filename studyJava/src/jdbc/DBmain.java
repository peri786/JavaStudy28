package jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBmain {

	public static void main(String[] args) {

		// 메뉴
		Scanner sc = new Scanner(System.in);
		boolean flag = true;
		ScoreDAO dao = new ScoreDAO();

		while (flag) {
			System.out.print("[1] 입력 [2] 학번 조건 출력 [3] 모두 출력 [4] 서브쿼리 출력 [5] 수정 [0] 종료 : ");

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
				
				System.out.println("전체 인원수 : " + dao.getCount());
				System.out.println("=========================================");
				List<ScoreDTO> list = dao.getScore();

				for (ScoreDTO dto : list) {
					int tot = dto.getKor() + dto.getEng() + dto.getMat();
					double ave = (double)tot / 3;
					String grade = "";
					if(ave >= 90) {
						grade = "A";
					} else if (ave >= 80) {
						grade = "B";
					} else if (ave >= 70) {
						grade = "C";
					} else if (ave >= 60) {
						grade = "D";
					} else {
						grade = "F";
					}
					System.out.print(dto);
					System.out.println(", " + tot + ", " + ave + ", " + grade);
				}
				System.out.println("=========================================");
				ScoreDTO dto = null;
				dto = dao.setTotal();
				System.out.println("전체 총점 : " + dto.getTkor() + ", " + dto.getTeng() + ", " + dto.getTmat());
				System.out.println("=========================================");
				dto = dao.setAvg();
				System.out.println("전체 평균 : " + dto.getAkor() + ", " + dto.getAeng() + ", " + dto.getAmat());
				
			} else if (menu == 4) {
				
				List<ScoreDTO> list = dao.getMaxKor();
				for(ScoreDTO dto : list) {
					System.out.println(dto.getIdx() + ", " + dto.getName());
				}

			} else if (menu == 5) {
				System.out.println("수정 항목 : ");
				String upd = sc.next();
				System.out.println("수정 내용 : ");
				int updSet = sc.nextInt();
				System.out.println("수정하고 싶은 id : ");
				int idxSet = sc.nextInt();
				dao.getUpdate(upd, updSet, idxSet);

			} else if (menu == 0) {
				flag = false;
				System.out.println("종료");
			}
		}

	}

}
