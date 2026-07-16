package jdbcOilShock;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class OilMain {

	public static void main(String[] args) {

		Connection conn = DBmanager.getInstance();
		OilDAO dao = new OilDAO();

		Scanner sc = new Scanner(System.in);
		boolean flag = true;

		if (conn != null) {

			while (flag) {
				System.out.println("[1] 전체 매출 조회 [2] 일매출통계 [0] 종료");
				int menu = sc.nextInt();

				if (menu == 1) {
					
					List<OilDTO> list = dao.SalesStatistics();
					dao.viewSalesStatistics(list);

				} else if (menu == 2) {
					
					List<OilDTO> list = dao.dailySalesStatistics();
					dao.viewDailySalesStatistics(list);

				} else if (menu == 0) {
					System.out.println("종료 합니다");
					flag = false;
				}

			}

		}

	}

}
