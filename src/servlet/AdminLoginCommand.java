package servlet;

import domain.Clerk;
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
import servlet.FrontCommand;
import util.LockManager;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @program: CoffeeWeb
 * @description: admin login
 * @author: DennyLee
 * @create: 2019-09-12 14:42
 **/
public class AdminLoginCommand extends FrontCommand {
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
        } catch (AuthenticationException e) {
            e.printStackTrace();
            request.setAttribute("errMsg", "Log in failed");
            target = "/jsp/error.jsp";
        } finally {
            forward(target);
        }
    }
}
