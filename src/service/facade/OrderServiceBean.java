package service.facade;

import com.sun.org.apache.xpath.internal.operations.Or;
import domain.Order;
import dto.OrderAssebler;
import dto.OrderDTO;
import service.OrderService;

import java.rmi.RemoteException;

/**
 * @program: CoffeeWeb
 * @description:
 * @author: DennyLee
 * @create: 2019-10-09 23:50
 **/
public class OrderServiceBean {
    public String findOrderById(String id) throws RemoteException{
        Order order = new Order();
        order.setOrderId(id);
        OrderDTO orderDTO = OrderAssebler.createOrderDTO(new OrderService().findOrderById(order));
        return OrderDTO.serialize(orderDTO);
    }

    //remote call addCategory service
    public boolean addOrder(String id, String json){
        OrderDTO orderDTO = OrderDTO.deserialize(json);
        return OrderAssebler.createOrder(orderDTO);

    }

    //remote call updateOrder service
    public boolean updateOrder(String id, String json){
        OrderDTO orderDTO = OrderDTO.deserialize(json);
        return OrderAssebler.updateOrder(orderDTO);

    }
}
