package jdbcArtist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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

		for (ArtistDTO dto : list) {
			System.out.println(dto);
		}

	}

	public List<ArtistDTO> print() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

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
				
				dto.setTalent(rs.getString("talent"));
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
	public void viewFindById(ArtistDTO dto) {
		
		
	}
	
	public ArtistDTO findById(int artist_id) {
		
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
			pstmt.setInt(1, artist_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
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
		}
	
		return null;
	}
	

}
