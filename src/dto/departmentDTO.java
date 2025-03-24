package dto;

public class departmentDTO {
    private String departmentCode = null;
    private String departmentName = null;



    public departmentDTO() {

    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void prt() {
        System.out.println("-----부서 정보-----");
        System.out.println("부서 코드: " + this.departmentCode);
        System.out.println("부서 이름: " + this.departmentName);

    }
}
