package jdbcOilShock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OilDAO {

	// 전체 매출 조회
	public void viewSalesStatistics(List<OilDTO> list) {

		DecimalFormat df = new DecimalFormat("#,###");

		int allcost = 0;

		System.out.printf("%-8s %-12s %-10s %6s %-8s %-8s %-8s %-15s %-18s %12s%n", "매출번호", "주유일자", "유종", "주유량", "결제",
				"회원성명", "회원번호", "전화번호", "카드번호", "금액");

		for (OilDTO dto : list) {

			String date = dto.getOildate();

			String year = date.substring(0, 4);
			String month = date.substring(4, 6);
			String day = date.substring(6, 8);

			String custname = dto.getCustname();
			String custno = dto.getCustno();
			String creditcart = dto.getCreditcart();
			String custtel1 = dto.getCusttel1();
			String custtel2 = dto.getCusttel2();
			String custtel3 = dto.getCusttel3();
			String paytype = "1".equals(dto.getPaytype()) ? "현금" : "카드";

			allcost += dto.getOilcost();

			if (custname == null && custno == null && custtel1 == null) {
				custname = "비회원";
				custno = "비회원";
				custtel1 = "000";
				custtel2 = "0000";
				custtel3 = "0000";
			}

			if (creditcart == null) {
				creditcart = "";
			}

			System.out.printf("%-8s %-12s %-10s %6d %-8s %-8s %-8s %-15s %-18s %12s%n", dto.getSaleno(),
					year + "년" + month + "월" + day + "일", dto.getOilname(), dto.getAmount(), paytype, custname, custno,
					custtel1 + "-" + custtel2 + "-" + custtel3, creditcart == null ? "" : creditcart,
					df.format(dto.getOilcost()));

		}

		System.out.println("매출총액 : " + allcost);
	}

	public List<OilDTO> SalesStatistics() {

		List<OilDTO> list = new ArrayList<OilDTO>();

		String sql = """
				select s.saleno, s.oildate, o.oilname, s.amount,
				       s.paytype, c.custname, c.custno, c.custtel1,
				       c.custtel2, c.custtel3, c.custtel3, s.creditcart, s.oilcost
				from tbl_saleinfo s
				left outer join tbl_oilinfo o
				on s.oiltype = o.oiltype
				left outer join tbl_custinfo c
				on c.custno = s.custno
								""";

		try (Connection conn = DBmanager.getInstance();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {

				OilDTO dto = new OilDTO();

				dto.setSaleno(rs.getString("saleno"));
				dto.setOildate(rs.getString("oildate"));
				dto.setOilname(rs.getString("oilname"));
				dto.setAmount(rs.getInt("amount"));
				dto.setPaytype(rs.getString("paytype"));
				dto.setCustname(rs.getString("custname"));
				dto.setCustno(rs.getString("custno"));
				dto.setCusttel1(rs.getString("custtel1"));
				dto.setCusttel2(rs.getString("custtel2"));
				dto.setCusttel3(rs.getString("custtel3"));
				dto.setCreditcart(rs.getString("creditcart"));
				dto.setOilcost(rs.getInt("oilcost"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 일매출통계
	public void viewDailySalesStatistics(List<OilDTO> list) {

		String year;
		String month;
		String day;

		DecimalFormat df = new DecimalFormat("#,###");

		System.out.println("주유일자\t\t유종\t\t건수\t\t금액");

		for (OilDTO dto : list) {

			String date = dto.getOildate();

			year = date.substring(0, 4);
			month = date.substring(4, 6);
			day = date.substring(6, 8);

			System.out.printf("%s년%s월%s일\t%-8s\t%2d\t%15s", year, month, day, dto.getOilname(), dto.getCnt_oilname(),
					df.format(dto.getDate_cost()));
			System.out.println();
		}

	}

	public List<OilDTO> dailySalesStatistics() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<OilDTO> list = new ArrayList<OilDTO>();

		String sql = """
				select s.oildate, o.oilname,
					count(s.saleno) as cnt_oilname,
					sum(s.oilcost) as date_cost
				from tbl_saleinfo s
				join tbl_oilinfo o
				on s.oiltype = o.oiltype
				group by s.oildate, o.oilname
				order by s.oildate asc, o.oilname asc
				""";

		try {

			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				OilDTO dto = new OilDTO();

				dto.setOildate(rs.getString("oildate"));
				dto.setOilname(rs.getString("oilname"));
				dto.setCnt_oilname(rs.getInt("cnt_oilname"));
				dto.setDate_cost(rs.getInt("date_cost"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
