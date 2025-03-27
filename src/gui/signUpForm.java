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
import dto.departmentAdminAccountDTO;
import dto.integratedAdminAccountDTO;
import main.departmentAccount;
import main.integratedAccount;

public class signUpForm extends JDialog {
    private integratedAdminAccountDAO iadao;
    private departmentAdminAccountDAO dadao;
    private departmentAccount dAccount;
    private integratedAccount iAccount;
    private JRadioButton integratedAdminButton;
    private JRadioButton departmentAdminButton;
    private RoundButton submitButton;
    private RoundButton cancelButton;
    private RoundButton showPasswordButton;
    private RoundButton checkIdButton;
    private ButtonGroup group;
    private JLabel idLabel;
    private JLabel idCheckResultLabel;
    private JLabel passwordLabel;
    private JLabel confirmPassword;
    private JLabel labelTitle;
    private JTextField idField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private boolean isPasswordVisible = false;

    public signUpForm(loginForm owner, integratedAdminAccountDAO iadao, departmentAdminAccountDAO dadao) {
        super(owner, "Sign Up", true);
        this.iadao = iadao;
        this.dadao = dadao;
        try {
            System.out.println("signUpForm 생성자 시작");
            initializeAccountsAsync();
            initComponents();
            addListeners();
            setDisplay();
            showFrame();
            System.out.println("signUpForm 생성자 완료");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "다이얼로그 생성 중 오류 발생: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeAccountsAsync() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                System.out.println("dAccount와 iAccount 초기화 시작");
                dAccount = new departmentAccount();
                iAccount = new integratedAccount();
                System.out.println("dAccount와 iAccount 초기화 완료");
                return null;
            }

            @Override
            protected void done() {
                try {
                    get(); // 예외를 확인하기 위해 호출
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(signUpForm.this, "계정 객체 초기화 실패: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void initComponents() {
        System.out.println("initComponents 시작");
        Dimension labelSize = new Dimension(80, 50);
        Dimension buttonSize = new Dimension(100, 25);
        int textFieldSize = 10;

        labelTitle = new JLabel("회원가입 정보란");

        idLabel = new JLabel("아이디");
        idLabel.setPreferredSize(labelSize);
        idLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        passwordLabel = new JLabel("비밀번호");
        passwordLabel.setPreferredSize(labelSize);
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

        confirmPassword = new JLabel("비밀번호 확인");
        confirmPassword.setPreferredSize(labelSize);
        confirmPassword.setFont(new Font("SansSerif", Font.BOLD, 12));

        idField = new JTextField("사용할 아이디를 입력하세요", textFieldSize);
        idField.setForeground(Color.GRAY);
        idField.setFont(new Font("SansSerif", Font.BOLD, 12));

        passwordField = new JPasswordField("사용할 비밀번호를 입력하세요", textFieldSize);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar('*');
        passwordField.setFont(new Font("SansSerif", Font.BOLD, 12));

        confirmPasswordField = new JPasswordField("비밀번호를 한 번 더 입력하세요", textFieldSize);
        confirmPasswordField.setForeground(Color.GRAY);
        confirmPasswordField.setEchoChar('*');
        confirmPasswordField.setFont(new Font("SansSerif", Font.BOLD, 12));

        submitButton = new RoundButton("확인");
        submitButton.setPreferredSize(buttonSize);
        submitButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        cancelButton = new RoundButton("취소");
        cancelButton.setPreferredSize(buttonSize);
        cancelButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        checkIdButton = new RoundButton("중복 확인");
        checkIdButton.setPreferredSize(buttonSize);
        checkIdButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        // 중복 확인 결과를 표시할 JLabel 초기화
        idCheckResultLabel = new JLabel("");
        idCheckResultLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        integratedAdminButton = new JRadioButton("통합 관리자");
        integratedAdminButton.setContentAreaFilled(false);
        integratedAdminButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        departmentAdminButton = new JRadioButton("부서 관리자");
        departmentAdminButton.setContentAreaFilled(false);
        departmentAdminButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        group = new ButtonGroup();
        group.add(integratedAdminButton);
        group.add(departmentAdminButton);

        showPasswordButton = new RoundButton("표시");
        showPasswordButton.setPreferredSize(buttonSize);
        showPasswordButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        System.out.println("initComponents 완료");
    }

    private void addListeners() {
        System.out.println("addListeners 시작");
        showPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPasswordVisible = !isPasswordVisible;
                if (isPasswordVisible) {
                    passwordField.setEchoChar((char) 0);
                    confirmPasswordField.setEchoChar((char) 0);
                    showPasswordButton.setText("숨김");
                } else {
                    passwordField.setEchoChar('*');
                    confirmPasswordField.setEchoChar('*');
                    showPasswordButton.setText("표시");
                }
            }
        });

        checkIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!integratedAdminButton.isSelected() && !departmentAdminButton.isSelected()) {
                    JOptionPane.showMessageDialog(signUpForm.this, "관리자 유형을 선택하세요.", "관리자 유형 미선택", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // DB 작업을 별도 스레드에서 수행
                SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                    String id = idField.getText();
                    boolean duplicate = false;

                    @Override
                    protected Boolean doInBackground() throws Exception {
                        if (integratedAdminButton.isSelected()) {
                            duplicate = iadao.duplicateintegratedAdminId(id);
                        } else if (departmentAdminButton.isSelected()) {
                            duplicate = dadao.duplicateDepartmentAdminId(id);
                        }
                        return duplicate;
                    }

                    @Override
                    protected void done() {
                        try {
                            boolean duplicate = get();
                            if (duplicate) {
                                idCheckResultLabel.setText("중복된 ID입니다.");
                                idCheckResultLabel.setForeground(new Color(255, 0, 0)); // 빨간색
                                idField.setText("");
                            } else {
                                idCheckResultLabel.setText("사용 가능한 ID입니다.");
                                idCheckResultLabel.setForeground(new Color(0, 255, 0)); // 초록색
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            idCheckResultLabel.setText("중복 확인 중 오류 발생: " + ex.getMessage());
                            idCheckResultLabel.setForeground(new Color(255, 255, 255)); // 흰색
                        }
                    }
                };
                worker.execute();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

                // 입력값 검증
                if (id.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(signUpForm.this, "모든 정보를 입력하세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(signUpForm.this, "입력한 비밀번호가 일치하지 않습니다.", "비밀번호 오류", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!integratedAdminButton.isSelected() && !departmentAdminButton.isSelected()) {
                    JOptionPane.showMessageDialog(signUpForm.this, "관리자 유형을 선택하세요.", "선택 오류", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // DB 작업을 별도 스레드에서 수행
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        if (integratedAdminButton.isSelected()) {
                            integratedAdminAccountDTO iadto = new integratedAdminAccountDTO();
                            iadto.setIntegratedId(id);
                            iadto.setIntegratedPassword(password);
                            iadao.insert(iadto);
                        } else if (departmentAdminButton.isSelected()) {
                            departmentAdminAccountDTO dadto = new departmentAdminAccountDTO();
                            dadto.setDepartmentId(id);
                            dadto.setDepartmentPass(password);
                            dadao.insert(dadto);
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        try {
                            get();
                            if (integratedAdminButton.isSelected()) {
                                JOptionPane.showMessageDialog(signUpForm.this, "통합 관리자 계정 생성 완료!");
                            } else if (departmentAdminButton.isSelected()) {
                                JOptionPane.showMessageDialog(signUpForm.this, "부서 관리자 계정 생성 완료!");
                            }
                            dispose();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(signUpForm.this, "계정 생성 실패: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                };
                worker.execute();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idField.setText("");
                passwordField.setText("");
                confirmPasswordField.setText("");
                group.clearSelection();
                idField.requestFocus();
            }
        });

        idField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (idField.getText().equals("사용할 아이디를 입력하세요")) {
                    idField.setText("");
                    idField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (idField.getText().isEmpty()) {
                    idField.setText("사용할 아이디를 입력하세요");
                    idField.setForeground(Color.GRAY);
                }
            }
        });

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordField.getText().equals("사용할 비밀번호를 입력하세요")) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getText().isEmpty()) {
                    passwordField.setText("사용할 비밀번호를 입력하세요");
                    passwordField.setForeground(Color.GRAY);
                }
            }
        });

        confirmPasswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (confirmPasswordField.getText().equals("비밀번호를 한 번 더 입력하세요")) {
                    confirmPasswordField.setText("");
                    confirmPasswordField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (confirmPasswordField.getText().isEmpty()) {
                    confirmPasswordField.setText("비밀번호를 한 번 더 입력하세요");
                    confirmPasswordField.setForeground(Color.GRAY);
                }
            }
        });
        System.out.println("addListeners 완료");
    }

    private void setDisplay() {
        System.out.println("setDisplay 시작");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel titlePanel = new JPanel();
        labelTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        titlePanel.add(labelTitle);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        idPanel.add(idLabel);
        idPanel.add(idField);
        idPanel.add(checkIdButton);

        // 중복 확인 결과 라벨을 위한 패널 추가
        JPanel idCheckResultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        idCheckResultPanel.add(idCheckResultLabel);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        passwordPanel.add(showPasswordButton);

        JPanel confirmPasswordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        confirmPasswordPanel.add(confirmPassword);
        confirmPasswordPanel.add(confirmPasswordField);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.add(integratedAdminButton);
        radioPanel.add(departmentAdminButton);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        formPanel.add(idPanel);
        formPanel.add(idCheckResultPanel); // 중복 확인 결과 라벨 추가
        formPanel.add(passwordPanel);
        formPanel.add(confirmPasswordPanel);
        formPanel.add(radioPanel);
        formPanel.add(buttonPanel);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
        System.out.println("setDisplay 완료");
    }

    private void showFrame() {
        System.out.println("showFrame 시작");
        setTitle("Sign Up");
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        System.out.println("showFrame 완료");
    }
}