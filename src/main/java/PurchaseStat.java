import java.io.Serializable;

public class PurchaseStat implements Serializable {
    private String category;
    private String date;
    private int sum;

    public void incrementSum(int sum) {
        this.sum += sum;
    }

    public PurchaseStat(String category, String date, int sum) {
        this.category = category;
        this.date = date;
        this.sum = sum;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public int getSum() {
        return sum;
    }
}