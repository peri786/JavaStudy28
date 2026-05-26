package studyJava;

import java.util.Arrays;
//java.util 패키지 안에 있는 모든 클래스는 객체생성 없이 사용이 가능한 클래스이다

public class Java48_array {

	public static void main(String[] args) {
		// 정수형 배열에 n개의 데이터를 저장하시오
		int[] score = {80, 56, 98, 77, 92};
		
		// for문을 사용 안하고 배열의 값을 간단히 문자로 변환하여 출력하시오
		System.out.println("Array.toString() : " + Arrays.toString(score));
		
		// 확장형(향상된) for문 : 앞으로 이것 겁나게 많이 사용한다. 시험볼때, 프로젝트 할 때...
		// for문으로 출력문을 작성하면 초기값, 조건식, 증가치를 따져야 되지? 불편하지?
		// 이럴 때 확장 for문을 쓰면 편리하다
		
		for (int a : score) {
			// 자동으로 score[0]번째 값을 가져와서 a변수에 저장한다. 그 다음 자동으로 반복한다
			// a변수명은 내 마음대로 단, 데이터타입은 반드시 배열 데이터 타입 형과 같아야 한다
			System.out.println("확장형 for : " + a);
		}
		
		// 배열에 저장된 합계를 구해보자
		
		int sum = 0;
		for(int a : score) {
			sum += a;
		}
		System.out.println("배열의 합계 : " + sum);
		System.out.println("배열의 평균 : " + (double)sum/score.length);
		
		//이해가 됐으면 이 배열에서 가장 큰 값, 가장 작은 값 구하기
		
		int max = score[0]; // Integer.MIN_VALUE;
		int min = score[0]; // Integer.MAX_VALUE;
		
		for(int a : score) {
			if(a > max) max = a;
			if (a < min) min = a;
		}
		
		System.out.println("max : " + max);
		System.out.println("min : " + min);
		
		// 위 배열에 저장된 값 중에서 평균에 가장 가까운 점수를 찾으시오
		
		double avg = (double)sum/score.length;
		int closeSet = score[0];  // 가장 가까운 값을 저장하는 변수
		double minDiff = Math.abs(score[0] - avg); // 첫번째 거리 값 저장하기 위한 변수
		for (int c : score) {
			double diff = Math.abs(c - avg); // Math.abs : 절대값 구하는 메서드
			// 지금까지 차이보다 더 작으면 그 값을 저장
			if (diff < minDiff) {
				minDiff = diff;  // 가장 작은 거리값 저장
				closeSet = c;  // 배열 값 저장
			}
		}
		
		System.out.println("평균 : " + avg);
		System.out.println("평균에 가장 가까운 값 : " + closeSet);
	}

}
