package studyJava.middle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Java33 {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// 파일 입출력 (File I/O)
		// 파일 입출력이란 프로그램과 파일 사이에서 읽고 쓰는 것이다
		// 입력(Input) : 파일에서 프로그램으로 데이터 읽기
		// 출력(Output) : 프로그램에서 파일로 데이터 쓰기
		
		// 스트림 구조 한눈에 보기
		// 1. 바이트 스트림(1바이트 씩)
		//	1.1 InputStream -> 읽기 (FileInputStream)
		//	1.2 OutputStream -> 쓰기 (FileOutputStream)
		// 2. 문자스트림(문자단위, 한글 안깨짐)
		// 	2.1 Reader -> 읽기(FileReader, BufferedReader)
		// 	2.2 Writer -> 쓰기(FileWriter, BufferedWriter)
		// 버퍼 : 중간 임시 저장소 -> 한꺼번에 처리해서 속도 향상
		// BufferedReader -> 줄 단위로 빠르게 읽기
		// BufferedWriter -> 줄 단위로 빠르게 쓰기
		// 3. File -> 파일/폴더 관리
		
		System.out.println(System.getProperty("user.dir"));
		
		String filename = "hello.txt";
		System.out.println("파일쓰기");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
			bw.write("hello java");
			bw.newLine();
			bw.write("파일 입출력 학습 중");
			bw.newLine();
			bw.write("오늘도 글을 쓰는 중");
			//bw.close();
			bw.flush();
			// writer() : 버퍼에만 기록
			// flush() : 버퍼 내용을 파일에 저장(파일은 열려있음)
			// close() : 버퍼 저장 + 파일 닫기
			System.out.println("파일 쓰기 성공 : " + filename);
		} catch(IOException e) {
			System.out.println("쓰기 오류 : " + e.getMessage());
		}
		
		// 파일 읽기
		System.out.println("===파일 읽기===");
		
		BufferedReader br = new BufferedReader(new FileReader(filename));
		
		String line; // 한줄 저장 변수
		int lineNum = 1; // 줄번호
		
		while((line = br.readLine()) != null) {
			System.out.println(lineNum++ + ": " + line);
		}
		br.close();
		
		// 파일 이어서 쓰기
		System.out.println("===파일 이어서 쓰기===");
		try {
			FileWriter fw = new FileWriter(filename, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.newLine();
			bw.write("이어쓰기 추가 되었어요");
			bw.close();
		} catch (Exception e) {
			System.out.println("이어쓰기 오류 : " + e.getMessage());
		}
		
		// 파일 전체 다시 읽기
		System.out.println("===파일 전체 다시 읽기===");
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(filename));
			String line1;
			while((line1 = br1.readLine()) != null) {
				System.out.println(" " + line1);
			}
			br1.close();
		}catch(Exception e) {
			System.out.println("읽기오류 : " + e.getMessage());
		}
		
		System.out.println("===파일 정보===");
		File file = new File(filename);
		System.out.println("파일명 : " + file.getName());
		System.out.println("크기 : " + file.length());
		System.out.println("존재여부 : " + file.exists());
		System.out.println("읽기가능 : " + file.canRead());
		System.out.println("쓰기 가능 : " + file.canWrite());
		
		if(file.delete()) {
			System.out.println("파일 삭제 완료!");
		}
		System.out.println("삭제 후 존재여부 : " + file.exists());
	}

}
