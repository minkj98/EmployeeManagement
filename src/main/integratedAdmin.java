package main;

import java.util.ArrayList;
import java.util.Scanner;
import dao.departmentDAO;
import dto.departmentDTO;

public class integratedAdmin {
    private Scanner Scan = new Scanner(System.in);

    departmentDAO dado = new departmentDAO();
    integratedAdmin(){


        boolean flag = true;
        System.out.println("통합 관리자 전용 기능입니다.");
        while (flag) {
            System.out.println("[1] 부서 추가 [2] 부서 삭제 [3] 부서 수정 [4] 부서 전체 조회 [5] 종료");
            int choice = Scan.nextInt();
            switch (choice) {
                case 1:
                    registerDepartment();
                    break;
                case 2:
                    deleteDepartment();
                    break;
                case 3:
                    modifyDepartment();
                    break;
                case 4:
                    allViewDeoartment();
                    break;
                case 5:
                    System.out.println("통합 관리자 기능을 종료합니다.");
                    flag = false;
                    break;
            }

        }


    }

    private void registerDepartment() {
        System.out.println("부서 등록 기능입니다.");
        System.out.println("등록할 부서 코드을 입력하세요");
        String registDepartmentCode = Scan.nextLine();
        System.out.println("등록할 부서 이름을 입력하세요");
        String registDepartmentName = Scan.nextLine();
        departmentDTO dato = new departmentDTO();
        dato.setDepartmentCode(registDepartmentCode);
        dato.setDepartmentName(registDepartmentName);
        dado.insert(dato);

    }

    private void deleteDepartment() {
        System.out.println("부서 삭제 기능입니다.");
        System.out.println("삭제할 부서 코드를 입력하세요");
        String deleteDepartmentCode = Scan.nextLine();
        dado.delete(deleteDepartmentCode);
        
    }

    private void modifyDepartment() {
        System.out.println("부서 수정 기능입니다.");
        System.out.println("수정을 원하는 부서의 코드를 입력하세요");
        String modWantDepartmentCode = Scan.nextLine();
        System.out.println("수정할 부서 이름을 입력하세요");
        String modDepartmentName = Scan.nextLine();
        dado.update(modWantDepartmentCode, modDepartmentName);


    }

    private void allViewDeoartment() {
        System.out.println("부서 전체보기 기능입니다.");
        ArrayList<departmentDTO> dlist = dado.selectAll();
        for(departmentDTO d : dlist){
            d.prt();
        }
    }
}
