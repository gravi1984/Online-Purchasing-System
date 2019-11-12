package servlet;

import domain.Category;
import domain.Product;
import domain.Staff;
import security.AppSession;
import service.CartService;
import service.CategoryService;
import service.ProductService;
import util.LockManager;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @program: CoffeeWeb
 * @description: Edit a product
 * @author: DennyLee
 * @create: 2019-09-12 17:13
 **/
public class AdminEditProductCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //if a staff logged in
        if (AppSession.isAuthenticated()){
            if(AppSession.hasRole(Params.CLERK_ROLE) || AppSession.hasRole(Params.MANAGER_ROLE)){
                String productId = request.getParameter("product");
                String name = request.getParameter("productName");
                String info = request.getParameter("info");
                String[] category = null;
                category = request.getParameterValues("category");
                double price = Double.parseDouble(request.getParameter("price"));
                int weight = Integer.parseInt(request.getParameter("weight"));
                int inventory = Integer.parseInt(request.getParameter("inventory"));

                Staff staff = AppSession.getStaff();
                try {
                    //acquire write lock
                    LockManager.getInstance().acquireWriteLock(staff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Product product = new Product();
                product.setProductId(productId);
                ProductService productService = new ProductService();
                product = productService.findProductByID(product);
                if (!product.getProductName().equals(name)) {
                    product.setProductName(name);
                }
                if (!product.getInfo().equals(info)) {
                    product.setInfo(info);
                }
                if (product.getPrice() != price) {
                    CartService cartService = new CartService();
                    product.setPrice(price);
                    cartService.updatePrice(product);
                }
                if (product.getWeight() != weight) {
                    product.setWeight(weight);
                }
                if (product.getInventory() != inventory) {
                    product.setInventory(inventory);
                }

                boolean result = productService.updateProduct(product);


                productService.deleteAllRelations(product);
                CategoryService categoryService = new CategoryService();

                if (category != null && category.length > 0) {
                    for (String s : category) {
                        Category category1 = new Category();
                        category1.setCategoryName(s);
                        category1 = categoryService.findCategoryByName(category1);

                        result = productService.addRelation(product, category1) && result;
                    }
                }

                LockManager.getInstance().releaseWriteLock(staff);
                if (result) {
                    redirect("frontservlet?command=AdminProduct");
                } else {
                    request.setAttribute("errMsg", "Edit product fail.");
                    forward("/jsp/error.jsp");
                }
            }
        }else{
            redirect("frontservlet?command=ForwardAdminLogin");
        }
    }
}
