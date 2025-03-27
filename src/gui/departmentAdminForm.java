package gui;
import javax.swing.*;
import dao.employeeDAO;
import dto.employeeDTO;
import java.awt.*;

public class departmentAdminForm extends JFrame {
    private JTextField employeeCode;
    private JTextField employeeName;
    private JTextField position;
    private JTextField salary;
    private JTextField departmentCode;
    private JTextField hireYear;
    private JTextField email;
    private JTextField employmentStatus;
    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton selectButton;
    private JComboBox<String> departmenCodeComboBox;
    private String[] deptCode = {"dev", "hr", "mkt", "fin", "sal", "sup"};
    private JTable resultTable;
    private int width = 1000;
    private int height = 800;
    private int x = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
    private int y = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;





}