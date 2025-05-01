package bank.management.system;

import java.sql.*;

public class Connect {
    public Connection connection;
    public Statement statement;

    public Connect() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/banksystem", "root", "#kunal@4158"
            );

            statement = connection.createStatement();
            System.out.println("✅ Connected to database successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL JDBC Driver not found.");
            e.printStackTrace();
            throw new RuntimeException("Driver load failed.");
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed.");
            e.printStackTrace();
            throw new RuntimeException("DB connection failed.");
        }
    }
}