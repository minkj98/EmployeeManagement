package main;

import java.util.Scanner;

public class account {
	Scanner Scan = new Scanner(System.in);

	public account() {
		boolean flag = true;
		while (flag) {
			System.out.println("관리자 계정 등록 기능입니다.");
			System.out.println("[1] 통합 관리자 계정 생성 [2] 부서 관리자 계정 생성 [3] 종료");
			int choice = Scan.nextInt();
			Scan.nextLine();
			switch (choice) {
			case 1:
				integratedAccount integratedaccount = new integratedAccount();
				break;
			case 2:
				departmentAccount departmentaccount = new departmentAccount();
				break;
			case 3:
				System.out.println("프로그램을 종료합니다.");
				flag = false;
				break;
			}
		}
	}
}
