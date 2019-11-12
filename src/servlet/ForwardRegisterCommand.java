package servlet;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @program: CoffeeWeb
 * @description: Forward to customer register page
 * @author: DennyLee
 * @create: 2019-10-02 23:16
 **/
public class ForwardRegisterCommand extends FrontCommand{
    @Override
    public void process() throws ServletException, IOException {
        forward("/jsp/user/userRegister.jsp");
    }
}
