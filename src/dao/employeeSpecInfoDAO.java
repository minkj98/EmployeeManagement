package dao;

import dto.employeeSpecInfoDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class employeeSpecInfoDAO extends superDAO{
    private static employeeSpecInfoDAO employeespecinfodao = null;

    public employeeSpecInfoDAO() {
        super();
    }

    public static employeeSpecInfoDAO getInstance() {
        if(employeespecinfodao == null){
            employeeSpecInfoDAO employeespecdao = new employeeSpecInfoDAO();
        }
        return employeespecinfodao;
    }

    public void insert(employeeSpecInfoDTO esdto) {
        PreparedStatement psmt = null;
        try{
            if(getConnection()!=null){
                String sql = "insert into specInfo values(?,?,?,?,?,?)";
                psmt = conn.prepareStatement(sql);
                psmt.setString(1, esdto.getEmployeeCode());
                psmt.setString(2, esdto.getPhoneNum());
                psmt.setString(3, esdto.getResidenceAddress());
                psmt.setString(4, esdto.getCertifications());
                psmt.setString(5, esdto.getEducation());
                psmt.setString(6, esdto.getCareer());
                int result = psmt.executeUpdate();
                System.out.println(result + "건 삽입 완료");
            }
        }catch(Exception e){
            System.out.println("삽입 실패: " + e.getMessage());
            e.printStackTrace();
        }finally {
            try{
                psmt.close();
                conn.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void update(employeeSpecInfoDTO esdto) {
        PreparedStatement psmt = null;
        try{
            if(getConnection()!=null){
                String sql =  "UPDATE specInfo " +
                              "SET phoneNum = ?, " +
                              "residenceAddress = ?, " +
                              "certifications = ? " +
                              "WHERE employeeCode = ?";
                psmt = conn.prepareStatement(sql);
                psmt.setString(1, esdto.getPhoneNum());
                psmt.setString(2, esdto.getResidenceAddress());
                psmt.setString(3, esdto.getCertifications());
                psmt.setString(4, esdto.getEmployeeCode());
                int result = psmt.executeUpdate();
                System.out.println(result + "건 수정 완료");
            }
        }catch(Exception e){
            System.out.println("수정 실패: " + e.getMessage());
        }finally {
            try{
                conn.close();
                psmt.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public employeeSpecInfoDTO selectSpecInfo(String employeeCode) {
        employeeSpecInfoDTO specInfo = null;
        ResultSet rs = null;
        PreparedStatement psmt = null;

        try {
            if (getConnection() != null) {
                String sql = "select * from specInfo where employeeCode = ?";
                psmt = conn.prepareStatement(sql);
                psmt.setString(1, employeeCode);  // 직원 코드로 조회
                rs = psmt.executeQuery();
                while (rs.next()) {
                    specInfo = new employeeSpecInfoDTO();
                    specInfo.setEmployeeCode(rs.getString("employeeCode"));
                    specInfo.setPhoneNum(rs.getString("phoneNum"));
                    specInfo.setResidenceAddress(rs.getString("residenceAddress"));
                    specInfo.setCertifications(rs.getString("certifications"));
                    specInfo.setEducation(rs.getString("education"));
                    specInfo.setCareer(rs.getString("career"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                psmt.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return specInfo;
    }
}
