package domain;/**
 * Created by DennyLee on 2019/9/1.
 */

import mapper.ManagerMapper;

/**
 * @program: CoffeeWeb
 * @description: Manager object
 * @author: DennyLee
 * @create: 2019-09-01 22:55
 **/
public class Manager extends Staff {

    //manager email
    private String managerEmail;


    //constructor
    public Manager() {
        super();
    }

    //constructor with username, password and email
    public Manager(String staffUName, String staffPassword, String managerEmail) {
        super(staffUName, staffPassword);
        this.managerEmail = managerEmail;
    }

    //getter and setter methods
    public String getManagerEmail() {
        if (this.managerEmail == null) {
            load();
        }
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    //use lazy load to reduce request
    private void load() {
        ManagerMapper managerMapper = new ManagerMapper();
        Manager record = managerMapper.findManagerById(this);
        if (this.managerEmail == null) {
            this.managerEmail = record.getManagerEmail();
        }
    }
}
