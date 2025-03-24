package main;
import dao.departmentAdminAccountDAO;
import dao.integratedAdminAccountDAO;
import java.util.Scanner;

public class admin {
	private integratedAdminAccountDAO iadao = new integratedAdminAccountDAO();
	private departmentAdminAccountDAO dadao = new departmentAdminAccountDAO();
	private Scanner Scan = new Scanner(System.in);
	
	admin() {
		System.out.println("해당 프로그램은 관리자 계정만 사용 가능합니다.");

		boolean flag = true;
		while (flag) {
			System.out.println("[1] 통합 관리자 [2] 부서 관리자 [3] 종료");
			System.out.println("관리자 모드를 선택하세요");
			int choice = Scan.nextInt();
			Scan.nextLine();
			switch (choice) {
			case 1:
				System.out.println("통합 관리자 아이디를 입력하세요");
				String integratedAdminId = Scan.nextLine();
				System.out.println("통합 관리자 비밀번호를 입력하세요");
				String integratedAdminPass = Scan.nextLine();
				if (iadao.checkIntegratedAdminAccount(integratedAdminId, integratedAdminPass)) {
					System.out.println("통합 관리자 계정 로그인 성공했습니다.");
					integratedAdmin integratedAdmin = new integratedAdmin();
					break;
				}else{
					System.out.println("해당 통합 관리자 계정은 존재하지 않습니다.");
				}
				break;
			case 2:
				System.out.println("부서 관리자 아이디를 입력하세요");
				String departmentAdminId = Scan.nextLine();
				System.out.println("부서 관리자 비밀번호를 입력하세요");
				String departmentAdminPass = Scan.nextLine();
				if(dadao.checkDepartmentAdminAccount(departmentAdminId, departmentAdminPass)){
					System.out.println("부서 관리자 계정 로그인을 성공했습니다.");
					departmentAdmin departmentAdmin = new departmentAdmin();
					break;
				}else{
					System.out.println("해당 부서 관리자 계정은 존재하지 않습니다.");
				}
				break;
			case 3:
				System.out.println("관리자 메뉴를 종료합니다.");
				flag = false;
				break;
			}
		}
	}
}