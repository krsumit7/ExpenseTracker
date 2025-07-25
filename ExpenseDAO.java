import java.sql.*;
import java.util.*;

public class ExpenseDAO {
    private Connection conn;

    public ExpenseDAO(Connection conn) {
        this.conn = conn;
    }

    public void addExpense(Expense expense) throws SQLException {
        String sql = "INSERT INTO expenses (type, amount, description) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, expense.getType());
        stmt.setDouble(2, expense.getAmount());
        stmt.setString(3, expense.getDescription());
        stmt.executeUpdate();
    }

    public List<Expense> getAllExpenses() throws SQLException {
        List<Expense> list = new ArrayList<>();
        String sql = "SELECT * FROM expenses";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Expense e = new Expense(rs.getString("type"), rs.getDouble("amount"), rs.getString("description"));
            e.setId(rs.getInt("id"));
            e.setDate(rs.getString("date"));
            list.add(e);
        }
        return list;
    }

    public double getTotal(String type) throws SQLException {
        String sql = "SELECT SUM(amount) FROM expenses WHERE type=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, type);
        ResultSet rs = stmt.executeQuery();
        return rs.next() ? rs.getDouble(1) : 0.0;
    }
}
