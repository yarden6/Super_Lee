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

    public String makeInventoryReport(String[] selectedCategories) {
        InventoryReportByCategory r = new InventoryReportByCategory(reportCounter);
        String report = r.createReport(categoryFacade.getCategories(), selectedCategories);
        inventoryReports.put(reportCounter, r);
        reportCounter++;
        return report;
    }

    public String makeDefectiveReport() {
        DefectiveReport r = new DefectiveReport(reportCounter);
        String report = r.createReport(categoryFacade.getCategories().get("Defective").getProducts());
        defectiveReports.put(reportCounter, r);
        reportCounter++;
        return report;
    }

    public String [] publishReportWeekly() {
        LocalDate current = LocalDate.now();
        if (current.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return new String[] {makeInventoryReport(new String[0]) ,makeDefectiveReport()};
        }
        return null;
    }


}
