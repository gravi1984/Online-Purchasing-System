package servlet;

import domain.Category;
import domain.Customer;
import security.AppSession;
import service.CategoryService;
import util.LockManager;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * view all categories
 */
public class ViewCategoryCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {
        //get categories
        CategoryService categoryService = new CategoryService();
        Customer customer = AppSession.getUser();
        try {
            LockManager.getInstance().acquireReadLock(customer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Category> category = categoryService.getAllCategories();
        LockManager.getInstance().releaseReadLock(customer);
        //return result
        if (category.isEmpty()) {
            request.setAttribute("errMsg", "No category exists");
            forward("/jsp/error.jsp");
        } else {
            request.setAttribute("categories", category);
            forward("/jsp/viewCategory.jsp");
        }
    }
}
