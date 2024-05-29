package domain;

import java.util.Dictionary;
import java.util.List;

public class DefectiveReport extends Report {

    public DefectiveReport(int reportCounter, List<Product> products) {
        super(reportCounter);
        createReport(products);

    }
    public void createReport(List<Product> products){
        System.out.println("Defected products:");
        for (Product p:products) {
            p.toString();
            System.out.println("___________________________________________");
            products.remove(p); //once I showed the defected products no need to show it again todo
        }
    }


}
