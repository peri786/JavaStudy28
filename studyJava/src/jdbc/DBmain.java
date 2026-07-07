package jdbc;

import java.text.DecimalFormat;
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
			System.out.print("[1] 입력 [2] 학번 조건 출력 [3] 모두 출력 [4] 서브쿼리 출력 [5] 오라클 총점 평균 학점 출력 [6] 수정 [7] 삭제 [0] 종료 : ");

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
					// 자바 언어를 이용해서 총점, 평균, 학점
					int tot = dto.getKor() + dto.getEng() + dto.getMat();
					double ave = (double) tot / 3;
					String grade = "";
					if (ave >= 90) {
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
				for (ScoreDTO dto : list) {
					System.out.println(dto.getIdx() + ", " + dto.getName());
				}

			} else if (menu == 5) {

				List<ScoreDTO> list = dao.getOracle();
				DecimalFormat df = new DecimalFormat("###.00");
				for (ScoreDTO dto : list) {
					System.out.println(dto + ", " + "총점 : " + dto.getTot() + ", " + "평균 : " + df.format(dto.getAve())
							+ ", " + "학점 : " + dto.getGrade());
				}

			} else if (menu == 6) {
				System.out.println("수정하고 싶은 학번 : ");
				int idx = sc.nextInt();
				// 수정학번 입력 받는다 P.K
				ScoreDTO dto = dao.getCondition(idx);
				// 학번을 입력해서 DTO 객체로 가져온다
				if (dto == null) {
					// 학번에 해당하는 레코드가 없으면
					System.out.println("존재하지 않는 학번 입니다");
					continue;
				}
				System.out.println(dto);
				System.out.println("수정하시겠습니까?(Y/N) : ");
				String yesNo = sc.next();
				if ("y".equalsIgnoreCase(yesNo)) {

					System.out.println("국어 : ");
					int kor = sc.nextInt();
					System.out.println("영어 : ");
					int eng = sc.nextInt();
					System.out.println("수학 : ");
					int mat = sc.nextInt();

					dto.setKor(kor);
					dto.setEng(eng);
					dto.setMat(mat);
					dao.getUpdate(dto);
					// 수정처리

				} else {
					continue;
				}

			} else if (menu == 7) {
				System.out.println("삭제할 학번 입력 : ");
				int idx = sc.nextInt();
				ScoreDTO dto = dao.getCondition(idx);
				if (dto == null) {
					// 학번에 해당하는 레코드가 없으면
					System.out.println("존재하지 않는 학번 입니다");
					continue;
				}
				System.out.println(dto);
				System.out.println("삭제하시겠습니까?(Y/N) : ");
				String yesNo = sc.next();
				if ("y".equalsIgnoreCase(yesNo)) {
					dao.getDelete(idx);
				} else {
					continue;
				}

			} else if (menu == 0) {
				flag = false;
				System.out.println("종료");
			}
		}

	}

}
