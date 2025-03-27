package dao;

import dto.departmentDTO;
import java.sql.*;
import java.util.ArrayList;

public class departmentDAO extends superDAO {
    private static departmentDAO departmentdao = null;

    public departmentDAO() {
        super();
    }

    public static departmentDAO getInstance() {
        if (departmentdao == null) {
            departmentdao = new departmentDAO();
        }
        return departmentdao;
    }

    public void insert(departmentDTO dato) {
        PreparedStatement psmt = null;
        try {
            if (getConnection() != null) {
                String sql = "insert into department values(?, ?)";
                psmt = conn.prepareStatement(sql);
                psmt.setString(1, dato.getDepartmentCode());
                psmt.setString(2, dato.getDepartmentName());
                int result = psmt.executeUpdate();
                System.out.println(result + "건 삽입 완료");
            }
        } catch (SQLException e) {
            System.out.println("삽입 실패: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (psmt != null){
                    psmt.close();
                }
                if(conn != null){
                    conn.close();
                }
                // conn은 닫지 않음 (재사용을 위해)
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(String deleteDepartmentCode) {
        PreparedStatement psmt = null;
        try {
            if (getConnection() != null) {
                String sql = "delete from department where departmentCode = ?";
                psmt = conn.prepareStatement(sql);
                psmt.setString(1, deleteDepartmentCode);
                int result = psmt.executeUpdate();
                System.out.println(result + "건 삭제 완료했습니다.");
            }
        } catch (Exception e) {
            System.out.println("삭제 실패: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (psmt != null){
                    psmt.close();
                }
                if(conn != null){
                    conn.close();
                }
                // conn은 닫지 않음 (재사용을 위해)
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update(String modWantDepartmentCode, String modDepartmentName) {
        PreparedStatement psmt = null;
        try {
            if (getConnection() != null) {
                String sql = "update department set departmentName = ? where departmentCode = ?";
                psmt = conn.prepareStatement(sql);
                psmt.setString(1, modDepartmentName);
                psmt.setString(2, modWantDepartmentCode);
                int result = psmt.executeUpdate();
                System.out.println(result + "건 수정 완료");
            }
        } catch (Exception e) {
            System.out.println("수정 실패: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (psmt != null){
                    psmt.close();
                }
                if(conn != null){
                    conn.close();
                }
                // conn은 닫지 않음 (재사용을 위해)
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<departmentDTO> selectAll() {
        ArrayList<departmentDTO> dlist = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            if (getConnection() != null) {
                String sql = "select * from department";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    departmentDTO dto = new departmentDTO();
                    dto.setDepartmentCode(rs.getString("departmentCode"));
                    dto.setDepartmentName(rs.getString("departmentName"));
                    dlist.add(dto);
                }
            }
        } catch (Exception e) {
            System.out.println("조회 실패: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null){
                    rs.close();
                }
                if (stmt != null){
                    stmt.close();
                }
                // conn은 닫지 않음 (재사용을 위해)
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dlist;
    }

    public int findDepartmentSequence(String departmentCode) {
        Statement stmt = null;
        ResultSet rs = null;
        int sequenceValue = 0;
        try {
            if (getConnection() != null) {
                String sql = "select employee" + departmentCode + "seq.NEXTVAL from dual";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    sequenceValue = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("시퀀스 조회 실패: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null){
                    rs.close();
                }
                if (stmt != null){
                    stmt.close();
                }
                // conn은 닫지 않음 (재사용을 위해)
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sequenceValue;
    }

    public boolean duplicateDepartmentCode(String departmentCode) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            if (getConnection() != null) {
                String sql = "SELECT COUNT(*) FROM department WHERE departmentCode = ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, departmentCode);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    return rs.getInt(1) > 0; // 부서 코드가 존재하면 true 반환
                }
            }
            return false; // 부서 코드가 존재하지 않으면 false 반환
        } catch (SQLException e) {
            throw new SQLException("중복 확인 실패: " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null){
                    rs.close();
                }
                if (pstmt != null){
                    pstmt.close();
                }
                // conn은 닫지 않음 (재사용을 위해)
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}