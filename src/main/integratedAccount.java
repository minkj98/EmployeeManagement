package main;

import dao.integratedAdminAccountDAO;
import dto.departmentAdminAccountDTO;
import dto.integratedAdminAccountDTO;
import java.util.Scanner;
import java.util.ArrayList;
public class integratedAccount {
	private integratedAdminAccountDAO iadao = new integratedAdminAccountDAO();

	private Scanner Scan = new Scanner(System.in);

	public integratedAccount() {
		boolean flag = true;
		while (flag) {
			System.out.println("-----통합 관리자-----");
			System.out.println("[1] 계정 등록 [2] 계정 삭제 [3] 계정 전체보기 [4] 계정 조회 [5] 계정 수정 [6] 중복 확인 [7]종료");
			int choice = Scan.nextInt();
			Scan.nextLine();
			switch (choice) {
			case 1:
				registerAccount();
				break;
			case 2:
				deleteAccount();
				break;
			case 3:
				allViewAccount();
				break;
			case 4:
				searchAccount();
				break;
			case 5:
				modifyAccount();
				break;
			case 6:
				checkId();
				break;
			case 7:
				System.out.println("계정 관리 종료");
				flag = false;
				break;

			}
		}

	}

	private void checkId() {
		System.out.println("아이디 중복 확인 기능입니다.");
		System.out.println("중복 확인할 아이디를 입력하세요");
		String duplicateIntegratedAdminId = Scan.nextLine();
		integratedAdminAccountDTO iadto = new integratedAdminAccountDTO();
		iadto.setIntegratedId(duplicateIntegratedAdminId);

		// 중복 확인 메서드 호출
		boolean isDuplicate = iadao.duplicateintegratedAdminId(duplicateIntegratedAdminId);

		// true면 중복 false면 중복 아님
		if (isDuplicate) {
			System.out.println("중복된 ID입니다.");
		} else {
			System.out.println("사용 가능한 ID입니다.");
		}
	}
	
	private void searchAccount() {
		System.out.println("특정 계정 보기 기능입니다.");
		System.out.println("조회를 원하는 통합 관리자의 id를 입력하세요");
		String inteId = Scan.nextLine();

		integratedAdminAccountDTO account = integratedAdminAccountDAO.select(inteId);

		if (account != null) {  // 계정 정보가 존재할 경우
			System.out.println("통합 관리자 계정 정보가 조회되었습니다.");
			account.prt();  // 계정 정보 출력
		} else {
			System.out.println("해당 통합 관리자 계정은 존재하지 않습니다.");
		}
	}

	private void allViewAccount() {
		System.out.println("계정 전체보기 기능입니다.");
		ArrayList<integratedAdminAccountDTO> ialist = iadao.selectAll();  // 전체 직원 정보 조회
		for (integratedAdminAccountDTO ia: ialist) {
			ia.prt();  // 직원 정보 출력
		}
	}

	private void registerAccount() {
		System.out.println("통합 관리자 계정 등록 기능입니다.");
		System.out.println("등록할 통합 관리자 아이디를 입력하세요");
		String integratedId = Scan.nextLine();
		System.out.println("등록할 통합 관리자 비밀번호를 입력하세요");
		String integratedpassword = Scan.nextLine();
		integratedAdminAccountDTO iadto = new integratedAdminAccountDTO();
		iadto.setIntegratedId(integratedId);
		iadto.setIntegratedPassword(integratedpassword);
		iadao.insert(iadto);
	}

	private void deleteAccount() {
		System.out.println("통합 관리자 계정 삭제 기능입니다.");
		System.out.println("삭제할 통합 관리자 아이디를 입력하세요");
		String deleteId = Scan.nextLine();
		iadao.delete(deleteId);
	}

	private void modifyAccount() {
		System.out.println("통합 관리자 비밀번호 변경 기능입니다.");
		System.out.println("수정을 원하는 관리자 계정을 입력하세요");
		String modWandId = Scan.nextLine();
		System.out.println("수정할 관리자 비밀번호를 입력하세요");
		String modPass = Scan.nextLine();
		iadao.update(modWandId, modPass);
	}
}
