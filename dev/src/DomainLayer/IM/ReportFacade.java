package DomainLayer.IM;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class ReportFacade {
    private CategoryFacade categoryFacade;
    private Hashtable<Integer, Report> inventoryReports;
    private Hashtable<Integer, Report> defectiveReports;
    private Hashtable<Integer, Report> outOfStockReports;
    private int reportCounter;
    final DayOfWeek dayToPrint = DayOfWeek.SUNDAY; //to avoid magic numbers

    public ReportFacade(CategoryFacade cf) {
        this.categoryFacade = cf;
        inventoryReports = new Hashtable<>();
        defectiveReports = new Hashtable<>();
        outOfStockReports = new Hashtable<>();
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

    public String makeOutOfStockReport() {
        OutOfStockReport r = new OutOfStockReport(reportCounter);
        String report = r.createReport(categoryFacade.getProductsOutOfStock());
        defectiveReports.put(reportCounter, r);
        reportCounter++;
        return report;
    }

    public String [] publishReportWeekly() {
        LocalDate current = LocalDate.now();
        if (current.getDayOfWeek() == dayToPrint) {
            return new String[] {makeInventoryReport(new String[]{""}) ,makeDefectiveReport()};
        }
        return null;
    }

    public DayOfWeek getDayToPrint() {
        return dayToPrint;
    }
}
