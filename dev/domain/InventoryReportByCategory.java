package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

public class InventoryReportByCategory extends Report {


    public InventoryReportByCategory(int reportCounter) {
        super(reportCounter);
    }

    public String createReport(Hashtable<String,Category> categories, String[] selectedCategories){
        if (selectedCategories[0]==""){ // if there are no selected categories, print all the categories
            String[] categoryKeysArray = categories.keySet().toArray(new String[0]);
            return chooseCategory(categories,categoryKeysArray);
        }
        else
            return chooseCategory(categories,selectedCategories);
    }

    public String chooseCategory(Hashtable<String, Category> categories, String[] selectedCategories) {
        StringBuilder report = new StringBuilder();
        for (String name : selectedCategories) {
            if (!name.equals("Defective")) {
                Category selectedParent = categories.get(name); // for the main category
                if (selectedParent != null) {
                    report.append("Main Category: " + selectedParent.getName()+"\n");

                    for (String s : selectedParent.getSubCategories().keySet()) { // for the sub category
                        Category sub = selectedParent.getSubCategories().get(s);
                        report.append("  Sub Category: " + sub.getName()+"\n");

                        for (String subSubName : sub.getSubCategories().keySet()) { // for the sub-sub category
                            Category subSub = sub.getSubCategories().get(subSubName);
                            report.append("    Sub-Sub Category: " + subSub.getName()+"\n");
                            if (subSub.getProducts().isEmpty())
                                report.append("     No products"+"\n");
                            for (Integer mkt : subSub.getProducts().keySet()) { //for the products
                                Product p = subSub.getProducts().get(mkt);
                                report.append(p.toString()+"\n");
                            }
                        }

                    }
                }
            }
        }
        if(report.length() == 0)
            return "There is no category by this name";
        return report.toString();
    }
}
