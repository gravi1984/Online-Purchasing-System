package dto;

import com.google.gson.Gson;
import domain.Staff;

import java.util.Date;

/**
 * @program: CoffeeWeb
 * @description: Staff Data transfer object
 * @author: DennyLee
 * @create: 2019-10-10 00:35
 **/
public class StaffDTO {
    //username
    private String username;
    //password
    private String password;
    //id
    private String id;
    //start date
    private Date startDate;
    //end date
    private Date endDate;
    //manager email
    private String managerEmail;
    //clerk first name
    private String clerkFirstname;
    //clerk last name
    private String clerkLastname;
    //staff type
    private String type;

    //getter and setter methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getClerkFirstname() {
        return clerkFirstname;
    }

    public void setClerkFirstname(String clerkFirstname) {
        this.clerkFirstname = clerkFirstname;
    }

    public String getClerkLastname() {
        return clerkLastname;
    }

    public void setClerkLastname(String clerkLastname) {
        this.clerkLastname = clerkLastname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //serialize method
    public static String serialize(StaffDTO staffDTO) {
        Gson gson = new Gson();
        return gson.toJson(staffDTO);

    }

    //deserialize method
    public static StaffDTO deserialize(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, StaffDTO.class);
    }


}
