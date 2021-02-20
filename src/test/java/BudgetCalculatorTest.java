import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BudgetCalculatorTest {

    private final List<Budget> list = new ArrayList<Budget>() {{
        add(new Budget( "202101", 31));
        add(new Budget( "202102", 28*20));
        add(new Budget( "202103", 31*30));
        add(new Budget( "202112", 31*120));
        add(new Budget( "202201", 31*130));
    }};

    @Test
    public void period_of_Day_Budget() {
        BigDecimal result = givenBudgetDateBetween("20210101","20210312");
        budgetShouldBe(BigDecimal.valueOf(31+28*20+30*12), result);
    }

    @Test
    public void SingleDay_Budget() {
        BigDecimal result = givenBudgetDateBetween("20210101","20210101");
        budgetShouldBe(BigDecimal.valueOf(1), result);
    }

    @Test
    public void SingleMonth_Budget() {
        BigDecimal result = givenBudgetDateBetween("20210101","20210131");
        budgetShouldBe(BigDecimal.valueOf(31), result);
    }

    @Test
    public void Cross2Month_Budget() {
        BigDecimal singleDayBuget = givenBudgetDateBetween("20210131","20210201");
        budgetShouldBe(BigDecimal.valueOf(1+20), singleDayBuget);
    }

    @Test
    public void Cross3Month_Budget() {
        BigDecimal result = givenBudgetDateBetween("20210131","20210301");
        budgetShouldBe(BigDecimal.valueOf(1+28*20+30), result);
    }

    @Test
    public void Cross2Year_Budget() {
        BigDecimal result = givenBudgetDateBetween("20211231","20220101");
        budgetShouldBe(BigDecimal.valueOf(120+130), result);
    }

    @Test
    public void None_Budget() {
        BigDecimal result = givenBudgetDateBetween("20481231","20481231");
        budgetShouldBe(BigDecimal.valueOf(0), result);
    }

    @Test
    public void partial_None_Budget() {
        BigDecimal result = givenBudgetDateBetween("20220101","20481231");
        budgetShouldBe(BigDecimal.valueOf(31*130), result);
    }

    @Test
    public void StartDay_biggerThan_EndDay() {
        BigDecimal result = givenBudgetDateBetween("99990101","20481231");
        budgetShouldBe(BigDecimal.valueOf(0), result);
    }



    @Test
    public void StringToLocalDate() {
        LocalDate localDate = getLocalDate("20210101");
        assertEquals("2021-01-01", localDate.toString());
    }

    private LocalDate getLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate;
    }

    private BigDecimal givenBudgetDateBetween(String startDate, String endDate) {
        IBudgetRepo repo = mock(IBudgetRepo.class);
        Mockito.when(repo.getAll()).thenReturn(list);
        return new BudgetCalculator(repo)
                                .query(getLocalDate(startDate), getLocalDate(endDate));
    }

    private void budgetShouldBe(BigDecimal expected,BigDecimal result) {
        assertEquals(expected, result);
    }

}