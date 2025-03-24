package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import dao.integratedAdminAccountDAO;
import dao.departmentAdminAccountDAO;
import dto.departmentAdminAccountDTO;
import dto.integratedAdminAccountDTO;

public class loginForm extends JFrame {
    private departmentAdminAccountDTO dadto;
    private integratedAdminAccountDTO iadto;
    private departmentAdminAccountDAO dadao;
    private integratedAdminAccountDAO iadao;

    private JRadioButton integratedAdminButton;
    private JRadioButton departmentAdminButton;
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;
    private JButton showPasswordButton;  // 비밀번호 표시/숨김 버튼
    private ButtonGroup group;
    private JLabel labelId;
    private JLabel labelPassword;

    private boolean isPasswordVisible = false; // 비밀번호 표시 상태 변수

    loginForm() {
        dadao = new departmentAdminAccountDAO();
        iadao = new integratedAdminAccountDAO();
        initComponents();
        setDisplay();
        addListener();
        showFrame();
    }

    private void addListener() {
        // 비밀번호 표시/숨김 버튼 클릭 이벤트
        showPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPasswordVisible = true;
                if (isPasswordVisible) {
                    passwordField.setEchoChar((char) 0);  // 비밀번호 표시
                    showPasswordButton.setText("숨김"); // 버튼 텍스트 변경
                } else {
                    passwordField.setEchoChar('*'); // 비밀번호 숨김
                    showPasswordButton.setText("표시"); // 버튼 텍스트 변경
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String password = new String(passwordField.getPassword());

                boolean loginSuccess = false;
                if (!integratedAdminButton.isSelected() && !departmentAdminButton.isSelected()) {
                    JOptionPane.showMessageDialog(null, "관리자 유형을 선택하세요.", "관리자 유형 미선택", JOptionPane.WARNING_MESSAGE);
                    return; // 라디오 버튼이 선택되지 않으면 관리자 유형을 선택하라는 문구를 띄움
                }

                // 로그인 방식 결정
                if (integratedAdminButton.isSelected()) {
                    loginSuccess = iadao.checkIntegratedAdminAccount(id, password);
                } else if (departmentAdminButton.isSelected()) {
                    loginSuccess = dadao.checkDepartmentAdminAccount(id, password);
                }

                // 로그인 성공 여부에 따라 처리
                if (loginSuccess) {
                    JOptionPane.showMessageDialog(null, "로그인 성공");
                    // 로그인 성공 후 해당 관리자 화면으로 전환
                    if (integratedAdminButton.isSelected()) {
                        setVisible(false);  // 현재 로그인 화면을 숨긴다.
                        new integratedAdminForm();  // 통합 관리자 화면 열기
                    } else if (departmentAdminButton.isSelected()) {
                        setVisible(false);  // 현재 로그인 화면 숨긴다.
                        new departmentAdminForm();  // 부서 관리자 화면 열기
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호가 틀렸습니다.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void initComponents() {
        Dimension labelSize = new Dimension(80, 30);
        int textFieldSize = 10;
        Dimension buttonSize = new Dimension(100, 25);

        labelId = new JLabel("아이디");
        labelId.setPreferredSize(labelSize);

        labelPassword = new JLabel("비밀번호");
        labelPassword.setPreferredSize(labelSize);

        idField = new JTextField(textFieldSize);
        passwordField = new JPasswordField(textFieldSize);
        passwordField.setEchoChar('*');

        loginButton = new JButton("Login");
        loginButton.setPreferredSize(buttonSize);

        signUpButton = new JButton("Sign Up");
        signUpButton.setPreferredSize(buttonSize);

        departmentAdminButton = new JRadioButton("부서 관리자");
        integratedAdminButton = new JRadioButton("통합 관리자");
        //integratedAdminButton.setSelected(true);

        group = new ButtonGroup();
        group.add(integratedAdminButton);
        group.add(departmentAdminButton);

        // 비밀번호 표시/숨김 버튼 생성
        showPasswordButton = new JButton("표시");
        showPasswordButton.setPreferredSize(new Dimension(60, 25));
    }

    private void setDisplay() {
        FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT);

        JPanel pnlNorth = new JPanel(new GridLayout(0, 1));

        JPanel pnlId = new JPanel(flowLeft);
        pnlId.add(labelId);
        pnlId.add(idField);

        JPanel pnlPw = new JPanel(flowLeft);
        pnlPw.add(labelPassword);
        pnlPw.add(passwordField);
        pnlPw.add(showPasswordButton);  // 비밀번호 표시/숨김 버튼 추가

        pnlNorth.add(pnlId);
        pnlNorth.add(pnlPw);

        JPanel pnlSouth = new JPanel();
        pnlSouth.add(loginButton);
        pnlSouth.add(signUpButton);

        JPanel pnlSouth2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlSouth2.add(integratedAdminButton);
        pnlSouth2.add(departmentAdminButton);
        pnlSouth2.setBorder(new EmptyBorder(5, 0, 5, 0));

        pnlNorth.setBorder(new EmptyBorder(0, 20, 0, 20));
        pnlSouth.setBorder(new EmptyBorder(0, 0, 10, 0));

        add(pnlNorth, BorderLayout.NORTH);
        add(pnlSouth2, BorderLayout.CENTER);
        add(pnlSouth, BorderLayout.SOUTH);
    }

    private void showFrame() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setTitle("Login");
                setLocationRelativeTo(null);
                pack();
                setResizable(true);
                setVisible(true);
            }
        });
    }
}
