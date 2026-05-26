package studyJava;

public class Java47_array {

	public static void main(String[] args) {
		// 배열(Array)이란?
		// 같은 자료형의 데이터를 여러개 저장할 수 있는 자료구조
		// 요기서 중요한 것은 같은 자료형만 저장이 가능해
		// 배열을 이용하면 한번에 많은 메모리 공간이 할당 된다
		// 인덱스를 이용하여 원소 데이터 접근을 한다
		// 배열 인덱스(index)는 0부터 시작한다
		// 배열이 초기화 되지 않을 경우에는 묵시적인 값으로 자동 설정된다
		
		// 정수값 저장하는 배열
		int intArr [];
		// 문자 저장하는 배열 선언
		char charArr [];
		// 문자열 저장하는 배열 선언문
		String strArr[];
		
		// 선언문에서 주의사항은 배열의 개수를 적으면 안된다
		// int arr[10]; -> 안됨
		
		// 배열을 할당(생성) 하고 싶으면
		
		// 정수값 저장하는 배열 5개를 만들고 싶으면
		intArr = new int[5];
		// 문자를 저장하는 배열 5개
		charArr = new char[5];
		// 문자열을 저장하는 배열 5개
		strArr = new String[5];
		
		// 위 두개의 문장을 한 문장으로 바꾸면
		// int[] intArr = new int[5];
		
		// 실수 값을 저장하는 배열 5개를 한문장으로
		double[] douArr = new double[5];
		
		System.out.println("douArr : " + douArr);
		System.out.println("douArr[0] : " + douArr[0]);
		System.out.println("douArr[4] : " + douArr[4]);
		// 선언돠지 않은 배열에 값을 출력하려고 하면
		// System.out.println("douArr[5] : " + douArr[5]);
		// ArrayIndexOutOfBoundsException 예외 발생
		
//		for(int i = 0; i < douArr.length; i++) {
//			System.out.println(douArr[i]);
//		}
		
		douArr[0] = 10.0;
		douArr[1] = 20.0;
		douArr[2] = 30; // 4바이트->8바이트에 저장, 자동 형변환이 되어서 30.0저장
		douArr[3] = 40;
		douArr[4] = 50.0;
		
		// 위 문장을 for문으로 변경
//		double a = 11.0;
//		for (int i = 0; i < douArr.length; i++) {
//			douArr[i] = a;
//			a += 10.0;
//		}
		for (int i = 0; i < 5; i++) {
			douArr[i] = (i+1) * 10.0;
		}
		
		
		// 인덱스(index)번호는 음수를 사용할 수 없다
		
		// 인덱스 번호를 이용한 출력문 작성하면
		System.out.println(douArr[0]);
		System.out.println(douArr[1]);
		System.out.println(douArr[2]);
		System.out.println(douArr[3]);
		System.out.println(douArr[4]);
		
		// 위 문장을 for문으로 바꿔보자
		for (int i = 0; i < douArr.length; i++) {
			System.out.println(douArr[i]);
		}
		
		// 배열을 선언과 동시에 원하는 값으로 초기화 하는 방법은?
		
		int array1 [] = {10, 20, 30, 40, 50}; // array1 [] 순서를 바꿔도 된다
		// array2 int [] = {1,2,3,4}; 이것은 에러. 데이터형은 앞에 와야 한다
		
		// 배열의 길이를 알고 싶으면 배열명.length, 배열의 개수는 5, 인덱스번호는 0부터 시작
		
		for (int i = 0; i < array1.length; i++) {
			System.out.println(array1[i]);
		}

	}

}
