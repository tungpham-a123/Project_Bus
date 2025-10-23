/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.schoolname.schoolbusmanagementsystem.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBContext {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=SchoolBusDB;encrypt=false";
    private static final String USER = "tung";
    private static final String PASSWORD = "123"; 

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    public static void main(String[] args) {
        try (Connection conn = DBContext.getConnection()) {
            if (conn != null) {
                System.out.println("Kết nối CSDL thành công!");
            } else {
                System.out.println("Kết nối CSDL thất bại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
