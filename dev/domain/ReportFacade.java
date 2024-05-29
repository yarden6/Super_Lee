package domain;

import java.util.Dictionary;

public class ReportFacade {
    private CategoryFacade categoryFacade;
    private Dictionary<Integer, Report> inventoryReports;
    private Dictionary<Integer, Report> defectiveReports;
}
