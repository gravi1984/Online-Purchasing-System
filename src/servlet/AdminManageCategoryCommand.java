package servlet;

import domain.Category;
import domain.Product;
import domain.Staff;
import security.AppSession;
import service.CategoryService;
import service.ProductService;
import servlet.FrontCommand;
import util.LockManager;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * handle requests to manage categories by create, delete and edit
 */
public class AdminManageCategoryCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //if a staff logged in
        if (AppSession.isAuthenticated()){
            if(AppSession.hasRole(Params.CLERK_ROLE) || AppSession.hasRole(Params.MANAGER_ROLE)){
                String method = request.getParameter("method");
                String categoryId;
                Category category;
                CategoryService categoryService;
                Staff staff = AppSession.getStaff();
                try {
                    LockManager.getInstance().acquireReadLock(staff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                switch (method) {
                    case "create":
                        forward("/jsp/admin/newCategory.jsp");
                        break;
                    case "delete":
                        categoryId = request.getParameter("category");
                        category = new Category();
                        category.setCategoryId(categoryId);
                        categoryService = new CategoryService();
                        boolean result = categoryService.deleteCategory(category);
                        if (result){
                            redirect("frontservlet?command=AdminCategory");
                        }else{
                            request.setAttribute("errMsg", "Delete category failed");
                            forward("/jsp/error.jsp");
                        }
                        break;
                    case "edit":
                        //edit a category
                        categoryId = request.getParameter("category");
                        category = new Category();
                        category.setCategoryId(categoryId);
                        categoryService = new CategoryService();
                        category = categoryService.findCategroyById(category);
                        request.setAttribute("category", category);
                        forward("/jsp/admin/editCategory.jsp");
                        break;
                    case"view":
                        //find all products in a category
                        categoryId = request.getParameter("category");
                        category = new Category();
                        category.setCategoryId(categoryId);
                        categoryService = new CategoryService();
                        category = categoryService.findCategroyById(category);
                        ProductService productService = new ProductService();
                        List<Product> products = productService.findProductByCategory(category);
                        request.setAttribute("category",category);
                        request.setAttribute("products",products);
                        forward("/jsp/admin/viewCategoryProducts.jsp");
                        break;
                    default:
                        System.out.println("Wrong category manage method input");
                }
                LockManager.getInstance().releaseReadLock(staff);
            }
        }else{
            redirect("frontservlet?command=ForwardAdminLogin");
        }
    }
}
