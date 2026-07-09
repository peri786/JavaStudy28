package jdbcArtist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ArtistDAO {

	// 입력화면 만들기
	// 참가번호 :
	// 참가자명 :
	// 생년월일 :
	// 성별 [남자 : M, 여자 : F] :
	// 특기 [1. 댄스, 2. 랩, 3. 노래] :
	// 소속사

	Scanner sc = new Scanner(System.in);

	// 저장
	public ArtistDTO viewInsert() {

		ArtistDTO dto = new ArtistDTO();

		System.out.print("참가번호 : ");
		String artist_id = sc.next();

		System.out.print("참가자명 : ");
		String artist_name = sc.next();

		System.out.print("생년월일 : ");
		String artist_birth = sc.next();

		System.out.print("성별 [남자 : M, 여자 : F] : ");
		String artist_gender = sc.next();

		System.out.print("특기 [1. 댄스, 2. 랩, 3. 노래] : ");
		String talent = sc.next();

		System.out.print("소속사 : ");
		String agency = sc.next();

		dto.setArtist_id(artist_id);
		dto.setArtist_name(artist_name);
		dto.setArtist_birth(artist_birth);
		dto.setArtist_gender(artist_gender);
		dto.setTalent(talent);
		dto.setAgency(agency);

		return dto;

	}

	public void insert(ArtistDTO dto) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "insert into tbl_artist (artist_id, artist_name, artist_birth, artist_gender, talent, agency) values (?, ?, ?, ?, ?, ?)";

		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getArtist_id());
			pstmt.setString(2, dto.getArtist_name());
			pstmt.setString(3, dto.getArtist_birth());
			pstmt.setString(4, dto.getArtist_gender());
			pstmt.setString(5, dto.getTalent());
			pstmt.setString(6, dto.getAgency());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBmanager.close(pstmt, conn);
		}

	}

	// 전체 출력
	public void viewPrint(List<ArtistDTO> list) {

		System.out.printf("%-5s %-5s %10s %10s %7s %10s", "참가번호", "참가자명", "생년월일", "성별", "특기", "소속사");
		System.out.println();

		for (ArtistDTO dto : list) {
			System.out.println(dto);
		}

	}

	public List<ArtistDTO> print() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Map<String, String> map = Map.of("1", "댄스", "2", "랩", "3", "노래");
		List<ArtistDTO> list = new ArrayList<ArtistDTO>();

		String sql = "select * from tbl_artist order by artist_id";

		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ArtistDTO dto = new ArtistDTO();

				dto.setArtist_id(rs.getString("artist_id"));
				dto.setArtist_name(rs.getString("artist_name"));
				dto.setArtist_birth(rs.getString("artist_birth"));

				if ("M".equals(rs.getString("artist_gender"))) {
					dto.setArtist_gender("남성");
				} else if ("F".equals(rs.getString("artist_gender"))) {
					dto.setArtist_gender("여성");
				}

				dto.setTalent(map.get(rs.getString("talent")));
				dto.setAgency(rs.getString("agency"));

				list.add(dto);
			}

		} catch (Exception e) {

		} finally {
			DBmanager.close(rs, pstmt, conn);
		}

		return list;
	}

	// id로 검색
	public void viewFindById() {
		System.out.print("검색 번호 입력 : ");
		ArtistDTO dto = findById(sc.next());

		if (dto == null) {
			System.out.println("없는 번호 입니다");
			return;
		}

		System.out.println(dto);
	}

	public ArtistDTO findById(String artist_id) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArtistDTO dto = new ArtistDTO();

		String sql = """
				select * from tbl_artist
				where artist_id=?
				""";
		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, artist_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dto.setArtist_id(rs.getString("artist_id"));
				dto.setArtist_name(rs.getString("artist_name"));
				dto.setArtist_birth(rs.getString("artist_birth"));
				dto.setArtist_gender(rs.getString("artist_gender"));
				dto.setTalent(rs.getString("talent"));
				dto.setAgency(rs.getString("agency"));

				return dto;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBmanager.close(rs, pstmt, conn);
		}

		return null;
	}

	// 삭제
	public void viewDelete() {
		System.out.print("삭제할 번호 입력 : ");
		String artist_id = sc.next();

		ArtistDTO dto = findById(artist_id);

		if (dto == null) {
			System.out.println("없는 번호 입니다");
			return;
		}

		System.out.println(dto);

		System.out.print("삭제하시겠습니까?[Y/N] : ");
		String deleteJudge = sc.next();

		if ("y".equalsIgnoreCase(deleteJudge)) {
			delete(artist_id);
		} else if ("n".equalsIgnoreCase(deleteJudge)) {
			return;
		} else {
			System.out.println("[Y/N] 만 선택 가능합니다");
		}
	}

	public void delete(String artist_id) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "delete from tbl_artist where artist_id=?";

		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, artist_id);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBmanager.close(pstmt, conn);
		}

	}

	// 조인 조건절
	public void viewPrintJoinArtist(List<ArtistDTO> list) {
		
		for(ArtistDTO dto : list) {
			System.out.printf("%d\t%s\t%s\t%s\t%d\t%s\t%s", 
					dto.getSerial_no(), dto.getArtist_id(), dto.getArtist_name(), 
					dto.getArtist_birth(), dto.getPoint(), dto.getGrade(), dto.getMento_name());
			System.out.println();
		}
		
	}
	
	public List<ArtistDTO> printJoinArtist() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ArtistDTO> list = new ArrayList<ArtistDTO>();

		String sql = """
								select c.serial_no, a.artist_id, a.artist_name, a.artist_birth, c.point,
				case
				    when c.point >= 90 then 'A'
				    when c.point >= 80 then 'B'
				    when c.point >= 70 then 'C'
				    else 'F'
				end as grade,
				b.mento_name
				from tbl_artist a, tbl_mento b, tbl_point c
				where a.artist_id = c.artist_id and b.mento_id = c.mento_id
				order by c.serial_no
								""";
		
		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ArtistDTO dto = new ArtistDTO();
				dto.setSerial_no(rs.getInt("serial_no"));
				dto.setArtist_id(rs.getString("artist_id"));
				dto.setArtist_name(rs.getString("artist_name"));
				dto.setArtist_birth(rs.getString("artist_birth"));
				dto.setPoint(rs.getInt("point"));
				dto.setGrade(rs.getString("grade"));
				dto.setMento_name(rs.getString("mento_name"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBmanager.close(rs, pstmt, conn);
		}
		
		return list;
	}

}
