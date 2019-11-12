package servlet;

import domain.Category;
import domain.Staff;
import security.AppSession;
import service.CategoryService;
import util.LockManager;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.concurrent.locks.Lock;

/**
 * @program: CoffeeWeb
 * @description: add a category by admin
 * @author: DennyLee
 * @create: 2019-09-07 18:01
 **/
public class AdminAddCategoryCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {
        //if a staff logged in
        if (AppSession.isAuthenticated()) {
            if (AppSession.hasRole(Params.CLERK_ROLE) || AppSession.hasRole(Params.MANAGER_ROLE)) {
                //get parameters and create new category object
                String categoryName = request.getParameter("categoryName");
                Staff staff = AppSession.getStaff();
                try {
                    //acquire write lock
                    LockManager.getInstance().acquireWriteLock(staff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Category category = new Category(categoryName);
                CategoryService categoryService = new CategoryService();

                //find category by name
                if (categoryService.findCategoryByName(category) != null) {
                    request.setAttribute("errMsg", "Category name exists.");
                    forward("/jsp/error.jsp");
                } else {
                    //add category
                    boolean result = categoryService.newCategory(category);

                    //return add category result to front-end
                    if (result) {
                        redirect("frontservlet?command=AdminCategory");
                    } else {
                        request.setAttribute("errMsg", "Add category failed");
                        forward("/jsp/error.jsp");
                    }
                }
                LockManager.getInstance().releaseWriteLock(staff);
            }
        } else {
            redirect("frontservlet?command=ForwardAdminLogin");
        }
    }
}
