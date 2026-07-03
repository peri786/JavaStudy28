package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ScoreDAO {
	
	// insert, update, delete : 2개 객체 사용, Connection, PreparedStatement
	// select : 3개, Connection, PreparedStatement, ResultSet
	
	// 저장
	public void setInsert(ScoreDTO dto) {
		Connection conn = null; // db 접속 정보 저장 객체
		PreparedStatement pstmt = null; // sql
		
		String sql = "insert into score (idx, name, kor, eng, mat) values (?, ?, ?, ?, ?)";
		
		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql); // sql문을 실행하기 위한 준비
			pstmt.setInt(1, dto.getIdx()); // 1번 ?표에 idx값을 가져와서 int형으로 set
			pstmt.setString(2, dto.getName());
			pstmt.setInt(3, dto.getKor());
			pstmt.setInt(4, dto.getEng());
			pstmt.setInt(5, dto.getMat());
			pstmt.executeUpdate(); // sql 실행
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("이미 존재하는 학번입니다");
		}
	}
	
	public int countIdx() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		String sql = "select max(idx)+1 as maxidx from score";
		
		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int maxidx = rs.getInt("maxidx");
				return maxidx;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return 0;
	}
	
	
	// 출력
	public void getScore() {
		
	}
	
	// 조건 검색
	public ScoreDTO getCondition(int idx) {
		Connection conn = null; // db접속
		PreparedStatement pstmt = null; // sql 처리하기 위한 객체
		ResultSet rs = null; // 검색 결과를 담는 객체
		
		String sql = "select * from score where idx=?";
		
		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			while(rs.next()) { 
				// rs.next() : 검색된 결과가 있으면 첫번째 레코드(튜플)로 이동하고
				// true를 리턴 해준다
				ScoreDTO dto = new ScoreDTO();
				// 객체형태로 데이터를 주고 받기 위해 dto 객체를 생성한다
				dto.setIdx(rs.getInt("idx"));
				// rs가 가리키는 레코드의 idx컬럼에 검색된 값을 dto idx 변수에 저장
				dto.setName(rs.getString("name"));
				dto.setKor(rs.getInt("kor"));
				dto.setEng(rs.getInt("eng"));
				dto.setMat(rs.getInt("mat"));
				return dto;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	public ScoreDTO getCondition(int idx) {
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		ScoreDTO dto = null;
//		
//		String sql = "select * from score where idx=?";
//		
//		try {
//			conn = DBmanager.getInstance();
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, idx);
//			rs = pstmt.executeQuery();
//			dto = new ScoreDTO();
//			
//			if(rs.next()) {
//				dto.setIdx(rs.getInt("idx"));
//				dto.setName(rs.getString("name"));
//				dto.setKor(rs.getInt("kor"));
//				dto.setEng(rs.getInt("eng"));
//				dto.setMat(rs.getInt("mat"));
//			}
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//		return dto;
//	}
	
	
	
	// 수정
	public void getUpdate() {
		Connection conn = null;
		PreparedStatement pstmt = null;
	}
	
	// 삭제
	public void getDelete() {
		Connection conn = null;
		PreparedStatement pstmt = null;
	}
	
	
}
