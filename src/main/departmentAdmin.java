package main;
import dao.employeeDAO;
import dto.employeeDTO;
import dao.employeeSpecInfoDAO;
import dto.employeeSpecInfoDTO;
import java.util.ArrayList;
import java.util.Scanner;
import dao.departmentDAO;

// 부서 관리자의 기능을 처리하는 클래스
public class departmentAdmin {

    // 각종 DAO 객체를 생성 (직원 정보, 부서 정보, 직원 특수 정보 처리)
    private static employeeDAO employeedao = new employeeDAO();
    private static departmentDAO departdao = new departmentDAO();
    private static employeeSpecInfoDAO employeespecinfodao = new employeeSpecInfoDAO();
    private  Scanner Scan = new Scanner(System.in);

    // 부서 관리자 메뉴를 위한 생성자
    departmentAdmin(){
        boolean flag = true;
        System.out.println("부서 관리자 전용 기능입니다.");

        // 무한 루프를 돌며 사용자의 선택을 받음
        while(flag){
            System.out.println("[1] 직원 정보 등록 [2] 직원 정보 삭제 [3] 직원 정보 수정 [4] 직원 정보 전체조회 [5] 특정 직원 조회 [6] 특정 정보 수정 [7] 종료");
            int choice = Scan.nextInt();  // 사용자 선택 받기
            Scan.nextLine();  // 엔터키 버퍼 처리
            switch(choice){
                case 1:
                    registerEmployee();  // 직원 등록
                    break;
                case 2:
                    deleteEmployee();  // 직원 삭제
                    break;
                case 3:
                    modifyEmployee();  // 직원 수정
                    break;
                case 4:
                    detailViewEmployee();  // 직원 정보 전체 조회
                    break;
                case 5:
                    lookUpEmployee();  // 특정 직원 조회
                    break;
                case 6:
                    modifySpecInfo();  // 직원의 특수 정보 수정
                    break;
                case 7:
                    System.out.println("부서 관리자 기능을 종료합니다.");  // 종료
                    flag = false;
                    break;
            }
        }
    }

    // 직원의 특수 정보를 수정하는 메서드
    private void modifySpecInfo() {
        System.out.println("직원의 특수 정보 수정 기능입니다.");
        System.out.println("수정을 원하는 직원의 직원 코드를 입력하세요");
        String modWantEmployeeCode = Scan.nextLine();  // 수정할 직원 코드 입력
        System.out.println("수정할 전화번호를 입력하세요");
        String modPhoneNum = Scan.nextLine();  // 수정할 전화번호 입력
        System.out.println("수정할 거주지를 입력하세요");
        String modResidenceAddress = Scan.nextLine();  // 수정할 거주지 입력
        System.out.println("수정할 자격증을 입력하세요");
        String Certifications = Scan.nextLine();  // 수정할 자격증 입력

        // 수정할 직원 특수 정보를 DTO에 저장
        employeeSpecInfoDTO esdto = new employeeSpecInfoDTO();
        esdto.setEmployeeCode(modWantEmployeeCode);
        esdto.setPhoneNum(modPhoneNum);
        esdto.setResidenceAddress(modResidenceAddress);
        esdto.setCertifications(Certifications);
        // 직원 특수 정보 수정 DAO 메서드 호출
        employeespecinfodao.update(esdto);
    }

    // 직원 정보를 등록하는 메서드
    private void registerEmployee() {
        System.out.println("직원 정보 등록 기능입니다.");
        System.out.println("직원 이름을 입력하세요");
        String employeeName = Scan.nextLine();  // 직원 이름 입력
        System.out.println("직원의 직책을 입력하세요");
        String employeePosition = Scan.nextLine();  // 직원 직책 입력
        System.out.println("직원의 급여를 입력하세요");
        double employeeSalary = Scan.nextDouble();  // 직원 급여 입력
        Scan.nextLine();  // 버퍼 비우기
        System.out.println("직원의 부서 코드를 입력하세요");
        String employeeDeptCode = Scan.nextLine();  // 부서 코드 입력
        System.out.println("직원의 입사연도를 입력하세요");
        int hireYear = Scan.nextInt();  // 입사 연도 입력
        Scan.nextLine();  // 버퍼 비우기
        System.out.println("직원의 이메일을 입력하세요");
        String employeeEmail = Scan.nextLine();  // 이메일 입력
        System.out.println("직원의 근무 상태를 입력하세요");
        String employeeStatus = Scan.nextLine();  // 근무 상태 입력

        // 입사 연도에서 마지막 두 자리만 추출
        String yearTwoCut = String.format("%02d", hireYear % 100);

        // 부서에 맞는 시퀀스를 찾아 직원 코드 생성
        int seqNum = departdao.findDepartmentSequence(employeeDeptCode);
        String seqNumFormat = String.format("%03d", seqNum);  // 시퀀스를 3자리로 포맷

        // 직원 코드 생성 (부서 코드 + 입사 연도 뒷 두 자리 + 시퀀스 번호)
        String employeeCode = employeeDeptCode + yearTwoCut + seqNumFormat;

        // 직원 DTO에 정보 설정
        employeeDTO edto = new employeeDTO();
        edto.setEmployeeName(employeeName);
        edto.setPosition(employeePosition);
        edto.setSalary(employeeSalary);
        edto.setDepartmentCode(employeeDeptCode);
        edto.setHireDate(hireYear);
        edto.setEmail(employeeEmail);
        edto.setEmploymentStatus(employeeStatus);
        edto.setEmployeeCode(employeeCode);
        // 직원 정보 등록 DAO 메서드 호출
        employeedao.insert(edto);

        // 직원의 특수 정보 입력
        System.out.println("직원의 특정 정보 등록 기능입니다.");
        System.out.println("직원의 전화번호를 입력하세요");
        String employeePhoneNum = Scan.nextLine();  // 전화번호 입력
        System.out.println("직원의 거주지를 입력하세요");
        String employeeResidenceAddress = Scan.nextLine();  // 거주지 입력
        System.out.println("직원이 보유한 자격증을 입력하세요");
        String employeeCertification = Scan.nextLine();  // 자격증 입력
        System.out.println("직원의 학력을 입력하세요");
        String employeeEducation = Scan.nextLine();  // 학력 입력
        System.out.println("직원의 경력을 입력하세요");
        String employeeCareer = Scan.nextLine();  // 경력 입력

        // 직원 특수 정보 DTO에 정보 설정
        employeeSpecInfoDTO esdto = new employeeSpecInfoDTO();
        esdto.setEmployeeCode(employeeCode);
        esdto.setPhoneNum(employeePhoneNum);
        esdto.setResidenceAddress(employeeResidenceAddress);
        esdto.setCertifications(employeeCertification);
        esdto.setEducation(employeeEducation);
        esdto.setCareer(employeeCareer);
        // 직원 특수 정보 등록 DAO 메서드 호출
        employeespecinfodao.insert(esdto);
    }

