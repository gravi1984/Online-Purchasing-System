package servlet;


import domain.CartDetail;
import domain.Customer;
import security.AppSession;
import service.CartService;
import util.LockManager;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * view shopping cart
 */
public class ViewCartCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //if a customer logged in
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(Params.CUSTOMER_ROLE)) {
                //find a cart by customer
                CartService cartService = new CartService();
                Customer customer = AppSession.getUser();
                try {
                    LockManager.getInstance().acquireReadLock(customer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<CartDetail> cartDetails = cartService.findCartDetailByUserId(customer);
                LockManager.getInstance().releaseReadLock(customer);
                //return result
                request.setAttribute("cartDetails", cartDetails);
                forward("/jsp/user/cart.jsp");
            }
        }else {
            redirect("frontservlet?command=UserLogin");
        }
    }

}
