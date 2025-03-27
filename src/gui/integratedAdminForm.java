package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import dao.departmentDAO;
import dto.departmentDTO;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

public class integratedAdminForm extends JFrame {
    private JComboBox<String> departmenCodeComboBox;
    private JComboBox<String> departmentNameComboBox;
    private JTextField departmentCodeField;
    private JTextField departmentNameField;
    private JTable resultTable; // JTextArea 대신 JTable 사용
    private DefaultTableModel tableModel; // 테이블 데이터 모델
    private JButton insertButton;
    private JButton deleteButton;
    private JButton selectAllButton;
    private final departmentDAO deptDAO;
    private int width = 1000;
    private int height = 800;
    private int x = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
    private int y = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
    private String[] deptCode = {"dev", "hr", "mkt", "fin", "sal", "sup"};
    private String[] deptName = {"개발팀", "인사팀", "마케팅팀", "재무팀", "급여팀", "고객지원팀"};

    public integratedAdminForm() {
        deptDAO = departmentDAO.getInstance();
        initComponents();
        addListener();
        showFrame();
    }

    private void addListener() {
        //update는 존재 필요가 없다.
        insertButton.addActionListener(e -> insertDepartment());
        deleteButton.addActionListener(e -> deleteDepartment());
        selectAllButton.addActionListener(e -> selectAllDepartments());
    }

    private void insertDepartment() {
        try {
            int codeIndex = departmenCodeComboBox.getSelectedIndex();
            int nameIndex = departmentNameComboBox.getSelectedIndex();

            if (codeIndex != nameIndex) {
                JOptionPane.showMessageDialog(this, "잘못된 선택입니다. 부서 코드와 이름이 일치해야 합니다.", "선택 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String departmentCode = deptCode[codeIndex];
            String departmentName = deptName[nameIndex];

            // 부서 코드 중복 확인
            if (deptDAO.duplicateDepartmentCode(departmentCode)) {
                JOptionPane.showMessageDialog(this, "이미 존재하는 부서 코드입니다: " + departmentCode, "중복 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }

            departmentDTO dto = new departmentDTO();
            dto.setDepartmentCode(departmentCode);
            dto.setDepartmentName(departmentName);
            deptDAO.insert(dto);

            JOptionPane.showMessageDialog(this, "부서 추가 완료: " + departmentCode + " - " + departmentName);
            selectAllDepartments();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "데이터베이스 오류로 부서 추가 실패: " + e.getMessage(), "데이터베이스 오류", JOptionPane.ERROR_MESSAGE);
            System.out.println("데이터베이스 오류: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "부서 추가 실패: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            System.out.println("부서 추가 중 오류 발생: " + e.getMessage());
        }
    }

    private void deleteDepartment() {
        try {
            int selectRow = resultTable.getSelectedRow();
            if (selectRow != -1) {
                String departmentCode = (String) tableModel.getValueAt(selectRow, 0);
                deptDAO.delete(departmentCode); // 데이터베이스에서 부서 삭제
                JOptionPane.showMessageDialog(this, "부서 삭제 완료: " + departmentCode);
                selectAllDepartments(); // 테이블 갱신
            } else {
                JOptionPane.showMessageDialog(this, "삭제할 부서의 행을 선택하세요");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "부서 삭제 실패: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            System.out.println("부서 삭제 중 오류 발생: " + e.getMessage());
        }
    }


    private void selectAllDepartments() {
        try {
            ArrayList<departmentDTO> departments = deptDAO.selectAll();
            tableModel.setRowCount(0); // 기존 데이터 지우기
            for (departmentDTO dept : departments) {
                tableModel.addRow(new String[]{
                        dept.getDepartmentCode(),
                        dept.getDepartmentName()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "부서 조회 실패: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showFrame() {
        setTitle("부서 관리 시스템");
        setResizable(true);
        setBounds(x, y, width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        JLabel departmentCodeLabel = new JLabel("부서 코드");
        departmenCodeComboBox = new JComboBox<>(deptCode);
        JLabel departmentNameLabel = new JLabel("부서 이름");
        departmentNameComboBox = new JComboBox<>(deptName);
        insertButton = new JButton("부서 추가");
        deleteButton = new JButton("부서 삭제");
        selectAllButton = new JButton("모든 부서 조회");

        String[] columnNames = {"부서 코드", "부서 이름"};
        tableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(tableModel);
        resultTable.setFillsViewportHeight(true);

        panel.add(departmentCodeLabel);
        panel.add(departmenCodeComboBox);
        panel.add(departmentNameLabel);
        panel.add(departmentNameComboBox);
        panel.add(insertButton);
        panel.add(deleteButton);
        panel.add(selectAllButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(resultTable), BorderLayout.CENTER); // JTable을 JScrollPane에 추가
    }
}