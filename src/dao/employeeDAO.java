package dao;

import dto.employeeDTO;
import dto.employeeSpecInfoDTO;
import dao.departmentDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class employeeDAO extends superDAO {
    private static employeeDAO employeedao = null;
    private static departmentDAO departmentdao = new departmentDAO();

    public employeeDAO() {
        super();
    }

    public static employeeDAO getInstance() {
        if(employeedao == null){
            employeeDAO employeedao = new employeeDAO();
        }
        return employeedao;
    }

    public void insert(employeeDTO edto) {
        PreparedStatement psmt = null;
        try{
            if(getConnection() != null){
                String sql = "insert into employee values(?,?,?,?,?,?,?,?)";
                psmt = conn.prepareStatement(sql);
                psmt.setString(1, edto.getEmployeeCode());
                psmt.setString(2, edto.getEmployeeName());
                psmt.setString(3, edto.getPosition());
                psmt.setDouble(4, edto.getSalary());
                psmt.setString(5, edto.getDepartmentCode());
                psmt.setInt(6, edto.getHireDate());
                psmt.setString(7, edto.getEmail());
                psmt.setString(8, edto.getEmploymentStatus());
                int result = psmt.executeUpdate();
                System.out.println(result + "건 삽입 완료");
            }
        }catch(Exception e){
            System.out.println("삽입 실패:" + e.getMessage());
            e.printStackTrace();
        }finally {
            try{
                psmt.close();
                conn.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void delete(String delEmpCode) {
        PreparedStatement psmt = null;
        try{
            if(getConnection() != null){
                String sql = "delete" +
                             " from employee" +
                             " where employeeCode = ?" +
                             "and employeeCode" +
                             " in (select s.employeeCode from specInfo s)";
                psmt = conn.prepareStatement(sql);
                psmt.setString(1, delEmpCode);
                int result = psmt.executeUpdate();
                System.out.println(result + "건 삭제완료");
            }
        }catch (Exception e){
            System.out.println("삭제 실패: " + e.getMessage());
            e.printStackTrace();

        }finally {
            try{
                conn.close();
                psmt.close();

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update(employeeDTO edto) {
        PreparedStatement psmt = null;
        try{
            if(getConnection() != null){
                String sql = "update employee" +
                             " set position = ?, salary = ?, email = ?, employmentStatus = ?" +
                             "where employeeCode = ?";
                psmt = conn.prepareStatement(sql);
                psmt.setString(1, edto.getPosition());
                psmt.setDouble(2, edto.getSalary());
                psmt.setString(3, edto.getEmail());
                psmt.setString(4, edto.getEmploymentStatus());
                psmt.setString(5, edto.getEmployeeCode());
                int result = psmt.executeUpdate();
                System.out.println(result + "건 수정 완료");

            }
        }catch (Exception e){
            System.out.println("수정 실패: " + e.getMessage());
            e.printStackTrace();

        }finally {
            try{
                psmt.close();
                conn.close();

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<employeeDTO> selectAll() {
        ArrayList<employeeDTO> elist = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            if(getConnection() != null) {
                String sql =  "SELECT e.*, " +
                              "s.phoneNum, " +
                              "s.residenceAddress, " +
                              "s.certifications, " +
                              "s.education, " +
                              "s.career " +
                              "FROM employee e " +
                              "LEFT JOIN specInfo s ON e.employeeCode = s.employeeCode";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    employeeDTO edto = new employeeDTO();
                    edto.setEmployeeCode(rs.getString("employeeCode"));
                    edto.setEmployeeName(rs.getString("employeeName"));
                    edto.setPosition(rs.getString("position"));
                    edto.setSalary(rs.getDouble("salary"));
                    edto.setDepartmentCode(rs.getString("departmentCode"));
                    edto.setHireDate(rs.getInt("hireYear"));
                    edto.setEmail(rs.getString("email"));
                    edto.setEmploymentStatus(rs.getString("employmentStatus"));

                    // 특수 정보 추가

                    employeeSpecInfoDTO esdto = new employeeSpecInfoDTO();
                    esdto.setPhoneNum(rs.getString("phoneNum"));
                    esdto.setResidenceAddress(rs.getString("residenceAddress"));
                    esdto.setCertifications(rs.getString("certifications"));
                    esdto.setEducation(rs.getString("education"));
                    esdto.setCareer(rs.getString("career"));
                    elist.add(edto);
                }
            }
        } catch(Exception e) {
            System.out.println("조회 실패: " + e.getMessage());
        } finally {
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return elist;
    }


    public ArrayList<employeeDTO> selectSpecAll(String specEmployeeCode) {
        ArrayList<employeeDTO> elist = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            if (getConnection() != null) {
                String sql = "SELECT e.*, " +
                             "s.phoneNum, " +
                             "s.residenceAddress, " +
                             "s.certifications, " +
                             "s.education, " +
                             "s.career " +
                             "FROM employee e " +
                             "LEFT JOIN specInfo s ON e.employeeCode = s.employeeCode " +
                             "WHERE e.employeeCode = ?";
                psmt = conn.prepareStatement(sql);
                psmt.setString(1, specEmployeeCode);
                rs = psmt.executeQuery();

                while (rs.next()) {
                    employeeDTO edto = new employeeDTO();
                    edto.setEmployeeCode(rs.getString("employeeCode"));
                    edto.setEmployeeName(rs.getString("employeeName"));
                    edto.setPosition(rs.getString("position"));
                    edto.setSalary(rs.getDouble("salary"));
                    edto.setDepartmentCode(rs.getString("departmentCode"));
                    edto.setHireDate(rs.getInt("hireYear"));
                    edto.setEmail(rs.getString("email"));
                    edto.setEmploymentStatus(rs.getString("employmentStatus"));

                    // 특수 정보 추가
                    employeeSpecInfoDTO esdto = new employeeSpecInfoDTO();
                    esdto.setPhoneNum(rs.getString("phoneNum"));
                    esdto.setResidenceAddress(rs.getString("residenceAddress"));
                    esdto.setCertifications(rs.getString("certifications"));
                    esdto.setEducation(rs.getString("education"));
                    esdto.setCareer(rs.getString("career"));
                    elist.add(edto);
                }
            }
        } catch (Exception e) {
            System.out.println("조회 실패: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (psmt != null) psmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return elist;
    }


    public String generateEmployeeCode(String deptCode, int hireYear) {
        // 입사 연도에서 마지막 두 자리 추출
        String yearTwoCut = String.format("%02d", hireYear % 100);

        // 부서에 맞는 시퀀스 조회
        int seqNum = departmentdao.findDepartmentSequence(deptCode);
        String seqNumFormat = String.format("%03d", seqNum);// 시퀀스를 3자리로 포맷

        // 직원 코드 생성 (부서 코드 + 입사 연도 뒷 두 자리 + 시퀀스 번호)
        return deptCode + yearTwoCut + seqNumFormat;
    }
}
