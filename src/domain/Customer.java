package domain;/**
 * Created by DennyLee on 2019/9/1.
 */

import mapper.CustomerMapper;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.Date;
import java.util.UUID;

/**
 * @program: CoffeeWeb
 * @description: Customer object
 * @author: DennyLee
 * @create: 2019-09-01 19:31
 **/
public class Customer extends DomainObject {
    //user id
    private String userId;
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

    //constructor
    public Customer() {
    }

    //constructor with firstname, lastname, username, password, birthday, email, address
    public Customer(String uFname, String uLname, String username, String uPassword, Date
            birthday, String userEmail, String address) {
        this.userId = UUID.randomUUID().toString();
        this.uFname = uFname;
        this.uLname = uLname;
        this.username = username;
        ByteSource salt = ByteSource.Util.bytes(username);
        String encryptedPassword = new SimpleHash("MD5", uPassword, salt, 1024).toHex();
        this.uPassword = encryptedPassword;
        this.birthday = birthday;
        this.userEmail = userEmail;
        this.address = address;
    }

    //getter and setter methods
    @Override
    public String getId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    //use lazy load to reduce request
    private void load() {
        CustomerMapper userMapper = new CustomerMapper();
        Customer record = userMapper.findUserById(this);
        if (this.uFname == null) {
            this.uFname = record.getuFname();
        }
        if (this.uLname == null) {
            this.uLname = record.getuLname();
        }
        if (this.username == null) {
            this.username = record.getUsername();
        }
        if (this.uPassword == null) {
            this.uPassword = record.getuPassword();
        }
        if (this.birthday == null) {
            this.birthday = record.getBirthday();
        }
        if (this.userEmail == null) {
            this.userEmail = record.getUserEmail();
        }
        if (this.address == null) {
            this.address = record.getAddress();
        }
    }
}
