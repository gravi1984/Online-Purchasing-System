package servlet;

import domain.Order;
import domain.OrderDetail;
import domain.Staff;
import security.AppSession;
import service.OrderService;
import util.LockManager;
import util.Params;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: handle requests to manage orders by viewing and editing
 * @author: DennyLee
 * @create: 2019-10-01 14:04
 **/
public class AdminManageOrderCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        //if a staff logged in
        if (AppSession.isAuthenticated()){
            if(AppSession.hasRole(Params.CLERK_ROLE) || AppSession.hasRole(Params.MANAGER_ROLE)){
                //get parameters
                String method = request.getParameter("method");
                String orderId = request.getParameter("order");
                Order order = new Order();
                order.setOrderId(orderId);

                Staff staff = AppSession.getStaff();
                try {
                    LockManager.getInstance().acquireReadLock(staff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                switch (method) {
                    case "view":
                        List<OrderDetail> orderDetails =
                                new OrderService().findOrderDetailsByOrderId(order);
                        request.setAttribute("order",order);
                        request.setAttribute("orderDetails",orderDetails);
                        forward("/jsp/admin/viewOrderDetail.jsp");
                        break;
                    case "update":
                        order = new OrderService().findOrderById(order);
                        String updateMethod = request.getParameter("update");
                        switch (updateMethod){
                            case "confirm":
                                order.setStatus(Params.CONFIRMED);
                                break;
                            case "ship":
                                order.setStatus(Params.SHIPPED);
                                break;
                            case "cancel":
                                order.setStatus(Params.CANCELLED);
                                break;
                        }

                        boolean result = new OrderService().updateOrderById(order);
                        if (result)
                            redirect("frontservlet?command=AdminOrder");
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
