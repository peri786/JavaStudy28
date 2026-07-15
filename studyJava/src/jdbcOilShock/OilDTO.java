package jdbcOilShock;

public class OilDTO {

	private String oildate;
	private String oilname;
	private int cnt_oilname;
	private int date_cost;

	public String getOildate() {
		return oildate;
	}

	public void setOildate(String oildate) {
		this.oildate = oildate;
	}

	public String getOilname() {
		return oilname;
	}

	public void setOilname(String oilname) {
		this.oilname = oilname;
	}

	public int getCnt_oilname() {
		return cnt_oilname;
	}

	public void setCnt_oilname(int cnt_oilname) {
		this.cnt_oilname = cnt_oilname;
	}

	public int getDate_cost() {
		return date_cost;
	}

	public void setDate_cost(int date_cost) {
		this.date_cost = date_cost;
	}

}
