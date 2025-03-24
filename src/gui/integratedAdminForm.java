package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import dao.departmentDAO;
import dto.departmentDTO;
public class integratedAdminForm extends JFrame{
    private JTextField departmentCodeField, departmentNameField;
    private JTextArea resultArea;
    private JButton insertButton, updateButton, deleteButton, selectAllButton;
    private departmentDAO deptDAO;

    public integratedAdminForm() {
        deptDAO = departmentDAO.getInstance(); // departmentDAO 객체 생성

        // 기본 설정
        setTitle("부서 관리 시스템");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // UI 구성
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel departmentCodeLabel = new JLabel("부서 코드");
        departmentCodeField = new JTextField();
        JLabel departmentNameLabel = new JLabel("부서 이름");
        departmentNameField = new JTextField();

        insertButton = new JButton("부서 추가");
        updateButton = new JButton("부서 수정");
        deleteButton = new JButton("부서 삭제");
        selectAllButton = new JButton("모든 부서 조회");

        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);

        panel.add(departmentCodeLabel);
        panel.add(departmentCodeField);
        panel.add(departmentNameLabel);
        panel.add(departmentNameField);
        panel.add(insertButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(selectAllButton);

        // 버튼 이벤트 처리
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertDepartment();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateDepartment();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteDepartment();
            }
        });

        selectAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectAllDepartments();
            }
        });

        // 화면에 추가
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(resultArea), BorderLayout.CENTER);
        setVisible(true);
    }

    // 부서 추가
    private void insertDepartment() {
        String departmentCode = departmentCodeField.getText();
        String departmentName = departmentNameField.getText();
        departmentDTO dto = new departmentDTO();
        dto.setDepartmentCode(departmentCode);
        dto.setDepartmentName(departmentName);

        deptDAO.insert(dto);
        resultArea.setText("부서 추가 완료: " + departmentCode + " - " + departmentName);
    }

    // 부서 수정
    private void updateDepartment() {
        String departmentCode = departmentCodeField.getText();
        String departmentName = departmentNameField.getText();

        deptDAO.update(departmentCode, departmentName);
        resultArea.setText("부서 수정 완료: " + departmentCode + " - " + departmentName);
    }

    // 부서 삭제
    private void deleteDepartment() {
        String departmentCode = departmentCodeField.getText();

        deptDAO.delete(departmentCode);
        resultArea.setText("부서 삭제 완료: " + departmentCode);
    }

    // 모든 부서 조회
    private void selectAllDepartments() {
        ArrayList<departmentDTO> departments = deptDAO.selectAll();
        StringBuilder sb = new StringBuilder();
        for (departmentDTO dept : departments) {
            sb.append("부서 코드: ").append(dept.getDepartmentCode()).append(", 부서 이름: ").append(dept.getDepartmentName()).append("\n");
        }
        resultArea.setText(sb.toString());
    }
}