import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class StatisticsTest {
    private static void clearStats(){
        File file = new File("data.bin");
        if (file.exists() && !file.isDirectory()){
            file.delete();
        }
    }

    @BeforeAll
    static void Preps(){
        clearStats();
    }

    @Test
    void CalcTests(){
        Statistics statistics;
        Purchase purchase;
        Report report;
        try {
            statistics = new Statistics();
        } catch (IOException e) {
            fail("I/O error");
            return;
        }

        purchase = new Purchase("булка", "2000.01.01", 1000);
        report = statistics.CommitPurchase(purchase);
        Assertions.assertEquals(report.getMaxCategory().getCategory(), "еда");
        Assertions.assertEquals(report.getMaxDayCategory().getCategory(), "еда");
        Assertions.assertEquals(report.getMaxMonthCategory().getCategory(), "еда");
        Assertions.assertEquals(report.getMaxYearCategory().getCategory(), "еда");

        purchase = new Purchase("тапки", "2000.02.01", 100);
        report = statistics.CommitPurchase(purchase);
        Assertions.assertEquals(report.getMaxCategory().getCategory(), "еда");
        Assertions.assertEquals(report.getMaxDayCategory().getCategory(), "одежда");
        Assertions.assertEquals(report.getMaxMonthCategory().getCategory(), "одежда");
        Assertions.assertEquals(report.getMaxYearCategory().getCategory(), "еда");

        purchase = new Purchase("мыло", "2000.02.02", 10);
        report = statistics.CommitPurchase(purchase);
        Assertions.assertEquals(report.getMaxCategory().getCategory(), "еда");
        Assertions.assertEquals(report.getMaxDayCategory().getCategory(), "быт");
        Assertions.assertEquals(report.getMaxMonthCategory().getCategory(), "одежда");
        Assertions.assertEquals(report.getMaxYearCategory().getCategory(), "еда");

        purchase = new Purchase("скрепка", "2001.01.01", 1);
        report = statistics.CommitPurchase(purchase);
        Assertions.assertEquals(report.getMaxCategory().getCategory(), "еда");
        Assertions.assertEquals(report.getMaxDayCategory().getCategory(), "другое");
        Assertions.assertEquals(report.getMaxMonthCategory().getCategory(), "другое");
        Assertions.assertEquals(report.getMaxYearCategory().getCategory(), "другое");
    }

    @AfterAll
    static void Clear(){
        clearStats();
    }
}
