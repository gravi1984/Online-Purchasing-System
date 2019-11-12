package servlet;

import domain.Clerk;
import domain.Customer;
import domain.Manager;
import domain.Staff;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import security.AppSession;
import service.StaffService;
import service.CustomerService;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @program: CoffeeWeb
 * @description: Handle request to verify username and password and assign role
 * @author: DennyLee
 * @create: 2019-10-01 23:10
 **/
public class LoginCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ByteSource salt = ByteSource.Util.bytes(username);
        String encryptedPassword = new SimpleHash("MD5",password,salt,1024).toHex();
        UsernamePasswordToken token = new UsernamePasswordToken(username, encryptedPassword);
        token.setRememberMe(true);
        Subject currentUser = SecurityUtils.getSubject();
        String target = null;
        try {
            currentUser.login(token);
            Customer customer = new Customer();
            customer.setUsername(username);
            customer = new CustomerService().findUserByName(customer);
            if (customer!=null) {
                AppSession.init(customer);
                target = "/jsp/user/userHome.jsp";
            }else{
                Staff manager = new Manager();
                manager.setStaffUName(username);
                manager = new StaffService().findStaffByName(manager);
                if (manager != null){
                    AppSession.init(manager);
                }else {
                    Staff clerk = new Clerk();
                    clerk.setStaffUName(username);
                    clerk = new StaffService().findStaffByName(clerk);
                    AppSession.init(clerk);
                }
                target = "/jsp/admin/adminHome.jsp";
            }
        } catch (AuthenticationException e) {
            request.setAttribute("errMsg", "Unrecognized username and password.");
            target = "/jsp/error.jsp";
        } finally {
            forward(target);
        }
    }
}