    // 직원 정보를 삭제하는 메서드
    private void deleteEmployee() {
        System.out.println("직원 정보 삭제 기능입니다.");
        System.out.println("삭제할 직원의 직원 코드를 입력하세요");
        String delEmpCode = Scan.nextLine();  // 삭제할 직원 코드 입력
        employeedao.delete(delEmpCode);  // 직원 정보 삭제 DAO 메서드 호출
    }

    // 직원 정보를 수정하는 메서드
    private void modifyEmployee() {
        employeeDTO edto = new employeeDTO();
        System.out.println("직원 정보 수정 기능입니다.");
        System.out.println("수정을 원하는 직원의 직원 코드를 입력하세요");
        String modWantEmployeeCode = Scan.nextLine();  // 수정할 직원 코드 입력
        System.out.println("수정할 직책을 입력하세요");
        String modPosition = Scan.nextLine();  // 수정할 직책 입력
        System.out.println("수정할 급여를 입력하세요");
        double modSalary = Scan.nextDouble();  // 수정할 급여 입력
        Scan.nextLine();  // 버퍼 비우기
        System.out.println("수정할 이메일을 입력하세요");
        String modEmail = Scan.nextLine();  // 수정할 이메일 입력
        System.out.println("수정할 근무 상태를 입력하세요");
        String modEmpStatus = Scan.nextLine();  // 수정할 근무 상태 입력
        edto.setPosition(modPosition);
        edto.setSalary(modSalary);
        edto.setEmail(modEmail);
        edto.setEmploymentStatus(modEmpStatus);
        edto.setEmployeeCode(modWantEmployeeCode);
        employeedao.update(edto);  // 직원 정보 수정 DAO 메서드 호출
    }

    // 직원 정보 및 특수 정보를 전체 조회하는 메서드
    private void detailViewEmployee() {
        System.out.println("직원 정보 및 특수 정보 전체보기 기능입니다.");
        ArrayList<employeeDTO> elist = employeedao.selectAll();  // 전체 직원 정보 조회
        for (employeeDTO e : elist) {
            e.prt();  // 직원 정보 출력
            employeeSpecInfoDTO specInfo = employeespecinfodao.selectSpecInfo(e.getEmployeeCode());  // 특수 정보 조회
            if (specInfo != null) {
                specInfo.prt();  // 특수 정보 출력
            } else {
                System.out.println("특수 정보가 없습니다.");  // 특수 정보가 없을 경우
            }
        }
    }

    // 특정 직원 정보를 조회하는 메서드
    private void lookUpEmployee() {
        System.out.println("특정 직원 정보 보기 기능입니다.");
        System.out.println("조회를 원하는 직원의 직원 코드를 입력하세요");
        String specEmployeeCode = Scan.nextLine();  // 조회할 직원 코드 입력
        // 해당 직원 코드에 대한 정보 조회
        ArrayList<employeeDTO> employee = employeedao.selectSpecAll(specEmployeeCode);

        if (!employee.isEmpty()) {  // 비어있지 않은지 확인
            // ArrayList의 요소에 대해 prt() 호출
            for (employeeDTO emp : employee) {
                emp.prt();  // 각 직원 정보 출력
            }

            // 특수 정보 조회
            employeeSpecInfoDTO specInfo = employeespecinfodao.selectSpecInfo(specEmployeeCode);
            if (specInfo != null) {
                specInfo.prt();  // 특수 정보 출력
            } else {
                System.out.println("특수 정보가 없습니다.");  // 특수 정보가 없을 경우
            }
        } else {
            System.out.println("입력한 직원 코드에 해당하는 직원 정보가 없습니다.");  // 직원 정보가 없을 경우
        }
    }
}
