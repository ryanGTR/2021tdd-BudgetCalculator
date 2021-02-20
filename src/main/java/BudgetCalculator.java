import static java.time.temporal.ChronoUnit.DAYS;

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
        BigDecimal decimal= BigDecimal.valueOf(0);
        long monthsBetween = ChronoUnit.MONTHS.between(
                                start.withDayOfMonth(1),
                                end.withDayOfMonth(1));
        if (monthsBetween < 1 && start.getMonthValue() == end.getMonthValue()) {
            decimal = count(start, end);
        }
        
        if (monthsBetween >= 1) {
            decimal = count(start, start.withDayOfMonth(start.lengthOfMonth()));
            for (int i = 1; i <  end.getMonthValue() - start.getMonthValue(); i++) {
                decimal = decimal.add(count(start.plusMonths(i).withDayOfMonth(1),
                                        start.plusMonths(i).withDayOfMonth(start.plusMonths(i).lengthOfMonth())));
            }
            decimal = decimal.add(count(end.withDayOfMonth(1), end));
        }
        return decimal;
    }

    private BigDecimal count(LocalDate start, LocalDate end) {
        List<Budget> collect = this.repo.getAll().stream().filter(
                                t -> (t.getYearMonth()).equals(start.format(DateTimeFormatter.ofPattern("yyyyMM")))
                                ).collect(Collectors.toList());
        long numOfDay = DAYS.between(start, end) +1;
        BigDecimal decimal = BigDecimal.valueOf(getMonthBudget(collect, 0))
                                .divide(BigDecimal.valueOf(start.lengthOfMonth()))
                                .multiply(BigDecimal.valueOf(numOfDay));
        return decimal;
    }

    private int getMonthBudget(List<Budget> collect, int index) {
        if (index >= 0 && index < collect.size())
        return collect.get(index).getAmount();

        return 0;
    }
}