package servlet;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @program: CoffeeWeb
 * @description: forwarde to user home page
 * @author: DennyLee
 * @create: 2019-10-03 00:30
 **/
public class ForwardUserHomeCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        forward("/jsp/user/userHome.jsp");
    }
}
