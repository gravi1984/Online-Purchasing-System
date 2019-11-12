package servlet;

import domain.*;
import security.AppSession;
import service.CartService;
import service.OrderService;
import service.ProductService;
import util.LockManager;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: handle request to purchase all products in shopping cart of current session customer
 * @author: DennyLee
 * @create: 2019-09-27 20:02
 **/
public class PlaceOrderCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //if a customer logged in
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(Params.CUSTOMER_ROLE)) {
                try {
                    LockManager.getInstance().acquireWriteLock(AppSession.getUser());
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }

                String address = request.getParameter("address");
                Customer customer = AppSession.getUser();
                double totalPrice = 0.0;
                Order order = new Order(customer, totalPrice, address, Params.PENDING);
                CartService cartService = new CartService();
                List<CartDetail> cartDetails = cartService.findCartDetailByUserId(customer);
                OrderService orderService = new OrderService();
                boolean result = true;
                for (CartDetail cartDetail : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cartDetail.getProduct());
                    orderDetail.setProductAmount(cartDetail.getProductAmount());
                    orderDetail.setProductCategory(cartDetail.getCategory());
                    totalPrice =
                            totalPrice + cartDetail.getProduct().getPrice() * cartDetail.getProductAmount();
                    result = result && orderService.insertOrderDetail(orderDetail);
                }
                order.setTotalPrice(totalPrice);
                result = orderService.insertOrder(order) && cartService.clearCartByUser(customer);

                if (result) {
                    forward("/jsp/user/orderSuccess.jsp");
                } else {
                    request.setAttribute("errMsg", "Fail to place the order!");
                    forward("/jsp/error.jsp");
                }
                LockManager.getInstance().releaseWriteLock(AppSession.getUser());
            }
        }
    }
}

