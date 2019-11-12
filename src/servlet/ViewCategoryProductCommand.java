package servlet;

import domain.Category;
import domain.Customer;
import domain.Product;
import security.AppSession;
import service.CategoryService;
import service.ProductService;
import util.LockManager;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * @program: CoffeeWeb
 * @description: view products in a category
 * @author: DennyLee
 * @create: 2019-09-07 15:51
 **/
public class ViewCategoryProductCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //get parameters
        String categoryId = request.getParameter("category");
        Category category = new Category();
        category.setCategoryId(categoryId);
        Customer customer = AppSession.getUser();
        try {
            LockManager.getInstance().acquireReadLock(customer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //find category by id
        CategoryService categoryService = new CategoryService();
        category = categoryService.findCategroyById(category);
        if (category == null) {
            request.setAttribute("errMsg", "The category no longer exists");
            forward("/jsp/error.jsp");
        } else {
            //find product by category
            ProductService productService = new ProductService();
            List<Product> products = productService.findProductByCategory(category);

            //return result
            if (products.isEmpty()) {
                request.setAttribute("errMsg", "No Product in this category");
                forward("/jsp/error.jsp");
            } else {
                request.setAttribute("products", products);
                request.setAttribute("category", category);
                forward("/jsp/viewProductsInCategory.jsp");
            }
        }
        LockManager.getInstance().releaseReadLock(customer);
    }
}
