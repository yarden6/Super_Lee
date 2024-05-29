package domain;

import java.time.LocalDate;

public class Report {
    private int reportId;
    private LocalDate created;

    public Report(int reportId){
        this.reportId = reportId;
        this.created = LocalDate.now() ;
    }

}
