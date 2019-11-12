package domain;

import java.util.Date;

/**
 * @program: CoffeeWeb
 * @description: Define time range for clerk
 * @author: DennyLee
 * @create: 2019-09-13 10:52
 **/
public class TimeRange extends DomainObject {
    //work start date of clerks
    private Date startDate;
    //work end date of clerkss
    private Date endDate;

    //constructor
    public TimeRange() {
    }

    //constructor
    public TimeRange(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //getter and setter method
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
