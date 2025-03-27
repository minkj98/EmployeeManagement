package dao;

import dto.integratedAdminAccountDTO;

import java.sql.*;
import java.util.ArrayList;

public class integratedAdminAccountDAO extends superDAO {
	private static integratedAdminAccountDAO integratedAdminAccountdao = null;

	public integratedAdminAccountDAO() {
		super();
	}

	private static integratedAdminAccountDAO getInstance() {
		if (integratedAdminAccountdao == null) {
			integratedAdminAccountdao = new integratedAdminAccountDAO();
		}
		return integratedAdminAccountdao;
	}

	public static integratedAdminAccountDTO select(String inteId) {
		PreparedStatement psmt = null;
		ResultSet rs = null;
		integratedAdminAccountDTO account = null;
		try{
			if(getConnection() != null){
				String sql = "SELECT * FROM integratedAdminAccount WHERE IntegratedId = ?";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, inteId);
				rs = psmt.executeQuery();
				if(rs.next()){
					account = new integratedAdminAccountDTO();
					account = integratedAdminAccountDAO.select(rs.getString("IntegratedId"));
					account.setIntegratedId(rs.getString("IntegratedPassword"));
				}
			}

		}catch (Exception e){
			System.out.println("조회 실패: " + e.getMessage());

		}finally {
			try{
				psmt.close();
				rs.close();
				conn.close();


			}catch (Exception e){

			}
		}
		return account;

	}

	public void insert(integratedAdminAccountDTO iadto) {
		PreparedStatement psmt = null;
		try {
			if (getConnection() != null) {
				String sql = "insert into integratedAdminAccount values(?,?)";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, iadto.getIntegratedId());
				psmt.setString(2, iadto.getIntegratedPassword());
				int result = psmt.executeUpdate();
				System.out.println(result + "건 삽입 완료");

			}
		} catch (SQLException e) {
			System.out.println("삽입 실패" + e.getMessage());

		} finally {
			try {
				psmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void delete(String deleteId) {
		PreparedStatement psmt = null;
		try {
			if (getConnection() != null) {
				String sql = "delete from integratedAdminAccount where integratedId = ?";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, deleteId);
				int result = psmt.executeUpdate();
				System.out.println(result + "건 삭제 완료");
			}
		} catch (SQLException e) {
			System.out.println("삭제 실패 " + e.getMessage());

		} finally {
			try {
				psmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void update(String modWandId, String modPass) {
		PreparedStatement psmt = null;
		try {
			if (getConnection() != null) {
				String sql = "UPDATE integratedAdminAccount " +
							 "SET IntegratedPassword = ? " +
							 "WHERE IntegratedId = ?";

				psmt = conn.prepareStatement(sql);
				psmt.setString(1, modPass);
				psmt.setString(2, modWandId);
				int result = psmt.executeUpdate();
				System.out.println(result + "건 수정 완료");

			}
		} catch (SQLException e) {
			System.out.println("수정 실패" + e.getMessage());

		} finally {
			try {
				conn.close();
				psmt.close();

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}

	public boolean checkIntegratedAdminAccount(String integratedAdminId, String integratedAdminPass){
		System.out.println("계정 로그인 중");
		PreparedStatement psmt = null;
		boolean flag = false;
		try{
			if(getConnection() != null){
				String sql = "SELECT * " +
							 "FROM integratedAdminAccount " +
							 "WHERE IntegratedId = ? " +
							 "AND IntegratedPassword = ?";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, integratedAdminId);
				psmt.setString(2, integratedAdminPass);
				ResultSet rs = psmt.executeQuery();
				if(rs.next()){
					System.out.println(rs.getString("IntegratedId"));
					System.out.println(rs.getString("IntegratedPassword"));
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

	public ArrayList<integratedAdminAccountDTO> selectAll() {
		ArrayList<integratedAdminAccountDTO> ialist = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		try{
			if (getConnection() != null) {
				String sql = "select * from integratedAdminAccount";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					integratedAdminAccountDTO iadto = new integratedAdminAccountDTO();
					iadto.setIntegratedId(rs.getString("IntegratedId"));
					iadto.setIntegratedPassword(rs.getString("IntegratedPassword"));
					ialist.add(iadto);
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
		return ialist;
	}

	public boolean duplicateintegratedAdminId(String duplicateIntegratedAdminId) {
		boolean flag = false;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			if (getConnection() != null) {
				String sql = "SELECT * FROM integratedAdminAccount WHERE IntegratedId = ?";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, duplicateIntegratedAdminId);
				rs = psmt.executeQuery(); // 쿼리 실행

				// 결과 확인
				if (rs.next()) { // 결과가 있으면 중복
					flag = true;
				}
			}
		} catch (Exception e) {
			System.out.println("중복 조회 실패: " + e.getMessage());// 예외 발생 시 스택 트레이스 출력
		} finally {
			// 자원 해제
			try {
				rs.close();
				psmt.close();
				conn.close(); // 연결 해제
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag; // 중복 여부 반환
	}

}
