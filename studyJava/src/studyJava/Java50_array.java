package studyJava;

import java.util.Scanner;

public class Java50_array {

	public static void main(String[] args) {
		
		// 스캐너 객체 생성
		Scanner sc = new Scanner(System.in);
		// 학생 수 입력 문자열 출력
		System.out.print("학생 수 입력 : ");
		// 정수 값 1개 입력받아 n 변수에 저장
		int n = sc.nextInt();
		// 이름 저장할 수 있는 배열(name)을 n개 만큼 만드시오
		// 정수 값을 저장 할 수 있는 배열(scores)을 n개 만큼 만드시오
		String[] name = new String[n];
		int[] scores = new int[n];
		
		// 1번 이름 : aaa
		// 1번 점수 : 89
		// 위 처럼 3번을 입력 받아 배열에 저장하는 프로그램을 작성하시오
		for (int i = 0; i < n; i++) {
			System.out.print(i+1 + "번 이름 : ");
			name[i] = sc.next();
			System.out.print(i+1 + "번 점수 : ");
			scores[i] = sc.nextInt();
		}
		
		// 배열에 저장된 정수값 합계(total)를 계산하시오
		// 배열에 저장된 정수값 중에서 최대값(max), 최소값(min), 평균(avg)을 구하시오
		int total = 0; //scores[0] + scores[1] + scores[2];
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;

		// total 구하기 for문
//		for (int i = 0; i < n; i++) {
//			total += scores[i];
//		}

		// 최대값 최소값 for문
//		for (int i = 0; i < n; i++) {
//			if(max < scores[i]) max = scores[i];
//			if(min > scores[i]) min = scores[i];
//		}
		
		// 위 문장을 향상된 for문으로
		for (int s : scores) {
			total += s;
			if (max < s) max = s;
			if (min > s) min = s;
		}
		
		double avg = (double)total / n; // 평균
		
		// 출력
		System.out.println("-----------------------------------------------------");
		System.out.printf("%-6s\t점수\t등수\t학점\t합격\t전체평균대비%n", "이름");
		System.out.println("-----------------------------------------------------");
		
		// 등수와 학점을 계산하시오
		// 학점은 90이상 A, 80이상 B, 70이상 C, 60이상 D 아니면 F
		// 합격여부는 점수가 60 이상이면 합격, 아니면 불합격
		
		for (int i = 0; i < n; i++) {
			int rank = 1;
			for (int j = 0; j < n; j++) {
				// 등수계산
				if (scores[i] < scores[j]) {
					rank++;
				}
			}
			// 학점
			String grade;
			if (scores[i] >= 90) {
				grade = "A";
			} else if (scores[i] >= 80) {
				grade = "B";
			} else if (scores[i] >= 70) {
				grade = "C";
			} else if (scores[i] >= 60) {
				grade = "D";
			} else {
				grade = "F";
			}
			// 합격 여부
			String pass = (scores[i] >= 60) ? "합격" : "불합격";
			// 평균 대비
			double diff;
			diff = scores[i] - avg;
			String diffStr = String.format("%+.1f", diff);
			System.out.printf("%-6s\t%3d점\t%2d등\t%s\t%-5s%s\n", 
					name[i], scores[i], rank, grade, pass, diffStr);
		}
		System.out.println("====================================================");
		System.out.printf("전체평균 : %.1f점\t최고 : %d점\t 최저 : %d점\n", avg, max, min);
		System.out.println("====================================================");

	}

}
