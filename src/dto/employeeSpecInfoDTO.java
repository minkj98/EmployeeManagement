package dto;

public class employeeSpecInfoDTO {
    String employeeCode = null;   //직원 코드
    String phoneNum = null;       //전화번호
    String residenceAddress = null;  //거주지
    String certifications = null;    //자격증
    String education = null;   //학력
    String career = null;   //경력


    public employeeSpecInfoDTO() {

    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public void prt() {
        System.out.println("-----해당 직원의 특수 정보-----");
        System.out.println("전화번호: " + this.phoneNum);
        System.out.println("거주지: " + this.residenceAddress);
        System.out.println("보유 자격증: " + this.certifications);
        System.out.println("학력: " + this.education);
        System.out.println("경력: " + this.career);


    }
}
