package servlet;

import security.AppSession;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @program: CoffeeWeb
 * @description: .Forward to add clerk page
 * @author: DennyLee
 * @create: 2019-10-03 00:30
 **/
public class ForwardAddAdminCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //if a manager logged in
        if (AppSession.isAuthenticated()){
            if (AppSession.hasRole(Params.MANAGER_ROLE)){
                forward("/jsp/admin/addStaff.jsp");
            }
        }else {
            request.setAttribute("errMsg","Do not have permission");
            forward("/jsp/error.jsp");
        }

    }
}
