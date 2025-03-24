package dao;

import dto.departmentAdminAccountDTO;
import dto.employeeDTO;
import dto.employeeSpecInfoDTO;
import java.sql.*;
import java.util.ArrayList;

public class departmentAdminAccountDAO extends superDAO{
	private static departmentAdminAccountDAO departmentAdminAccountdao = null;
	public departmentAdminAccountDAO()
	{
		super();
	}
	public departmentAdminAccountDAO getInstance() {
		if (departmentAdminAccountdao == null) {
			departmentAdminAccountdao = new departmentAdminAccountDAO();
		}
		return departmentAdminAccountdao;
	}

	public void insert(departmentAdminAccountDTO dadto) {
		PreparedStatement psmt = null;
		try {
			if (getConnection() != null) {
				String sql = "insert into departmentAdminAccount values(?,?)";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, dadto.getDepartmentId());
				psmt.setString(2, dadto.getDepartmentPass());
				int result = psmt.executeUpdate();
				System.out.println(result + "건 삽입 완료");
			}
		} catch (SQLException e) {
			System.out.println("삽입 실패" + e.getMessage());
		} finally {
			try {
				conn.close();
				psmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void delete(String delDepartmentId) {
		PreparedStatement psmt = null;
		try {
			if (getConnection() != null) {
				String sql = "delete from departmentAdminAccount where DepartmentId = ?";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, delDepartmentId);
				int result = psmt.executeUpdate();
				System.out.println(result + "건 삭제 완료");
			}else{
				System.out.println("부서 관리자 계정이 존재하지 않습니다.");
			}
		} catch (SQLException e) {
			System.out.println("삭제 실패" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				psmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void update(departmentAdminAccountDTO dadto) {
		PreparedStatement psmt = null;
		try {
			if(getConnection() != null) {
				String sql = "UPDATE departmentAdminAccount " +
							 "SET DepartmentPassword = ? " +
							 "WHERE DepartmentId = ?";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, dadto.getDepartmentPass());
				psmt.setString(2, dadto.getDepartmentId());
				int result = psmt.executeUpdate();
				System.out.println(result + "건 수정 완료");
			}
		}catch(SQLException e) {
			System.out.println("수정 실패" + e.getMessage());
			e.printStackTrace();

		}finally {
			try {
				psmt.close();
				conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean checkDepartmentAdminAccount(String departmentId, String departmentPass) {
		System.out.println("계정 로그인 중");
		PreparedStatement psmt = null;
		boolean flag = false;
		try{
			if(getConnection() != null){
				String sql = "SELECT * " +
							 "FROM departmentAdminAccount " +
							 "WHERE DepartmentId = ? " +
							 "AND DepartmentPassword = ?";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, departmentId);
				psmt.setString(2, departmentPass);
				ResultSet rs = psmt.executeQuery();
				while(rs.next()){
					System.out.println("-----로그인한 계정-----");
					System.out.println(rs.getString("DepartmentId"));
					System.out.println(rs.getString("DepartmentPassword"));
					flag = true;
				}
			}
		}catch(Exception e){

		}finally{
			try{
				psmt.close();
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return flag;
	}

	public ArrayList<departmentAdminAccountDTO> selectAll() {
		ArrayList<departmentAdminAccountDTO> dalist = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		try{
			if (getConnection() != null) {
				String sql = "select * from departmentAdminAccount";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					departmentAdminAccountDTO dadto = new departmentAdminAccountDTO();
					dadto.setDepartmentId(rs.getString("DepartmentId"));
					dadto.setDepartmentPass(rs.getString("DepartmentPassword"));
					dalist.add(dadto);
				}
			}
		}catch (Exception e) {
			System.out.println("조회 실패: " + e.getMessage());
		}finally {
			try{
				stmt.close();
				rs.close();
				conn.close();

			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dalist;
	}

	public static departmentAdminAccountDTO select(String deprId) {
		PreparedStatement psmt = null;
		ResultSet rs = null;
		departmentAdminAccountDTO account = null;
		try {
			if(getConnection() != null){
				String sql = "SELECT * FROM departmentAdminAccount WHERE DepartmentId = ?";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, deprId);  // 입력받은 ID를 쿼리 파라미터로 설정

				// 쿼리 실행 및 결과 받기
				rs = psmt.executeQuery();

				// 결과가 있으면 departmentAdminAccountDTO 객체로 매핑
				if (rs.next()) {
					account = new departmentAdminAccountDTO();
					account.setDepartmentId(rs.getString("DepartmentId"));
					account.setDepartmentPass(rs.getString("DepartmentPassword"));
				}
			}
		} catch (Exception e) {
			System.out.println("조회 실패: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				psmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return account;
	}
}
