import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Employee{
     static final String URL = "jdbc:mysql://localhost:3306/Empdb";
     static final String USERNAME = "root";
     static final String PASS = "harish8096";

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(URL, USERNAME, PASS);
    }
    void empadd(String name, String job, double salary) {
        String que = "INSERT INTO emp (name, job, salary) VALUES ('" + name + "', '" + job + "', " + salary + ")";
        try (Connection connection = Employee.getConnection();
             PreparedStatement stmt = connection.prepareStatement(que)) {
            stmt.executeUpdate();
            System.out.println("Employee added successfully to db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void getemp(int emp_id) {
        String que = "SELECT * FROM emp WHERE emp_id = ?";
        try (Connection connection = Employee.getConnection();
             PreparedStatement stmt = connection.prepareStatement(que)) {
            stmt.setInt(1, emp_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("emp_ID: " + rs.getInt("emp_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("job: " + rs.getString("job"));
                System.out.println("Salary: " + rs.getDouble("salary"));
            } else {
                System.out.println("Employee not found in db");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void empupd(int emp_id, String name, String job, double salary) {
        String que = "UPDATE emp SET name = '" + name + "', job = '" + job + "', salary = " + salary + " WHERE emp_id = " + emp_id;
        try (Connection connection = Employee.getConnection();
             PreparedStatement stmt = connection.prepareStatement(que)) {
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee updated successfully in db");
            } else {
                System.out.println("Employee not found in db");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void deleteEmployee(int emp_id) {
        String que = "DELETE FROM emp WHERE emp_id = " + emp_id;
        try (Connection connection = Employee.getConnection();
             PreparedStatement stmt = connection.prepareStatement(que)) {
            
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Employee deleted successfully from db");
            } else {
                System.out.println("Employee not found in db ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Employee crud = new Employee();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Add Employee");
            System.out.println("2. Get Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");

            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter job: ");
                    String job = sc.nextLine();
                    System.out.print("Enter salary: ");
                    double salary = sc.nextDouble();
                    crud.empadd(name, job, salary);
                    break;
                case 2:
                    System.out.print("Enter employee emp_ID: ");
                    int emp_id = sc.nextInt();
                    crud.getemp(emp_id);
                    break;
                case 3:
                    System.out.print("Enter employee emp_ID: ");
                    int updateemp_Id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new name: ");
                    String newName = sc.nextLine();
                    System.out.print("Enter new job: ");
                    String newjob = sc.nextLine();
                    System.out.print("Enter new salary: ");
                    double newSalary = sc.nextDouble();
                    crud.empupd(updateemp_Id, newName, newjob, newSalary);
                    break;
                case 4:
                    System.out.print("Enter employee emp_ID: ");
                    int deleteemp_Id = sc.nextInt();
                    crud.deleteEmployee(deleteemp_Id);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

