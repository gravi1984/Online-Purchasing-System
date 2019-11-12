package servlet;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @program: CoffeeWeb
 * @description: forard to user login
 * @author: DennyLee
 * @create: 2019-09-12 14:41
 **/
public class ForwardUserLoginCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        forward("/jsp/user/userLogin.jsp");
    }

}
