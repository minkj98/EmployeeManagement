package dto;

public class integratedAdminAccountDTO {
	private String integratedId = null;
	private String integratedPassword = null;

	public integratedAdminAccountDTO() {
		this.integratedId = integratedId;
		this.integratedPassword = integratedPassword;
	}


	public String getIntegratedId() {
		return integratedId;
	}

	public void setIntegratedId(String integratedId) {
		this.integratedId = integratedId;
	}

	public String getIntegratedPassword() {
		return integratedPassword;
	}

	public void setIntegratedPassword(String integratedPassword) {
		this.integratedPassword = integratedPassword;
	}

	public void prt(){
		System.out.println("-----통합 관리자 계정 정보-----");
		System.out.println("관리자 아이디: " + this.integratedId);
		System.out.println("관리자 비밀번호: " + this.integratedPassword);

	}
}