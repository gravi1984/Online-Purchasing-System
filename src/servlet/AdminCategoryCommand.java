package servlet;

import domain.Category;
import domain.Staff;
import security.AppSession;
import service.CategoryService;
import servlet.FrontCommand;
import util.LockManager;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: View all category
 * @author: DennyLee
 * @create: 2019-09-12 15:46
 **/
public class AdminCategoryCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //if a staff logged in
        if (AppSession.isAuthenticated()){
            if(AppSession.hasRole(Params.CLERK_ROLE) || AppSession.hasRole(Params.MANAGER_ROLE)){
                Staff staff = AppSession.getStaff();
                try {
                    //acquire write lock
                    LockManager.getInstance().acquireReadLock(staff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //get all categories
                CategoryService categoryService = new CategoryService();
                List<Category> category = categoryService.getAllCategories();
                LockManager.getInstance().releaseReadLock(staff);
                request.setAttribute("categories", category);
                forward("/jsp/admin/categoryManage.jsp");
            }
        }else{
            redirect("frontservlet?command=ForwardAdminLogin");
        }
    }
}
