package servlet;

import domain.Customer;
import domain.Order;
import domain.OrderDetail;
import domain.Product;
import security.AppSession;
import service.OrderService;
import util.LockManager;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: View order details
 * @author: DennyLee
 * @create: 2019-09-28 13:46
 **/
public class ViewOrderDetailCommand extends FrontCommand{
    @Override
    public void process() throws ServletException, IOException {
        //if a customer logged in
        if (AppSession.isAuthenticated()){
            if (AppSession.hasRole(Params.CUSTOMER_ROLE)){
                String orderId = request.getParameter("order");
                Customer customer = AppSession.getUser();
                try {
                    LockManager.getInstance().acquireReadLock(customer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                OrderService orderService = new OrderService();
                Order order = new Order();
                order.setOrderId(orderId);
                List<OrderDetail> orderDetails = orderService.findOrderDetailsByOrderId(order);
                List<Product> products = new ArrayList<>();

                for (OrderDetail orderDetail: orderDetails                     ) {
                    products.add(orderDetail.getProduct());
                }
                LockManager.getInstance().releaseReadLock(customer);
                request.setAttribute("orderDetails",orderDetails);
                request.setAttribute("products",products);

                forward("/jsp/user/viewOrderDetail.jsp");
            }
        }else {
            redirect("frontservlet?command=ForwardUserHome");
        }
    }
}
