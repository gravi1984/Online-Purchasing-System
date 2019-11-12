package servlet;

import domain.Product;
import domain.Staff;
import security.AppSession;
import service.ProductService;
import servlet.FrontCommand;
import util.LockManager;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: manage all product by admin
 * @author: DennyLee
 * @create: 2019-09-12 15:46
 **/
public class AdminProductCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //if a staff logged in
        if (AppSession.isAuthenticated()){
            if(AppSession.hasRole(Params.CLERK_ROLE) || AppSession.hasRole(Params.MANAGER_ROLE)){
                Staff staff = AppSession.getStaff();
                try {
                    LockManager.getInstance().acquireReadLock(staff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //get all products
                ProductService productService = new ProductService();
                List<Product> products = productService.getAll();
                LockManager.getInstance().releaseReadLock(staff);
                //return result
                request.setAttribute("products", products);
                forward("/jsp/admin/productManage.jsp");
            }
        }else{
            redirect("frontservlet?command=ForwardAdminLogin");
        }
    }
}
