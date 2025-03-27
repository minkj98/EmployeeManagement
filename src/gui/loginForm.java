package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import dao.integratedAdminAccountDAO;
import dao.departmentAdminAccountDAO;
import gui.RoundButton;

public class loginForm extends JFrame {
    private final departmentAdminAccountDAO dadao;
    private final integratedAdminAccountDAO iadao;
    private JRadioButton integratedAdminButton;
    private JRadioButton departmentAdminButton;
    private JTextField idField;
    private JPasswordField passwordField;
    private RoundButton loginButton;
    private RoundButton signUpButton;
    private RoundButton showPasswordButton;
    private ButtonGroup group;
    private JLabel labelId;
    private JLabel labelPassword;
    private final int width = 1000;
    private final int height = 800;
    private final int x = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
    private final int y = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
    private boolean isPasswordVisible = false;

    public loginForm() {
        dadao = new departmentAdminAccountDAO();
        iadao = new integratedAdminAccountDAO();
        initComponents();
        setDisplay();
        addListener();
        showFrame();
    }

    private void addListener() {
        showPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPasswordVisible = !isPasswordVisible;
                if (isPasswordVisible) {
                    passwordField.setEchoChar((char) 0);
                    showPasswordButton.setText("숨김");
                } else {
                    passwordField.setEchoChar('*');
                    showPasswordButton.setText("표시");
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
                    return;
                }

                if (integratedAdminButton.isSelected()) {
                    loginSuccess = iadao.checkIntegratedAdminAccount(id, password);
                } else if (departmentAdminButton.isSelected()) {
                    loginSuccess = dadao.checkDepartmentAdminAccount(id, password);
                }

                if (loginSuccess) {
                    JOptionPane.showMessageDialog(null, "로그인 성공");
                    dispose();
                    if (integratedAdminButton.isSelected()) {
                        new integratedAdminForm();
                    } else {
                        new departmentAdminForm();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호가 틀렸습니다.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        idField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (idField.getText().equals("아이디를 입력하세요")) {
                    idField.setText("");
                    idField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (idField.getText().isEmpty()) {
                    idField.setText("아이디를 입력하세요");
                    idField.setForeground(Color.GRAY);
                }
            }
        });
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordField.getText().equals("비밀번호를 입력하세요")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getText().isEmpty()) {
                    passwordField.setText("비밀번호를 입력하세요");
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        System.out.println("signUpForm 생성 시작");
                        new signUpForm(loginForm.this, iadao, dadao);
                        System.out.println("signUpForm 생성 완료");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(loginForm.this, "회원가입 창 생성 실패: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
                    }
                });
            }
        });


    }

    private void initComponents() {
        // 레이블의 크기를 설정
        Dimension labelSize = new Dimension(80, 50);
        // 텍스트 필드의 크기를 설정 (너비)
        int textFieldSize = 10;
        // 버튼의 크기를 설정
        Dimension buttonSize = new Dimension(100, 25);

        // 아이디 레이블 생성 및 크기 설정
        labelId = new JLabel("아이디");
        labelId.setPreferredSize(labelSize);
        labelId.setFont(new Font("SansSerif", Font.BOLD, 14));

        // 비밀번호 레이블 생성 및 크기 설정
        labelPassword = new JLabel("비밀번호");
        labelPassword.setPreferredSize(labelSize);
        labelPassword.setFont(new Font("SansSerif", Font.BOLD, 14));

        // 아이디 입력 필드 생성, 기본 텍스트와 크기 설정
        idField = new JTextField("아이디를 입력하세요", textFieldSize);
        idField.setForeground(Color.GRAY); // 텍스트 색상을 회색으로 설정

        // 비밀번호 입력 필드 생성, 기본 텍스트와 크기 설정
        passwordField = new JPasswordField("비밀번호를 입력하세요", textFieldSize);
        passwordField.setForeground(Color.GRAY); // 텍스트 색상을 회색으로 설정
        passwordField.setEchoChar('*'); // 비밀번호 입력 시 '*'로 표시

        // 로그인 버튼 생성 및 크기 설정
        loginButton = new RoundButton("로그인");
        loginButton.setPreferredSize(buttonSize);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 12));

        // 회원가입 버튼 생성 및 크기 설정
        signUpButton = new RoundButton("회원가입");
        signUpButton.setPreferredSize(buttonSize);
        signUpButton.setFont(new Font("SansSerif", Font.BOLD, 12));

        // 라디오 버튼 생성 (부서 관리자)
        departmentAdminButton = new JRadioButton("부서 관리자");
        departmentAdminButton.setContentAreaFilled(false);
        departmentAdminButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        // 라디오 버튼 생성 (통합 관리자)
        integratedAdminButton = new JRadioButton("통합 관리자");
        integratedAdminButton.setContentAreaFilled(false);
        integratedAdminButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        // 라디오 버튼 그룹 생성
        group = new ButtonGroup();
        // 라디오 버튼을 그룹에 추가하여 한 번에 하나만 선택 가능하도록 설정
        group.add(integratedAdminButton);
        group.add(departmentAdminButton);

        // 비밀번호 표시 버튼 생성 및 크기 설정
        showPasswordButton = new RoundButton("표시");
        showPasswordButton.setPreferredSize(new Dimension(60, 25));
        showPasswordButton.setFont(new Font("SansSerif", Font.BOLD, 12));
    }

    private void setDisplay() {
        // 백그라운드 이미지를 설정하는 JPanel 생성
        JPanel backgroundPanel = new JPanel() {
            // 배경 이미지 로드 (파일 경로: "C:/Users/user/Desktop/departEmployee/src/image/employee.png")
            private Image backgroundImage = new ImageIcon("C:/Users/user/Desktop/departEmployee/src/image/employee.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // 부모 클래스의 기본 그리기 메서드 호출
                // 배경 이미지를 패널 크기에 맞게 그리기
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                }
            }
        };

        // 배경 패널의 레이아웃을 BorderLayout으로 설정 (컴포넌트 정렬을 위함)
        backgroundPanel.setLayout(new BorderLayout());

        // JFrame의 ContentPane을 위에서 만든 배경 패널로 설정
        setContentPane(backgroundPanel);

        // 컴포넌트들을 배치할 패널들 생성
        FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT); // 왼쪽 정렬 FlowLayout 사용

        JPanel pnlNorth = new JPanel(new GridBagLayout()); // 상단 패널 (아이디, 비밀번호 입력란 배치)
        pnlNorth.setOpaque(false); // 패널을 투명하게 설정

        // GridBagConstraints 객체 생성 (컴포넌트 배치 조절)
        GridBagConstraints gbc = new GridBagConstraints();

        // 컴포넌트가 가로로 확장되도록 설정 (HORIZONTAL)
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 컴포넌트 간의 여백 설정 (상, 좌, 하, 우 - 5px)
        gbc.insets = new Insets(5, 5, 5, 5);

        // 아이디 입력 필드 정렬
        // 아이디 라벨(labelId) 위치 설정 (첫 번째 열, 첫 번째 행)
        gbc.gridx = 0;  // 열 위치 (첫 번째 열)
        gbc.gridy = 0;  // 행 위치 (첫 번째 행)
        pnlNorth.add(labelId, gbc); // 패널(pnlNorth)에 추가

        // 아이디 입력 필드(idField) 위치 설정 (두 번째 열, 첫 번째 행)
        // gridwidth = 2 → 두 개의 열을 차지하도록 설정
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 2개의 열을 차지하도록 설정 (텍스트 필드가 넓어짐)
        pnlNorth.add(idField, gbc);

        // 비밀번호 입력 필드 정렬
        // 비밀번호 라벨(labelPassword) 위치 설정 (첫 번째 열, 두 번째 행)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // 기본값 (한 칸만 차지)
        pnlNorth.add(labelPassword, gbc);

        // 비밀번호 입력 필드(passwordField) 위치 설정 (두 번째 열, 두 번째 행)
        gbc.gridx = 1;
        gbc.gridy = 1;
        pnlNorth.add(passwordField, gbc);

        // 비밀번호 표시 버튼(showPasswordButton) 위치 설정 (세 번째 열, 두 번째 행)
        gbc.gridx = 2;
        gbc.gridy = 1;
        pnlNorth.add(showPasswordButton, gbc);

        // 로그인 및 회원가입 버튼을 포함하는 패널 생성
        JPanel pnlSouth = new JPanel();
        pnlSouth.setOpaque(false);
        pnlSouth.add(loginButton); // 로그인 버튼 추가
        pnlSouth.add(signUpButton); // 회원가입 버튼 추가

        // 관리자 선택 라디오 버튼을 포함하는 패널 생성
        JPanel pnlCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlCenter.setOpaque(false);
        pnlCenter.add(integratedAdminButton); // 통합 관리자 버튼 추가
        pnlCenter.add(departmentAdminButton); // 부서 관리자 버튼 추가
        pnlCenter.setBorder(new EmptyBorder(5, 0, 5, 0)); // 패딩 설정 (위, 왼쪽, 아래, 오른쪽)

        // 상단 패널의 좌우 여백 설정
        pnlNorth.setBorder(new EmptyBorder(0, 20, 0, 20));

        // 로그인/회원가입 버튼 패널의 하단 여백 설정
        pnlSouth.setBorder(new EmptyBorder(0, 0, 10, 0));

        // 배경 패널에 각 패널을 배치
        backgroundPanel.add(pnlNorth, BorderLayout.NORTH); // 아이디 및 비밀번호 입력 패널 (상단)
        backgroundPanel.add(pnlCenter, BorderLayout.CENTER); // 관리자 선택 패널 (가운데)
        backgroundPanel.add(pnlSouth, BorderLayout.SOUTH); // 로그인 및 회원가입 버튼 패널 (하단)
    }



    private void showFrame() {
        setTitle("Login");
        pack();
        setResizable(true);
        setBounds(x, y, width, height);
        setVisible(true);
       // setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}