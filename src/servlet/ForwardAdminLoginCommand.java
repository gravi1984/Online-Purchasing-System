package servlet;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @program: CoffeeWeb
 * @description: forward to admin login
 * @author: DennyLee
 * @create: 2019-09-12 14:41
 **/
public class ForwardAdminLoginCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        forward("/jsp/admin/adminLogin.jsp");
    }

}
