package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public  class superDAO {
    private  String driver = "oracle.jdbc.driver.OracleDriver";
    private static String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static String id = "system";
    private static String pass = "oracle";
    static Connection conn = null;

    superDAO() {
        try {
            Class.forName(driver);
            System.out.println("클래스 load 성공");

        } catch (ClassNotFoundException e) {
            System.out.println("클래스 load 실패");
            e.printStackTrace();

        }
    }

    static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url, id, pass);
            System.out.println("연결 성공");
        } catch (Exception e) {
            System.out.println("연결 오류");
            conn = null;
        }
        return conn;
    }
}
