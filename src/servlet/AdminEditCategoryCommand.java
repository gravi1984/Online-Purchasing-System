package servlet;

import domain.Category;
import domain.Staff;
import security.AppSession;
import service.CategoryService;
import util.LockManager;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * edit a category
 */
public class AdminEditCategoryCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //if a staff logged in
        if (AppSession.isAuthenticated()){
            if(AppSession.hasRole(Params.CLERK_ROLE) || AppSession.hasRole(Params.MANAGER_ROLE)){
                // get modify category id
                String id = request.getParameter("category");
                String name = request.getParameter("categoryName");

                Staff staff = AppSession.getStaff();
                try {
                    //acquire write lock
                    LockManager.getInstance().acquireWriteLock(staff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // category service findById
                CategoryService categoryService = new CategoryService();
                Category category = new Category();
                category.setCategoryId(id);
                category = categoryService.findCategroyById(category);
                category.setCategoryName(name);

                // update
                boolean result = categoryService.updateCategory(category);
                LockManager.getInstance().releaseWriteLock(staff);
                //return result
                if (result){
                    redirect("frontservlet?command=AdminCategory");
                }else {
                    request.setAttribute("errMsg", "Edit category fail.");
                    forward("/jsp/error.jsp");
                }
            }
        }else{
            redirect("frontservlet?command=ForwardAdminLogin");
        }
    }
}
