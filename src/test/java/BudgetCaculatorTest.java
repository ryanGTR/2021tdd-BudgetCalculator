import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BudgetCaculatorTest {

    @Test
    public void Jan_SingleDay_Budget() {
        List<Budget> list = new ArrayList<Budget>() {{
            add(new Budget( "202001", 31));
        }};
//        BudgetRepo budgetRepo = new BudgetRepo();
        IBudgetRepo repo = mock(IBudgetRepo.class);
        Mockito.when(repo.getAll()).thenReturn(list);
        BigDecimal singleDayBuget =  new BudgetCalculator(repo).query(LocalDate.of(2020, 1,1), LocalDate.of(2020, 1,1));
        assertEquals(BigDecimal.valueOf(1), singleDayBuget);
    }

    @Test
    public void Jan_SingleMonth_Budget() {
        List<Budget> list = new ArrayList<Budget>() {{
            add(new Budget( "202001", 31));
        }};
//        BudgetRepo budgetRepo = new BudgetRepo();
        IBudgetRepo repo = mock(IBudgetRepo.class);
        Mockito.when(repo.getAll()).thenReturn(list);
        BigDecimal singleDayBuget =  new BudgetCalculator(repo).query(LocalDate.of(2020, 1,1), LocalDate.of(2020, 1,31));
        assertEquals(BigDecimal.valueOf(31), singleDayBuget);
    }

 //   @Test
//    public void Jan_MultiMonth_Budget() {
//        List<Budget> list = new ArrayList<Budget>() {{
//            add(new Budget( "202101", 31));
//            add(new Budget( "202102", 280));
//        }};
////        BudgetRepo budgetRepo = new BudgetRepo();
//        IBudgetRepo repo = mock(IBudgetRepo.class);
//        Mockito.when(repo.getAll()).thenReturn(list);
//        BigDecimal singleDayBuget =  new BudgetCalculator(repo).query(
//                                LocalDate.of(2021, 1,31),
//                                LocalDate.of(2021, 2,1)
//        );
//        assertEquals(BigDecimal.valueOf(11), singleDayBuget);
//    }




}