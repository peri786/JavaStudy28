package jdbcScore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScoreDAO {

	// insert, update, delete : 2개 객체 사용, Connection, PreparedStatement
	// select : 3개, Connection, PreparedStatement, ResultSet

	Scanner sc = new Scanner(System.in);

	// 화면 입력
	public void viewInsert() {
		System.out.println("학번 : ");
		int idx = countIdx();
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

		setInsert(dto);
	}

	// 저장
	public boolean setInsert(ScoreDTO dto) {
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
			int result = pstmt.executeUpdate(); // sql 실행
			if(result > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("이미 존재하는 학번입니다");
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return false;
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

			while (rs.next()) {
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

			while (rs.next()) {
				cnt = rs.getInt("cnt");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
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

			while (rs.next()) {
				ScoreDTO dto = new ScoreDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setName(rs.getString("name"));
				dto.setKor(rs.getInt("kor"));
				dto.setEng(rs.getInt("eng"));
				dto.setMat(rs.getInt("mat"));

				list.add(dto);
			}

		} catch (Exception e) {
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

		String sql = " select idx, name, kor, eng, mat,\r\n" + "   kor + eng + mat as tot,\r\n"
				+ "   (kor + eng + mat) / 3 as ave,\r\n" + "   case\r\n"
				+ "     when (kor + eng + mat) / 3. >= 90 then 'A'\r\n"
				+ "     when (kor + eng + mat) / 3. >= 80 then 'B'\r\n"
				+ "     when (kor + eng + mat) / 3. >= 70 then 'C'\r\n"
				+ "     when (kor + eng + mat) / 3. >= 60 then 'D'\r\n" + "     else 'F'\r\n"
				+ "   end as grade from score";

		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {

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

	public void printMaxIdxName() {
		List<ScoreDTO> list = getMaxKor();
		for (ScoreDTO dto : list) {
			System.out.println(dto.getIdx() + ", " + dto.getName());
		}
	}

	// 국어점수가 가장 높은 사람의 학번, 이름 출력
	public List<ScoreDTO> getMaxKor() {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select idx, name from score " + "where kor=(select max(kor) from score)";
		// 여러줄로 코딩할 때는 반드시 사이 띄우기 할것!
		// subquery : select 안에 select문을 만드는 것!

		List<ScoreDTO> list = new ArrayList<ScoreDTO>();

		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
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

	// 화면 출력
	public void findByIdx() {
		System.out.println("학번 입력 : ");
		int idx = sc.nextInt();
		ScoreDTO dto = getCondition(idx);
		System.out.print(dto.getIdx() + ", ");
		System.out.print(dto.getName() + ", ");
		System.out.print(dto.getKor() + ", ");
		System.out.print(dto.getEng() + ", ");
		System.out.print(dto.getMat() + "\n");
		int tot = dto.getKor() + dto.getEng() + dto.getMat();
		double ave = (double) tot / 3;
		System.out.println("총점 : " + tot);
		System.out.println("평균 : " + ave);
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
			while (rs.next()) {
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

		} catch (Exception e) {
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

	public void printAll() {
		System.out.println("전체 인원수 : " + getCount());
		System.out.println("=========================================");
		List<ScoreDTO> list = getScore();

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
		dto = setTotal();
		System.out.println("전체 총점 : " + dto.getTkor() + ", " + dto.getTeng() + ", " + dto.getTmat());
		System.out.println("=========================================");
		dto = setAvg();
		System.out.println("전체 평균 : " + dto.getAkor() + ", " + dto.getAeng() + ", " + dto.getAmat());
	}

	public void printTotAve() {
		List<ScoreDTO> list = getOracle();
		DecimalFormat df = new DecimalFormat("###.00");
		for (ScoreDTO dto : list) {
			System.out.println(dto + ", " + "총점 : " + dto.getTot() + ", " + "평균 : " + df.format(dto.getAve()) + ", "
					+ "학점 : " + dto.getGrade());
		}
	}

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

			while (rs.next()) {

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

			while (rs.next()) {
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

	public void updateMain() {
		System.out.println("수정하고 싶은 학번 : ");
		int idx = sc.nextInt();
		// 수정학번 입력 받는다 P.K
		ScoreDTO dto = getCondition(idx);
		// 학번을 입력해서 DTO 객체로 가져온다
		if (dto == null) {
			// 학번에 해당하는 레코드가 없으면
			System.out.println("존재하지 않는 학번 입니다");
			return; // 아래쪽 실행 안하고 main으로 돌아가라
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
			getUpdate(dto);
			// 수정처리

		} else {
			return;
		}
	}

	// 수정
	public boolean getUpdate(ScoreDTO dto) {
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
			int row = pstmt.executeUpdate();
			if(row > 0) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public void deleteMain() {
		System.out.println("삭제할 학번 입력 : ");
		int idx = sc.nextInt();
		ScoreDTO dto = getCondition(idx);
		if (dto == null) {
			// 학번에 해당하는 레코드가 없으면
			System.out.println("존재하지 않는 학번 입니다");
			return;
		}
		System.out.println(dto);
		System.out.println("삭제하시겠습니까?(Y/N) : ");
		String yesNo = sc.next();
		if ("y".equalsIgnoreCase(yesNo)) {
			getDelete(idx);
		} else {
			return;
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
