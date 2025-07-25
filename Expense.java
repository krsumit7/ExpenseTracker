public class Expense {
    private int id;
    private String type;
    private double amount;
    private String description;
    private String date;

    public Expense(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    // Getters and setters here...
}
