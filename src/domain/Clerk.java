package domain;

import mapper.ClerkMapper;

import java.util.Date;

/**
 * @program: CoffeeWeb
 * @description: Clerk object
 * @author: DennyLee
 * @create: 2019-09-13 22:34
 **/
public class Clerk extends Staff {
    //clerk firstname
    private String clerkFirstname;
    //clerk lastname
    private String clerkLastName;
    //clerk work time range
    private TimeRange timeRange;

    //constructor
    public Clerk() {
    }

    //constructor with username, password, clerk firstname, clerk lastname, start date, and end date
    public Clerk(String staffUName, String staffPassword, String clerkFirstname,
                 String clerkLastName, Date startDate, Date endDate) {
        super(staffUName, staffPassword);
        this.clerkFirstname = clerkFirstname;
        this.clerkLastName = clerkLastName;
        this.timeRange = new TimeRange(startDate, endDate);
    }

    //getter and setter methods
    public String getClerkFirstname() {
        if (this.clerkFirstname == null)
            load();
        return clerkFirstname;
    }

    public void setClerkFirstname(String clerkFirstname) {
        this.clerkFirstname = clerkFirstname;
    }

    public String getClerkLastName() {
        if (this.clerkLastName == null) {
            load();
        }
        return clerkLastName;
    }

    public void setClerkLastName(String clerkLastName) {
        this.clerkLastName = clerkLastName;
    }

    public TimeRange getTimeRange() {
        if (this.timeRange == null)
            load();
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }

    //use lazy load to reduce request
    private void load() {
        ClerkMapper clerkMapper = new ClerkMapper();
        Clerk record = clerkMapper.findClerkById(this);
        if (this.clerkFirstname == null) {
            this.clerkFirstname = record.getClerkFirstname();
        }
        if (this.clerkLastName == null) {
            this.clerkLastName = record.getClerkLastName();
        }
        if (this.timeRange == null) {
            this.timeRange = record.getTimeRange();
        }
    }
}
