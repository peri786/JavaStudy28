package jdbc;

public class ScoreDTO {

	// 오라클 테이블을 참조하여 멤버변수를 만든다

	private int idx; // 학번
	private String name; // 이름
	private int kor; // 국어
	private int eng; // 영어
	private int mat; // 수학
	
	private int tkor; // 국어 총점
	private int teng; // 영어 총점
	private int tmat; // 수학 총점
	
	private double akor;
	private double aeng;
	private double amat;

	public double getAkor() {
		return akor;
	}

	public void setAkor(double akor) {
		this.akor = akor;
	}

	public double getAeng() {
		return aeng;
	}

	public void setAeng(double aeng) {
		this.aeng = aeng;
	}

	public double getAmat() {
		return amat;
	}

	public void setAmat(double amat) {
		this.amat = amat;
	}

	public int getTkor() {
		return tkor;
	}

	public void setTkor(int tkor) {
		this.tkor = tkor;
	}

	public int getTeng() {
		return teng;
	}

	public void setTeng(int teng) {
		this.teng = teng;
	}

	public int getTmat() {
		return tmat;
	}

	public void setTmat(int tmat) {
		this.tmat = tmat;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getMat() {
		return mat;
	}

	public void setMat(int mat) {
		this.mat = mat;
	}
	
	@Override
	public String toString() {
		return String.format("학번 : %d, 이름 : %s, 국어 : %d, 영어 : %d, 수학 : %d", idx, name, kor, eng, mat);
	}

}
