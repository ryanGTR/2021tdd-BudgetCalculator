import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class BudgetCalculator  {

    private final IBudgetRepo repo;

    public BudgetCalculator(IBudgetRepo repo) {
        this.repo = repo;
    }

    public BigDecimal query( LocalDate start, LocalDate end) {

        List<Budget> collect = this.repo.getAll().stream().filter(
                                t -> (t.getYearMonth()).equals(start.format(DateTimeFormatter.ofPattern("yyyyMM")))
                                ).collect(Collectors.toList());
        Budget budget = collect.get(0);
        long numofDay = ChronoUnit.DAYS.between(start, end) +1;
        BigDecimal decimal = BigDecimal.valueOf(budget.getAmount())
                                .divide(BigDecimal.valueOf(YearMonth.from(start).lengthOfMonth()))
                                .multiply(BigDecimal.valueOf(numofDay));
        return decimal;
    }
}