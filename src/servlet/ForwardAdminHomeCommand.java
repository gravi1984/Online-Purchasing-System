package servlet;

import security.AppSession;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @program: CoffeeWeb
 * @description: .
 * @author: DennyLee
 * @create: 2019-10-03 00:30
 **/
public class ForwardAdminHomeCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //if a staff logged in
        if (AppSession.isAuthenticated()){
            if (AppSession.hasRole(Params.MANAGER_ROLE)|| AppSession.hasRole(Params.CLERK_ROLE)){
                forward("/jsp/admin/adminHome.jsp");
            }else {
                request.setAttribute("errMsg", "Do not have permission");
                forward("/jsp/error.jsp");
            }
        }else {
            redirect("frontservlet?command=ForwardAdminLogin");
        }

    }
}
