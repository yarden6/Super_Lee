package domain;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

public class ReportFacade {
    private CategoryFacade categoryFacade;
    private Dictionary<Integer, Report> inventoryReports;
    private Dictionary<Integer, Report> defectiveReports;
    private int reportCounter;

    public ReportFacade(CategoryFacade cf){
        this.categoryFacade=cf;
        inventoryReports = new Hashtable<>() ;
        defectiveReports = new Hashtable<>();
        reportCounter = 1;
    }

    public void makeInventoryReport(){
        Report r = new InventoryReportByCategory(reportCounter,categoryFacade.getCategories());
        inventoryReports.put(reportCounter,r);

    }

    public void makeDefectiveReport(){
        Report r = new DefectiveReport(reportCounter,categoryFacade.getCategories().get("defective").getProducts());
        defectiveReports.put(reportCounter,r);
    }


}
