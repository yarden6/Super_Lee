package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.RecursiveTask;

public class ReportFacade {
    private CategoryFacade categoryFacade;
    private Hashtable<Integer, Report> inventoryReports;
    private Hashtable<Integer, Report> defectiveReports;
    private int reportCounter;

    public ReportFacade(CategoryFacade cf) {
        this.categoryFacade = cf;
        inventoryReports = new Hashtable<>();
        defectiveReports = new Hashtable<>();
        reportCounter = 1;
    }

    public void makeInventoryReport(String[] selectedCategories) {
        Report r = new InventoryReportByCategory(reportCounter, categoryFacade.getCategories(), selectedCategories);
        inventoryReports.put(reportCounter, r);
        reportCounter++;

    }

    public void makeDefectiveReport() {
        Report r = new DefectiveReport(reportCounter, categoryFacade.getCategories().get("Defective").getProducts());
        defectiveReports.put(reportCounter, r);
        reportCounter++;
    }

    public void publishReportWeekly() {
        LocalDate current = LocalDate.now();
        if (current.getDayOfWeek() == DayOfWeek.SUNDAY) {
            makeInventoryReport(new String[0]);
            makeDefectiveReport();

        }
    }


}
