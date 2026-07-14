package jdbcArtist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmpDAO {

	public List<EmpDTO> empPrint() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = """
								select e.emp_name, d.dept_name
				from emp e
				left outer join dept d
				on e.dept_no = d.dept_no
								""";
		
		List<EmpDTO> list = new ArrayList<EmpDTO>();
		
		try {
			conn = DBmanager.getInstance();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				EmpDTO dto = new EmpDTO();
				dto.setEmp_name(rs.getString("emp_name"));
				dto.setDept_name(rs.getString("dept_name"));
				
				list.add(dto);
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
