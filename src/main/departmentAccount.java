package main;

import dao.departmentAdminAccountDAO;
import dto.departmentAdminAccountDTO;
import java.util.ArrayList;
import java.util.Scanner;

public class departmentAccount {
	private departmentAdminAccountDAO dadao = new departmentAdminAccountDAO();
	private Scanner Scan = new Scanner(System.in);

	departmentAccount() {
		boolean flag = true;
		while (flag) {
			System.out.println("[1] 계정 등록 [2] 계정 삭제 [3] 계정 조회 [4] 계정 전체보기 [5] 비밀번호 수정 [6] 종료");
			int choice = Scan.nextInt();
			Scan.nextLine();
			switch (choice) {
			case 1:
				resisterAccount();
				break;
			case 2:
				deleteAccount();
				break;
			case 3:
				searchAccount();
				break;
			case 4:
				allViewAccount();
				break;
			case 5:
				modifyAccount();
				break;
			case 6:
				System.out.println("계정 관리 종료");
				flag = false;
				break;
			}
		}

	}

	public void searchAccount() {
		System.out.println("특정 계정 조회 기능입니다.");
		System.out.println("조회를 원하는 부서 관리자 계정의 id를 입력하세요");
		String deptId = Scan.nextLine();  // 부서 관리자 계정 ID 입력 받기

		// 부서 관리자 계정 조회 DAO 메서드 호출
		departmentAdminAccountDTO account = departmentAdminAccountDAO.select(deptId);

		if (account != null) {  // 계정 정보가 존재할 경우
			System.out.println("부서 관리자 계정 정보가 조회되었습니다.");
			account.prt();  // 계정 정보 출력
		} else {
			System.out.println("해당 부서 관리자 계정은 존재하지 않습니다.");
		}
	}




	private void allViewAccount() {
		System.out.println("계정 전체보기 기능입니다.");
		ArrayList<departmentAdminAccountDTO> dalist = dadao.selectAll();  // 전체 직원 정보 조회
		for (departmentAdminAccountDTO da: dalist) {
			da.prt();  // 직원 정보 출력
		}
	}

	private void resisterAccount() {
		System.out.println("부서 관리자 계정 등록 기능입니다.");
		System.out.println("등록할 부서 관리자 아이디를 입력하세요");
		String departmentId = Scan.nextLine();
		System.out.println("등록할 부서 관리자 비밀번호를 입력하세요");
		String departmentPassword = Scan.nextLine();
		departmentAdminAccountDTO dadto = new departmentAdminAccountDTO();
		dadto.setDepartmentId(departmentId);
		dadto.setDepartmentPass(departmentPassword);
		dadao.insert(dadto);

	}

	private void deleteAccount() {
		System.out.println("부서 관리자 계정 삭제 기능입니다.");
		System.out.println("삭제할 부서 관리자 아이디를 입력하세요");
		String delDepartmentId = Scan.nextLine();
		dadao.delete(delDepartmentId);

	}

	private void modifyAccount() {
		System.out.println("부서 관리자 비밀번호 변경 기능입니다.");
		System.out.println("수정을 원하는 부서 관리자 아이디를 입력하세요");
		String modWantDepartmentId = Scan.nextLine();
		System.out.println("수정할 부서 관리자 비밀번호를 입력하세요");
		String modDepartmentPass = Scan.nextLine();
		departmentAdminAccountDTO dadto = new departmentAdminAccountDTO();
		dadto.setDepartmentId(modWantDepartmentId);
		dadto.setDepartmentPass(modDepartmentPass);
		dadao.update(dadto);

	}
}
