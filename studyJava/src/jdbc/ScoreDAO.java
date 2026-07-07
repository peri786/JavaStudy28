package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
	
	// 학번 자동 추가
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
	
	// 전체 인원수
	public int getCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select count(*) as cnt from score";
		
		int cnt = 0;
		
		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				cnt = rs.getInt("cnt");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	
	// 가변 배열을 이용한 모든 레코드 출력(내림차순)
	public List<ScoreDTO> getScore() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ScoreDTO> list = new ArrayList<ScoreDTO>();
		// 검색된 결과가 여러개 일때 ScoreDTO 객체를 담을 수 있는 가변배열 생성
		
		String sql = "select * from score order by name desc";
		
		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ScoreDTO dto = new ScoreDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setName(rs.getString("name"));
				dto.setKor(rs.getInt("kor"));
				dto.setEng(rs.getInt("eng"));
				dto.setMat(rs.getInt("mat"));
				
				list.add(dto);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	// 오라클을 이용한 총점 ,평균, 학점 출력
	public List<ScoreDTO> getOracle() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ScoreDTO> list = new ArrayList<ScoreDTO>();
		// sql을 실행한 결과가 여러개 이면 무조건 DTO객체를 저장하는 가변배열을 생성한다
		
		String sql = " select idx, name, kor, eng, mat,\r\n"
				+ "   kor + eng + mat as tot,\r\n"
				+ "   (kor + eng + mat) / 3 as ave,\r\n"
				+ "   case\r\n"
				+ "     when (kor + eng + mat) / 3. >= 90 then 'A'\r\n"
				+ "     when (kor + eng + mat) / 3. >= 80 then 'B'\r\n"
				+ "     when (kor + eng + mat) / 3. >= 70 then 'C'\r\n"
				+ "     when (kor + eng + mat) / 3. >= 60 then 'D'\r\n"
				+ "     else 'F'\r\n"
				+ "   end as grade from score";
		
		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				ScoreDTO dto = new ScoreDTO();
				
				dto.setIdx(rs.getInt("idx"));
				dto.setName(rs.getString("name"));
				dto.setKor(rs.getInt("kor"));
				dto.setEng(rs.getInt("eng"));
				dto.setMat(rs.getInt("mat"));
				dto.setTot(rs.getInt("tot"));
				dto.setAve(rs.getDouble("ave"));
				dto.setGrade(rs.getString("grade"));
				
				list.add(dto);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	// 국어점수가 가장 높은 사람의 학번, 이름 출력
	public List<ScoreDTO> getMaxKor() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select idx, name from score " 
				+ "where kor=(select max(kor) from score)";
		// 여러줄로 코딩할 때는 반드시 사이 띄우기 할것!
		// subquery : select 안에 select문을 만드는 것!
		
		List<ScoreDTO> list = new ArrayList<ScoreDTO>();
		
		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ScoreDTO dto = new ScoreDTO();
				
				dto.setIdx(rs.getInt("idx"));
				dto.setName(rs.getString("name"));
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
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
	
	// 과목별 총점 계산 메서드
	public ScoreDTO setTotal() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select sum(kor) as tkor, sum(eng) as teng, sum(mat) as tmat from score";
		
		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				ScoreDTO dto = new ScoreDTO();
				
				dto.setTkor(rs.getInt("tkor"));
				dto.setTeng(rs.getInt("teng"));
				dto.setTmat(rs.getInt("tmat"));
				return dto;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// 과목별 평균 구하는 메서드
	
	public ScoreDTO setAvg() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select avg(kor) as akor, avg(eng) as aeng, avg(mat) as amat from score";
		
		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ScoreDTO dto = new ScoreDTO();
				dto.setAkor(rs.getDouble("akor"));
				dto.setAeng(rs.getDouble("aeng"));
				dto.setAmat(rs.getDouble("amat"));
				
				return dto;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	// 수정
	public void getUpdate(ScoreDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "update score set kor=?, eng=?, mat=? where idx=?";
		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getKor());
			// kor 변수에 저장된 값을 읽어와서 1번째 ?에 정수 타입으로 저장
			pstmt.setInt(2, dto.getEng());
			pstmt.setInt(3, dto.getMat());
			pstmt.setInt(4, dto.getIdx());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 삭제
	public void getDelete(int idx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "delete from score where idx=?";
		
		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
