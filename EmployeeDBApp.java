package InternshipTask7;

import java.sql.*;
import java.util.Scanner;

public class EmployeeDBApp {
	static final String DRIVER = "com.mysql.cj.jdbc.Driver"; 
    static final String URL = "jdbc:mysql://localhost:3306/employee_db";
 
    static final String USER = "root";  
    static final String PASS = "radhaghodke"; 

public static void main(String[] args) {
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
         Scanner sc = new Scanner(System.in)) {

        System.out.println("Connected to database!");

        int choice;
        do {
            System.out.println("\n--- Employee DB Menu ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addEmployee(conn, sc);
                case 2 -> viewEmployees(conn);
                case 3 -> updateEmployee(conn, sc);
                case 4 -> deleteEmployee(conn, sc);
                case 5 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
private static void addEmployee(Connection conn, Scanner sc) throws SQLException {
    System.out.print("Enter name: ");
    String name = sc.nextLine();
    System.out.print("Enter email: ");
    String email = sc.nextLine();
    System.out.print("Enter department: ");
    String dept = sc.nextLine();

    String sql = "INSERT INTO employees (name, email, department) VALUES (?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, dept);
        int rows = ps.executeUpdate();
        System.out.println(rows + " employee added.");
    }
}
private static void viewEmployees(Connection conn) throws SQLException {
    String sql = "SELECT * FROM employees";
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        System.out.println("\nID\tName\tEmail\tDepartment");
        while (rs.next()) {
            System.out.println(rs.getInt("id") + "\t" +
                               rs.getString("name") + "\t" +
                               rs.getString("email") + "\t" +
                               rs.getString("department"));
        }
    }
}
private static void updateEmployee(Connection conn, Scanner sc) throws SQLException {
    System.out.print("Enter employee ID to update: ");
    int id = sc.nextInt();
    sc.nextLine();

    System.out.print("Enter new name: ");
    String name = sc.nextLine();
    System.out.print("Enter new email: ");
    String email = sc.nextLine();
    System.out.print("Enter new department: ");
    String dept = sc.nextLine();

    String sql = "UPDATE employees SET name=?, email=?, department=? WHERE id=?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, dept);
        ps.setInt(4, id);
        int rows = ps.executeUpdate();
        System.out.println(rows + " employee updated.");
    }
}

private static void deleteEmployee(Connection conn, Scanner sc) throws SQLException {
    System.out.print("Enter employee ID to delete: ");
    int id = sc.nextInt();
    String sql = "DELETE FROM employees WHERE id=?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        int rows = ps.executeUpdate();
        System.out.println(rows + " employee deleted.");
        }
    }
}
