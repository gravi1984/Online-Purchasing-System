package servlet;

import domain.CartDetail;
import domain.Customer;
import domain.Staff;
import security.AppSession;
import service.CartService;
import util.LockManager;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: If the cart is not empty, forward to check out page
 * @author: DennyLee
 * @create: 2019-09-27 22:47
 **/
public class CheckOutCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //if a customer logged in
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(Params.CUSTOMER_ROLE)) {
                Customer customer = AppSession.getUser();
                try {
                    LockManager.getInstance().acquireReadLock(customer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<CartDetail> cartDetails = new CartService().findCartDetailByUserId(customer);
                LockManager.getInstance().releaseReadLock(customer);
                if (cartDetails.isEmpty()) {
                    request.setAttribute("errMsg", "Have nothing to checkout!");
                    forward("/jsp/error.jsp");
                } else {
                    forward("/jsp/user/placeOrder.jsp");
                }
            }
        }


    }
}
