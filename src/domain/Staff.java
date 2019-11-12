package domain;

import mapper.StaffMapper;
import service.StaffService;

import java.util.UUID;

/**
 * @program: CoffeeWeb
 * @description: Staff domain
 * @author: DennyLee
 * @create: 2019-09-13 22:25
 **/
public abstract class Staff extends DomainObject {
    //staff id
    private String staffId;
    //staff username
    private String staffUName;
    //staff password
    private String staffPassword;

    //constructor
    public Staff() {
    }

    //constructor with username and password
    Staff(String staffUName, String staffPassword) {
        this.staffId = UUID.randomUUID().toString();
        this.staffUName = staffUName;
        this.staffPassword = staffPassword;
    }

    //getter and setter methods
    @Override
    public String getId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffUName() {
        if (this.staffUName == null) {
            load();
        }
        return staffUName;
    }

    public void setStaffUName(String staffUName) {
        this.staffUName = staffUName;
    }

    public String getStaffPassword() {
        if (this.staffPassword == null)
            load();
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }

    //use lazy load to reduce request
    private void load() {
        StaffMapper staffMapper = new StaffMapper();
        Staff record = staffMapper.findStaffById(this);
        if (this.staffUName == null)
            this.staffUName = record.getStaffUName();
        if (this.staffPassword == null)
            this.staffPassword = record.getStaffPassword();
    }

}
