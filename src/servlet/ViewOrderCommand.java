package servlet;

import domain.Order;
import domain.Customer;
import security.AppSession;
import service.OrderService;
import util.LockManager;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: view orders of current user
 * @author: DennyLee
 * @create: 2019-09-27 23:49
 **/
public class ViewOrderCommand extends FrontCommand{
    @Override
    public void process() throws ServletException, IOException {
        //if a customer logged in
        if (AppSession.isAuthenticated()){
            if (AppSession.hasRole(Params.CUSTOMER_ROLE)){
                Customer customer =AppSession.getUser();
                try {
                    LockManager.getInstance().acquireReadLock(customer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                OrderService orderService = new OrderService();
                List<Order> orders = orderService.findOrderByUser(customer);
                LockManager.getInstance().releaseReadLock(customer);
                request.setAttribute("orders",orders);
                forward("/jsp/user/viewOrders.jsp");
            }
        } else {
            redirect("frontservlet?command=ForwardUserHome");
        }
    }
}
