package domain;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class DefectiveReport extends Report {

    public DefectiveReport(int reportCounter, Hashtable<Integer, Product> products) {
        super(reportCounter);
        createReport(products);

    }
    public void createReport(Hashtable<Integer, Product> products){
        System.out.println("Defected products:");
        for (Integer MKT:products.keySet()) {
            Product p = products.get(MKT);
            p.toString();
            System.out.println("___________________________________________");
        }
    }


}
