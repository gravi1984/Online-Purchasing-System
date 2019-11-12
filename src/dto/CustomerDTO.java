package dto;/**
 * Created by DennyLee on 2019/9/1.
 */

import com.google.gson.Gson;

import java.util.Date;

/**
 * @program: CoffeeWeb
 * @description: Customer data transfer object
 * @author: DennyLee
 * @create: 2019-09-01 19:31
 **/
public class CustomerDTO {
    //user id
    private String customerId;
    //user firstname
    private String uFname;
    //user lastname
    private String uLname;
    //user name
    private String username;
    //user password
    private String uPassword;
    //user birthday
    private Date birthday;
    //user email
    private String userEmail;
    //user address
    private String address;

    //getter and setter method
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getuFname() {
        return uFname;
    }

    public void setuFname(String uFname) {
        this.uFname = uFname;
    }

    public String getuLname() {
        return uLname;
    }

    public void setuLname(String uLname) {
        this.uLname = uLname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //serialize method
    public static String serialize(CustomerDTO customerDTO) {
        Gson gson = new Gson();
        return gson.toJson(customerDTO);

    }

    //deserialize method
    public static CustomerDTO deserialize(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, CustomerDTO.class);
    }

}