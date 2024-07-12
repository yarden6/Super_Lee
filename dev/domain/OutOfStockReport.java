package domain;

import java.util.Hashtable;

public class OutOfStockReport extends Report{
    public OutOfStockReport(int reportId) {
        super(reportId);
    }

    public String createReport(Hashtable<Integer, Product> productsOutOfStock){
        StringBuilder report = new StringBuilder("___________________________________________"+ "\n");
        report.append("Getting out of stock products:\n");
        int index = 1;
        for (Product p :productsOutOfStock.values()) {
            report.append(String.valueOf(index++)+ "\n");
            report.append(p.toString()+ "\n");
            report.append("___________________________________________"+"\n");
        }
        return report.toString();
    }
}
