package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;

public class InventoryReportByCategory extends Report {

    public InventoryReportByCategory(int reportCounter, Hashtable<String,Category> categories, List<String> selectedCategories) {
        super(reportCounter);
        createReport(categories , selectedCategories);
    }


    //TODO TRY TO MAKE EFFIECIENT
    public void createReport(Hashtable<String,Category> categories, List<String> selectedCategories){
        if (selectedCategories.isEmpty()){
            chooseCategory(categories,categories.keySet().stream().toList());
        }
        else
            chooseCategory(categories,selectedCategories);
    }

    public void chooseCategory(Hashtable<String,Category> categories, List<String> selectedCategories) {
        for (String name : selectedCategories) {
            Category selectedParent = categories.get(name);//for the main category
            selectedParent.toString();
            for (Category sub : selectedParent.getSubCategories()) { //for the sub category
                sub.toString();
                for(Category subSub : sub.getSubCategories()){ //for the sub-sub category
                    subSub.toString();
                    for (Product p : subSub.getProducts()) { //for the products
                        toString();
                    }
                }

            }
        }
    }
}
