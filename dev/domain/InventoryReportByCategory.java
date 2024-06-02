package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

public class InventoryReportByCategory extends Report {

    public InventoryReportByCategory(int reportCounter, Hashtable<String,Category> categories, String[] selectedCategories) {
        super(reportCounter);
        createReport(categories , selectedCategories);
    }


    public void createReport(Hashtable<String,Category> categories, String[] selectedCategories){
        if (selectedCategories.length == 0){ // if there are no selected categories, print all the categories
            chooseCategory(categories,categories.keySet().toArray(new String[0]));
        }
        else
            chooseCategory(categories,selectedCategories);
    }

    public void chooseCategory(Hashtable<String,Category> categories, String[] selectedCategories) {
        for (String name : selectedCategories) {
            Category selectedParent = categories.get(name);//for the main category
            selectedParent.toString();
            for (String s : selectedParent.getSubCategories().keySet()) { //for the sub category
                Category sub = selectedParent.getSubCategories().get(s);
                sub.toString();
                for(String subSubName : sub.getSubCategories().keySet()){ //for the sub-sub category
                    Category subSub = sub.getSubCategories().get(subSubName);
                    subSub.toString();
                    for (Integer mkt : subSub.getProducts().keySet()) { //for the products
                        Product p = subSub.getProducts().get(mkt);
                        p.toString();
                    }
                }

            }
        }
    }
}
