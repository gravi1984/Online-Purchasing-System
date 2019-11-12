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
import java.util.List;

/**
 * handle requests to manage products by create, delete and edit
 */
public class AdminManageProductCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //if a staff logged in
        if (AppSession.isAuthenticated()){
            if(AppSession.hasRole(Params.CLERK_ROLE) || AppSession.hasRole(Params.MANAGER_ROLE)){
                //get parameters
                String method = request.getParameter("method");
                String productId;
                Product product;
                ProductService productService;
                CategoryService categoryService;
                List<Category> categories;

                Staff staff = AppSession.getStaff();
                try {
                    LockManager.getInstance().acquireReadLock(staff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                switch (method) {
                    //create new product
                    case "create":
                        categoryService = new CategoryService();
                        categories = categoryService.getAllCategories();

                        request.setAttribute("categories", categories);
                        forward("/jsp/admin/newProduct.jsp");
                        break;
                    //delete a product
                    case "delete":
                        productId = request.getParameter("product");
                        product = new Product();
                        product.setProductId(productId);
                        productService = new ProductService();
                        boolean result = productService.deleteProduct(product);
                        if (result) {
                            redirect("frontservlet?command=AdminProduct");
                        } else {
                            request.setAttribute("errMsg", "Delete product failed");
                            forward("/jsp/error.jsp");
                        }
                        break;
                    //edit product
                    case "edit":
                        productId = request.getParameter("product");
                        product = new Product();
                        product.setProductId(productId);
                        productService = new ProductService();
                        product = productService.findProductByID(product);

                        categoryService = new CategoryService();
                        categories = categoryService.getAllCategories();

                        request.setAttribute("categories", categories);
                        request.setAttribute("product", product);
                        forward("/jsp/admin/editProduct.jsp");
                        break;

                    default:
                        System.out.println("Wrong product manage method input");
                }

                LockManager.getInstance().releaseReadLock(staff);
            }
        }else{
            redirect("frontservlet?command=ForwardAdminLogin");
        }
    }
}
