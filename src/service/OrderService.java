package service;

import domain.Customer;
import domain.Order;
import domain.OrderDetail;
import mapper.OrderDetailMapper;
import mapper.OrderMapper;
import util.IdentityMap;
import util.UnitOfWork;

import java.util.List;

/**
 * @program: CoffeeWeb
 * @description:
 * @author: DennyLee
 * @create: 2019-09-06 22:53
 **/
public class OrderService {

    private OrderMapper orderMapper;
    private OrderDetailMapper orderDetailMapper;

    public OrderService() {
        this.orderMapper = new OrderMapper();
        this.orderDetailMapper = new OrderDetailMapper();
    }

    public boolean insertOrderDetail(OrderDetail orderDetail) {
        UnitOfWork.newCurrent();
        UnitOfWork.getCurrent().registerNew(orderDetail);
        return UnitOfWork.getCurrent().commit();
    }

    public boolean insertOrder(Order order) {
        UnitOfWork.newCurrent();
        UnitOfWork.getCurrent().registerNew(order);
        return UnitOfWork.getCurrent().commit();
    }

    public Order findOrderById(Order order) {
        IdentityMap<Order> identityMap = IdentityMap.getInstance(order);
        Order orderFinded = identityMap.get(order.getId());

        if (orderFinded != null) {
            return orderFinded;
        } else {
            return orderMapper.findOrderById(order);
        }
    }

    public List<Order> findOrderByUser(Customer customer) {
        return orderMapper.findOrderByUser(customer);
    }

    public List<OrderDetail> findOrderDetailsByOrderId(Order order) {
        return orderDetailMapper.findOrderDetailByOrderId(order);
    }

    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    public boolean updateOrderById(Order order) {
        return orderMapper.update(order);
    }
}
