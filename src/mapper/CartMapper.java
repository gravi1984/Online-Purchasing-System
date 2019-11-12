package mapper;

import domain.Cart;
import domain.Customer;
import domain.DomainObject;
import util.DBConnection;
import util.IdentityMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @program: CoffeeWeb
 * @description: Data mapper for cart
 * @author: DennyLee
 * @create: 2019-09-14 14:12
 **/
public class CartMapper extends DataMapper {
    /**
     * insert a cart to cart table
     *
     * @param domainObject Cart
     * @return result
     */
    @Override
    public boolean insert(DomainObject domainObject) {
        Cart cart = (Cart) domainObject;
        String insertNewCart = "INSERT INTO public.cart " +
                "(cart_id, user_id)" +
                "VALUES (?,?)";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(insertNewCart);
            preparedStatement.setString(1, cart.getId());
            preparedStatement.setString(2, cart.getCustomer().getId());
            result = preparedStatement.executeUpdate()==1;

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
     * delete a cart from cart table
     *
     * @param domainObject Cart
     * @return result
     */
    @Override
    public boolean delete(DomainObject domainObject) {
        Cart cart = (Cart) domainObject;
        String deletCategoryById = "DELETE FROM public.cart WHERE cart_id = ?";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(deletCategoryById);
            preparedStatement.setString(1, cart.getId());
            result = preparedStatement.executeUpdate() ==1;
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
     * update a cart from cart table
     *
     * @param domainObject Cart
     * @return result
     */
    @Override
    public boolean update(DomainObject domainObject) {
        Cart cart = (Cart) domainObject;
        String updateCartById = "UPDATE public.cart SET " +
                "user_id =? WHERE cart_id = ?";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(updateCartById);
            preparedStatement.setString(1, cart.getCustomer().getId());
            preparedStatement.setString(2, cart.getId());
            result = preparedStatement.executeUpdate()==1;
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
     * find a cart by cart id in cart table
     *
     * @param domainObject Cart
     * @return a Cart object or null
     */
    public Cart findCartById(DomainObject domainObject) {
        Cart cart = (Cart) domainObject;
        String findCartById = "SELECT * FROM public.cart WHERE cart_id = ?";

        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findCartById);
            preparedStatement.setString(1, cart.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Cart cart1 = new Cart();
                IdentityMap<Cart> identityMap = IdentityMap.getInstance(cart1);

                cart1.setCartId(resultSet.getString(1));
                Customer customer = new Customer();
                customer.setUserId(resultSet.getString(2));
                CustomerMapper userMapper = new CustomerMapper();
                customer = userMapper.findUserById(customer);
                cart1.setCustomer(customer);

                identityMap.put(cart1.getId(), cart1);
                return cart1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * find a cart by user_id in cart table
     *
     * @param domainObject Customer
     * @return a Cart object or null
     */
    public Cart findCartByUserId(DomainObject domainObject) {
        Customer customer = (Customer) domainObject;
        String findCartById = "SELECT * FROM public.cart WHERE user_id = ?";

        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findCartById);
            preparedStatement.setString(1, customer.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Cart cart1 = new Cart();
                IdentityMap<Cart> identityMap = IdentityMap.getInstance(cart1);

                cart1.setCartId(resultSet.getString(1));
                Customer customer1 = new Customer();
                customer.setUserId(resultSet.getString(2));
                CustomerMapper userMapper = new CustomerMapper();
                customer1 = userMapper.findUserById(customer1);
                cart1.setCustomer(customer1);

                identityMap.put(cart1.getId(), cart1);
                return cart1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
