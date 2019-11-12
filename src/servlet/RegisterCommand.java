package servlet;

import domain.Cart;
import domain.Customer;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import service.CartService;
import service.CustomerService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: CoffeeWeb
 * @description: Customer register
 * @author: DennyLee
 * @create: 2019-10-02 22:56
 **/
public class RegisterCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        String firstname = request.getParameter("firstName");
        String lastname = request.getParameter("lastName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        Date birthday = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            birthday = simpleDateFormat.parse(request.getParameter("birthday"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Customer customer = new Customer(firstname, lastname, username, password, birthday, email,
                address);
        CustomerService userService = new CustomerService();
        userService.insertUser(customer);

        Cart cart = new Cart(customer);
        CartService cartService = new CartService();
        cartService.newCart(cart);
        redirect("frontservlet?command=ForwardUserLogin");
    }
}
