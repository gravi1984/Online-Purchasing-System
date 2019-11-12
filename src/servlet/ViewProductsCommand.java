package servlet;

import domain.Customer;
import domain.Product;
import security.AppSession;
import service.ProductService;
import util.LockManager;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: View products by customers
 * @author: DennyLee
 * @create: 2019-09-06 00:12
 **/
public class ViewProductsCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //get available products
        ProductService productService = new ProductService();
        Customer customer = AppSession.getUser();
        try {
            LockManager.getInstance().acquireReadLock(customer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Product> products = productService.getAllAvailableProducts();
        LockManager.getInstance().releaseReadLock(customer);
        //return result
        if (products.isEmpty()) {
            request.setAttribute("errMsg", "There is no product.");
            forward("/jsp/error.jsp");
        } else {
            request.setAttribute("products", products);
            forward("/jsp/viewProducts.jsp");
        }
    }
}
