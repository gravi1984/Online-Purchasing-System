package servlet;

import domain.Category;
import domain.Product;
import domain.Staff;
import security.AppSession;
import service.CategoryService;
import service.ProductService;
import util.LockManager;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @program: CoffeeWeb
 * @description: add a product by admin
 * @author: DennyLee
 * @create: 2019-09-07 18:01
 **/
public class AdminAddProductCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //if a staff logged in
        if (AppSession.isAuthenticated()){
            if(AppSession.hasRole(Params.CLERK_ROLE) || AppSession.hasRole(Params.MANAGER_ROLE)){

                //get parameters
                String name = request.getParameter("productName");
                String info = request.getParameter("info");
                String[] category = null;
                category = request.getParameterValues("category");
                double price = Double.parseDouble(request.getParameter("price"));
                int weight = Integer.parseInt(request.getParameter("weight"));
                int inventory = Integer.parseInt(request.getParameter("inventory"));

                //create new product object
                Product product = new Product(name, info, price, weight, inventory);
                ProductService productService = new ProductService();

                Staff staff = AppSession.getStaff();
                try {
                    //acquire write lock
                    LockManager.getInstance().acquireWriteLock(staff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (productService.findProductByName(product) != null) {
                    request.setAttribute("errMsg", "Product name exists.");
                    forward("/jsp/error.jsp");
                } else {
                    //add a product
                    boolean result = productService.insertProduct(product);
                    if (!result) {
                        request.setAttribute("errMsg", "Add new product fail.");
                        forward("/jsp/error.jsp");
                    } else {
                        //add product category relations
                        CategoryService categoryService = new CategoryService();
                        if (category != null && category.length > 0) {
                            for (String s : category) {
                                Category category1 = new Category();
                                category1.setCategoryName(s);
                                category1 = categoryService.findCategoryByName(category1);

                                result = productService.addRelation(product, category1) && result;
                            }
                        }
                        //return result
                        if (result) {
                            redirect("frontservlet?command=AdminProduct");
                        } else {
                            request.setAttribute("errMsg", "Add to category fail.");
                            forward("/jsp/error.jsp");
                        }
                    }
                }
                LockManager.getInstance().releaseWriteLock(staff);
            }
        }else{
            redirect("frontservlet?command=ForwardAdminLogin");
        }
    }
}
