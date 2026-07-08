package jdbcArtist;

public class ArtistDTO {

	private String artist_id;
	private String artist_name;
	private String artist_birth;
	private String artist_gender;
	private String talent;
	private String agency;
	
	private String year;
	private String month;
	private String date;

	public String getArtist_id() {
		return artist_id;
	}

	public void setArtist_id(String artist_id) {
		this.artist_id = artist_id;
	}

	public String getArtist_name() {
		return artist_name;
	}

	public void setArtist_name(String artist_name) {
		this.artist_name = artist_name;
	}

	public String getArtist_birth() {
		return artist_birth;
	}

	public void setArtist_birth(String artist_birth) {
		this.artist_birth = artist_birth;
	}

	public String getArtist_gender() {
		return artist_gender;
	}

	public void setArtist_gender(String artist_gender) {
		this.artist_gender = artist_gender;
	}

	public String getTalent() {
		return talent;
	}

	public void setTalent(String talent) {
		this.talent = talent;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}
	
	@Override
	public String toString() {
		year = artist_birth.substring(0, 4);
		month = artist_birth.substring(4, 6);
		date = artist_birth.substring(6, 8);
		return String.format("참가번호 : %s, 참가자명 : %s, 생년월일 : %s월 %s월 %s일 , 성별 : %s, 특기 : %s, 소속사 : %s", artist_id, artist_name, year, month, date, artist_gender, talent, agency);
	}

}
