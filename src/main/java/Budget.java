import java.time.YearMonth;
import java.util.List;

public class Budget implements IBudgetRepo {

    private String yearMonth;
    private int amount;

    public Budget(String yearMonth, int amount) {
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public List<Budget> getAll() {
        return null;
    }
}
