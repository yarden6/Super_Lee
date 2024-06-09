package domain;

import java.util.Hashtable;

public class DefectiveReport extends Report {

    public DefectiveReport(int reportCounter) {
        super(reportCounter);

    }
    public String createReport(Hashtable<Integer, Product> products){
        StringBuilder report = new StringBuilder("___________________________________________"+ "\n");
        report.append("Defected products:\n");
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
