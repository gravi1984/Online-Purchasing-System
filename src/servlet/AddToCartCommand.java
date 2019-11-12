package servlet;

import domain.CartDetail;
import domain.Category;
import domain.Product;
import domain.Customer;
import security.AppSession;
import service.CartService;
import service.CategoryService;
import service.ProductService;
import util.LockManager;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: add a product to cart
 * @author: DennyLee
 * @create: 2019-09-07 18:01
 **/
public class AddToCartCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //if a user logged in
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(Params.CUSTOMER_ROLE)) {
                Customer customer = AppSession.getUser();
                try {
                    //acquire write lock
                    LockManager.getInstance().acquireWriteLock(customer);
                } catch (InterruptedException e) {
                    System.out.println("Acquiring write lock when adding a product failed.");
                }
                //get parameters
                String productId = request.getParameter("product");
                String categoryId = request.getParameter("category");
                int amount = Integer.parseInt(request.getParameter("amount"));

                //find product by id
                ProductService productService = new ProductService();
                Product product = new Product();
                product.setProductId(productId);
                product = productService.findProductByID(product);

                if (amount > product.getInventory()) {
                    LockManager.getInstance().releaseWriteLock(customer);
                    request.setAttribute("errMsg", "Do not have enough products.");
                    forward("/jsp/error.jsp");
                } else {
                    product.setInventory(product.getInventory()-amount);
                    productService.updateProduct(product);
                    //find category by id
                    CategoryService categoryService = new CategoryService();
                    Category category = new Category();
                    category.setCategoryId(categoryId);
                    category = categoryService.findCategroyById(category);

                    CartService cartService = new CartService();

                    //add to cart
                    boolean result = cartService.AddToCart(customer, product, amount, category);
                    LockManager.getInstance().releaseWriteLock(customer);
                    if (result) {
                        List<CartDetail> cartDetails = cartService.findCartDetailByUserId(customer);
                        request.setAttribute("cartDetails", cartDetails);
                        forward("/jsp/user/cart.jsp");
                    } else {
                        request.setAttribute("errMsg", "Add to cart failed.");
                        forward("/jsp/error.jsp");
                    }
                }
            }
        } else {
            forward("/jsp/user/userLogin.jsp");
        }
    }
}
