package domain;

import java.util.Dictionary;

public class InventoryReportByCategory extends Report {

    public InventoryReportByCategory(int reportCounter, Dictionary<String,Category> categories) {
        super(reportCounter);
    }

    public void createReport(Dictionary<String,Category> defectives){
        //TODO
    }
}
