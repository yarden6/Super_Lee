package domain;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class DefectiveReport extends Report {

    public DefectiveReport(int reportCounter) {
        super(reportCounter);

    }
    public String createReport(Hashtable<Integer, Product> products){
        StringBuilder report = new StringBuilder("___________________________________________"+ "\n");
        report.append("Defected products:");
        int index = 1;
        for (Integer MKT:products.keySet()) {
            report.append(String.valueOf(index++)+ "\n");
            Product p = products.get(MKT);
            report.append(p.toString()+ "\n");
            report.append("___________________________________________"+"\n");
        }
        return report.toString();
    }


}
