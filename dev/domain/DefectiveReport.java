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
        System.out.println("___________________________________________");
        System.out.println("Defected products:");
        int index = 1;
        for (Integer MKT:products.keySet()) {
            System.out.println(index++);
            Product p = products.get(MKT);
            System.out.println(p.toString());
            System.out.println("___________________________________________");
        }
    }


}
