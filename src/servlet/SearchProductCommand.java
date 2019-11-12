package servlet;

import domain.Customer;
import domain.Product;
import security.AppSession;
import service.ProductService;
import util.LockManager;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * search a product
 */
public class SearchProductCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //get parameters
        String productName = request.getParameter("name");
        Customer customer = AppSession.getUser();
        try {
            LockManager.getInstance().acquireReadLock(customer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //find product by name
        Product product = new Product();
        ProductService productService = new ProductService();
        product.setProductName(productName);
        product = productService.findProductByName(product);
        List<Product> products = new ArrayList<>();
        products.add(product);
        LockManager.getInstance().releaseReadLock(customer);
        //return result
        if (product == null) {
            request.setAttribute("errMsg", "Cannot find the product");
            forward("/jsp/error.jsp");
        } else {
            request.setAttribute("products", products);
            forward("/jsp/viewProducts.jsp");
        }
    }
}
