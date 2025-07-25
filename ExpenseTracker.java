import java.sql.*;
import java.util.*;
import java.io.*;

public class ExpenseTracker {
    public static void main(String[] args) {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("dbconfig.properties"));

            Connection conn = DriverManager.getConnection(
                props.getProperty("url"),
                props.getProperty("username"),
                props.getProperty("password")
            );

            ExpenseDAO dao = new ExpenseDAO(conn);
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n1. Add Expense/Income");
                System.out.println("2. View All Transactions");
                System.out.println("3. View Summary");
                System.out.println("4. Exit");
                System.out.print("Enter choice: ");
                int ch = sc.nextInt();
                sc.nextLine();

                if (ch == 1) {
                    System.out.print("Enter type (income/expense): ");
                    String type = sc.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Enter description: ");
                    String desc = sc.nextLine();
                    dao.addExpense(new Expense(type, amount, desc));
                    System.out.println("Added successfully!");
                } else if (ch == 2) {
                    List<Expense> list = dao.getAllExpenses();
                    for (Expense e : list) {
                        System.out.printf("[%d] %s - %.2f (%s) on %s\n",
                            e.getId(), e.getType(), e.getAmount(), e.getDescription(), e.getDate());
                    }
                } else if (ch == 3) {
                    double income = dao.getTotal("income");
                    double expense = dao.getTotal("expense");
                    System.out.printf("Total Income: %.2f\n", income);
                    System.out.printf("Total Expense: %.2f\n", expense);
                    System.out.printf("Balance: %.2f\n", income - expense);
                } else {
                    break;
                }
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
