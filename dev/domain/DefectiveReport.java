package domain;

import java.util.Dictionary;
import java.util.List;

public class DefectiveReport extends Report {

    public DefectiveReport(int reportCounter, List<Product> products) {
        super(reportCounter);
        createReport(products);

    }
    public void createReport(List<Product> products){
        //TODO
    }
}
