package servlet;

import domain.Order;
import domain.Staff;
import security.AppSession;
import service.OrderService;
import util.LockManager;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * @program: CoffeeWeb
 * @description: Manage all orders by admin
 * @author: DennyLee
 * @create: 2019-09-12 15:45
 **/
public class AdminOrderCommand extends FrontCommand {
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
                //get all categories
                OrderService orderService = new OrderService();
                List<Order> orders = orderService.getAllOrders();
                LockManager.getInstance().releaseReadLock(staff);
                request.setAttribute("orders", orders);
                forward("/jsp/admin/orderManage.jsp");
            }
        }else{
            redirect("frontservlet?command=ForwardAdminLogin");
        }
    }
}
