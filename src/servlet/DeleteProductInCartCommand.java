package servlet;

import domain.CartDetail;
import domain.Customer;
import domain.Product;
import security.AppSession;
import service.CartService;
import service.ProductService;
import util.LockManager;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @program: CoffeeWeb
 * @description: delete a product in cart
 * @author: DennyLee
 * @create: 2019-09-14 22:48
 **/
public class DeleteProductInCartCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //if a customer logged in
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(Params.CUSTOMER_ROLE)) {
                //get parameters
                String cartDetailId = request.getParameter("cartDetail");

                Customer customer = AppSession.getUser();
                try {
                    LockManager.getInstance().acquireReadLock(customer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //delete cart detail by id
                CartDetail cartDetail = new CartDetail();
                cartDetail.setCartDetailId(cartDetailId);
                CartService cartService = new CartService();
                cartDetail = cartService.findCartDetailById(cartDetail);
                Product product =  cartDetail.getProduct();
                product.setInventory(product.getInventory() + cartDetail.getProductAmount());
                boolean result =
                        new ProductService().updateProduct(product) && cartService.deleteCartDetail(cartDetail);
                LockManager.getInstance().releaseReadLock(customer);
                //return result
                if (result) {
                    redirect("frontservlet?command=ViewCart");
                } else {
                    request.setAttribute("errMsg", "Delete product failed.");
                    forward("/jsp/error.jsp");
                }
            }
        }
    }
}
