package mapper;

import domain.Customer;
import domain.DomainObject;
import domain.Order;
import service.CustomerService;
import util.DBConnection;
import util.IdentityMap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: Data mapper for order
 * @author: DennyLee
 * @create: 2019-09-02 23:55
 **/

public class OrderMapper extends DataMapper {
    /**
     * insert an order
     *
     * @param domainObject Order
     * @return result
     */
    @Override
    public boolean insert(DomainObject domainObject) {
        Order order = (Order) domainObject;
        String insertOrder = "INSERT INTO public.order " +
                "(order_id, user_id, total_price, order_time, address, status, update_time) " +
                "VALUES(?,?,?,?,?,?,?)";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(insertOrder);
            preparedStatement.setString(1, order.getId());
            preparedStatement.setString(2, order.getCustomer().getId());
            preparedStatement.setDouble(3, order.getTotalPrice());
            preparedStatement.setTimestamp(4, new Timestamp(order.getOrderTime().getTime()));
            preparedStatement.setString(5, order.getAddress());
            preparedStatement.setString(6, order.getStatus());
            preparedStatement.setTimestamp(7, new Timestamp(new Date().getTime()));
            result = preparedStatement.executeUpdate() == 1;
            DBConnection.dbConnection.commit();
        } catch (SQLException e) {
            try {
                System.out.println("Rollback");
                DBConnection.dbConnection.rollback();
            } catch (SQLException ignored) {
                System.out.println("Rollback failed.");
            }
            result = false;
            e.printStackTrace();
        } finally {
            try {
                if (DBConnection.dbConnection != null) DBConnection.dbConnection.close();
            } catch (SQLException ignored) {
            }
        }
        return result;
    }

    /**
     * delete an order
     *
     * @param domainObject Order
     * @return result
     */
    @Override
    public boolean delete(DomainObject domainObject) {
        Order order = (Order) domainObject;
        String deleteOrderById = "DELETE from public.order WHERE order_id = ?";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(deleteOrderById);
            preparedStatement.setString(1, order.getId());
            result = preparedStatement.executeUpdate() == 1;
            DBConnection.dbConnection.commit();
        } catch (SQLException e) {
            try {
                System.out.println("Rollback");
                DBConnection.dbConnection.rollback();
            } catch (SQLException ignored) {
                System.out.println("Rollback failed.");
            }
            result = false;
            e.printStackTrace();
        } finally {
            try {
                if (DBConnection.dbConnection != null) DBConnection.dbConnection.close();
            } catch (SQLException ignored) {
            }
        }
        return result;
    }

    /**
     * update an order
     *
     * @param domainObject Order
     * @return result
     */
    @Override
    public boolean update(DomainObject domainObject) {
        Order order = (Order) domainObject;
        String updateOrderById = "UPDATE public.order " +
                "SET user_id = ?, total_price = ?, order_time = ?, address = ?, status = ?, " +
                "update_time = ? " +
                "WHERE order_id = ?";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(updateOrderById);
            preparedStatement.setString(1, order.getCustomer().getId());
            preparedStatement.setDouble(2, order.getTotalPrice());
            preparedStatement.setTimestamp(3, new Timestamp(order.getOrderTime().getTime()));
            preparedStatement.setString(4, order.getAddress());
            preparedStatement.setString(5, order.getStatus());
            preparedStatement.setTimestamp(6, new Timestamp(new Date().getTime()));
            preparedStatement.setString(7, order.getId());
            result = preparedStatement.executeUpdate() == 1;
            DBConnection.dbConnection.commit();
        } catch (SQLException e) {
            try {
                System.out.println("Rollback");
                DBConnection.dbConnection.rollback();
            } catch (SQLException ignored) {
                System.out.println("Rollback failed.");
            }
            result = false;
            e.printStackTrace();
        } finally {
            try {
                if (DBConnection.dbConnection != null) DBConnection.dbConnection.close();
            } catch (SQLException ignored) {
            }
        }
        return result;
    }

    /**
     * find a order by order ID
     *
     * @param domainObject Order
     * @return Order object or null
     */
    public Order findOrderById(DomainObject domainObject) {
        Order order = (Order) domainObject;
        String findOrderById = "SELECT * FROM public.order WHERE order_id = ?";
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findOrderById);
            preparedStatement.setString(1, order.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Order order1 = new Order();
                IdentityMap<Order> identityMap = IdentityMap.getInstance(order1);

                order1.setOrderId(resultSet.getString(1));
                Customer customer = new Customer();
                customer.setUserId(resultSet.getString(2));
                order1.setCustomer(new CustomerService().findUserById(customer));
                order1.setTotalPrice(resultSet.getDouble(3));
                order1.setOrderTime(resultSet.getTimestamp(4));
                order1.setAddress(resultSet.getString(5));
                order1.setStatus(resultSet.getString(6));
                order1.setUpdateTime(resultSet.getTimestamp(7));

                identityMap.put(order1.getId(), order1);
                return order1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * find a order by user Id
     *
     * @param domainObject Order
     * @return Order object or null
     */
    public List<Order> findOrderByUser(DomainObject domainObject) {
        Customer customer = (Customer) domainObject;
        String findOrderById = "SELECT * FROM public.order WHERE user_id = ? ORDER BY update_time DESC";
        List<Order> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findOrderById);
            preparedStatement.setString(1, customer.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order1 = new Order();
                IdentityMap<Order> identityMap = IdentityMap.getInstance(order1);

                order1.setOrderId(resultSet.getString(1));
                Customer customer1 = new Customer();
                customer1.setUserId(resultSet.getString(2));
                order1.setCustomer(new CustomerService().findUserById(customer1));
                order1.setTotalPrice(resultSet.getDouble(3));
                order1.setOrderTime(resultSet.getTimestamp(4));
                order1.setAddress(resultSet.getString(5));
                order1.setStatus(resultSet.getString(6));
                order1.setUpdateTime(resultSet.getTimestamp(7));

                identityMap.put(order1.getId(), order1);
                result.add(order1);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get all orders
     *
     * @return a list of order object or null
     */
    public List<Order> getAllOrders() {
        String findOrderById = "SELECT * FROM public.order ORDER BY update_time DESC";
        List<Order> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findOrderById);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order1 = new Order();
                IdentityMap<Order> identityMap = IdentityMap.getInstance(order1);

                order1.setOrderId(resultSet.getString(1));
                Customer customer1 = new Customer();
                customer1.setUserId(resultSet.getString(2));
                order1.setCustomer(new CustomerService().findUserById(customer1));
                order1.setTotalPrice(resultSet.getDouble(3));
                order1.setOrderTime(resultSet.getTimestamp(4));
                order1.setAddress(resultSet.getString(5));
                order1.setStatus(resultSet.getString(6));
                order1.setUpdateTime(resultSet.getTimestamp(7));

                identityMap.put(order1.getId(), order1);
                result.add(order1);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
