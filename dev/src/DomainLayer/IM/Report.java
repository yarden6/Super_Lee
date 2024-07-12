package DomainLayer.IM;

import java.time.LocalDate;

public class Report {
    private int reportId;
    private LocalDate created;

    public Report(int reportId){
        this.reportId = reportId;
        this.created = LocalDate.now() ;
    }

    public int getReportId() {
        return reportId;
    }

    public LocalDate getCreated() {
        return created;
    }


}
