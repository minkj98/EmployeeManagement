package dto;

public class employeeDTO {
    String employeeCode = null;
    String employeeName = null;
    String position = null;
    double salary  = 0.0;
    String departmentCode = null;
    int hireDate = 0;
    String email = null;
    String employmentStatus = null;

    public employeeDTO() {

    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public int getHireDate() {
        return hireDate;
    }

    public void setHireDate(int hireDate) {
        this.hireDate = hireDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public void prt(){
        System.out.println("-----직원 정보-----");
        System.out.println("직원 코드:" + this.employeeCode);
        System.out.println("직원 이름:" + this.employeeName);
        System.out.println("직책: " + this.position);
        System.out.println("급여: " + this.salary);
        System.out.println("부서코드: " + this.departmentCode);
        System.out.println("입사연도: " + this.hireDate);
        System.out.println("이메일: " + this.email);
        System.out.println("근무 상태: " + this.employmentStatus);
    }
}
