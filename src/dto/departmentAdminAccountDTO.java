package dto;

public class departmentAdminAccountDTO {
	private String departmentId = null;
	private String departmentPass = null;

	public departmentAdminAccountDTO(String departmentId, String departmentPass) {
		this.departmentId = departmentId;
		this.departmentPass = departmentPass;
	}

	public departmentAdminAccountDTO() {

	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentPass() {
		return departmentPass;
	}

	public void setDepartmentPass(String departmentPass) {
		this.departmentPass = departmentPass;
	}

	public void prt(){
		System.out.println("-----부서 관리자 계정 정보-----");
		System.out.println("관리자 아이디: " + this.departmentId);
		System.out.println("관리자 비밀번호: " + this.departmentPass);
	}

}
