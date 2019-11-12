package mapper;

import domain.Customer;
import domain.DomainObject;
import util.DBConnection;
import util.IdentityMap;

import java.sql.*;

/**
 * @program: CoffeeWeb
 * @description: Data mapper for user
 * @author: DennyLee
 * @create: 2019-09-02 00:27
 **/
public class CustomerMapper extends DataMapper {
    /**
     * insert a user to user table
     *
     * @param domainObject Customer
     * @return result
     */
    @Override
    public boolean insert(DomainObject domainObject) {
        Customer customer = (Customer) domainObject;
        String insertCustomer = "INSERT INTO public.customer " +
                "(user_id, user_firstname, user_lastname, birthday, " +
                "user_email, user_address) VALUES (?,?,?,?,?,?);";
        String insertUser = "INSERT INTO public.user " +
                "(user_id, username, password)" +
                "VALUES (?,?,?)";
        boolean result=false;
        Connection dbConnection = DBConnection.getDBConnection();

        try {
            dbConnection.setAutoCommit(false);
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertCustomer);
            preparedStatement.setString(1, customer.getId());
            preparedStatement.setString(2, customer.getuFname());
            preparedStatement.setString(3, customer.getuLname());
//            preparedStatement.setString(4, customer.getUsername());
//            preparedStatement.setString(5, customer.getuPassword());
            preparedStatement.setTimestamp(4, new Timestamp(customer.getBirthday().getTime()));
            preparedStatement.setString(5, customer.getUserEmail());
            preparedStatement.setString(6, customer.getAddress());

            PreparedStatement preparedStatement2 = dbConnection.prepareStatement(insertUser);
            preparedStatement2.setString(1, customer.getId());
            preparedStatement2.setString(2, customer.getUsername());
            preparedStatement2.setString(3, customer.getuPassword());
            result = preparedStatement.executeUpdate() ==1;
            result = result && preparedStatement2.executeUpdate()==1;
//            DBConnection.dbConnection.commit();
//           preparedStatement2.executeUpdate();
            dbConnection.commit();

        } catch (SQLException e) {
            try {
                System.out.println("Rollback");
                dbConnection.rollback();
            } catch (SQLException ignored) {
                System.out.println("Rollback failed.");
            }
            result = false;
            e.printStackTrace();
        } finally {
            try {
                if (dbConnection != null) dbConnection.close();
            } catch (SQLException ignored) {
            }
        }
        return result;
    }


    /**
     * delete a user from the user table
     *
     * @param domainObject Customer
     * @return result
     */
    @Override
    public boolean delete(DomainObject domainObject) {
        Customer customer = (Customer) domainObject;
        String deleteCustomerById = "DELETE FROM public.customer WHERE user_id = ?";
        String deleteFromUser = "DELETE FROM public.customer WHERE user_id = ?";
        int result = 0;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(deleteCustomerById);
            preparedStatement.setString(1, customer.getId());
            PreparedStatement preparedStatement2 = DBConnection.prepare(deleteFromUser);
            preparedStatement.setString(1, customer.getId());

            result = preparedStatement.executeUpdate()+preparedStatement2.executeUpdate();

//            DBConnection.dbConnection.commit();

        } catch (SQLException e) {
            try {
                DBConnection.dbConnection.rollback();
            } catch (SQLException ignored) {
                System.out.println("Rollback failed.");
            }
            result = 0;
            e.printStackTrace();
        } finally {
            try {
                if (DBConnection.dbConnection != null) DBConnection.dbConnection.close();
            } catch (SQLException ignored) {
            }
        }
        return result == 2;
    }

    /**
     * update a user in user table
     *
     * @param domainObject Customer
     * @return result
     */
    @Override
    public boolean update(DomainObject domainObject) {
        Customer customer = (Customer) domainObject;
        int result = 0;
        String updateUserById = "UPDATE public.customer,user SET user_firstname=?, " +
                "user_lastname=?," +
                "username=?, user_password=?, birthday=?, user_email=?, user_address=?" +
                "WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(updateUserById);
            preparedStatement.setString(1, customer.getuFname());
            preparedStatement.setString(2, customer.getuLname());
            preparedStatement.setString(3, customer.getUsername());
            preparedStatement.setString(4, customer.getuPassword());
            preparedStatement.setTimestamp(5, new Timestamp(customer.getBirthday().getTime()));
            preparedStatement.setString(6, customer.getUserEmail());
            preparedStatement.setString(7, customer.getAddress());
            preparedStatement.setString(8, customer.getId());
            result = preparedStatement.executeUpdate();
            DBConnection.dbConnection.commit();

        } catch (SQLException e) {
            try {
                DBConnection.dbConnection.rollback();
            } catch (SQLException ignored) {
                System.out.println("Rollback failed.");
            }
            result = 0;
            e.printStackTrace();
        } finally {
            try {
                if (DBConnection.dbConnection != null) DBConnection.dbConnection.close();
            } catch (SQLException ignored) {
            }
        }
        return result != 0;
    }

    /**
     * find a user by user id in user table
     *
     * @param domainObject Customer
     * @return a user object or null
     */
    public Customer findUserById(DomainObject domainObject) {
        Customer customer = (Customer) domainObject;
        String findUserById = "SELECT public.user.user_id, user_firstname, user_lastname, " +
                "username, password, birthday, user_email, user_address " +
                "From public.customer INNER JOIN public.user " +
                "ON public.customer.user_id = public.user.user_id " +
                "WHERE public.user.user_id= ?";
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findUserById);
            preparedStatement.setString(1, customer.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Customer result = new Customer();
                IdentityMap<Customer> identityMap = IdentityMap.getInstance(result);

                result.setUserId(resultSet.getString(1));
                result.setuFname(resultSet.getString(2));
                result.setuLname(resultSet.getString(3));
                result.setUsername(resultSet.getString(4));
                result.setuPassword(resultSet.getString(5));
                result.setBirthday(resultSet.getTimestamp(6));
                result.setUserEmail(resultSet.getString(7));
                result.setAddress(resultSet.getString(8));

                identityMap.put(result.getId(), result);
                DBConnection.close(preparedStatement);

                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * find a user by username
     *
     * @param domainObject Customer
     * @return a Customer object or null
     */
    public Customer findUserByName(DomainObject domainObject) {
        Customer customer = (Customer) domainObject;
        String findUserById ="SELECT public.user.user_id, user_firstname, user_lastname, " +
                "username, password, birthday, user_email, user_address " +
                "From public.customer INNER JOIN public.user " +
                "ON public.customer.user_id = public.user.user_id " +
                "WHERE username= ?";
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findUserById);
            preparedStatement.setString(1, customer.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Customer result = new Customer();
                IdentityMap<Customer> identityMap = IdentityMap.getInstance(result);

                result.setUserId(resultSet.getString(1));
                result.setuFname(resultSet.getString(2));
                result.setuLname(resultSet.getString(3));
                result.setUsername(resultSet.getString(4));
                result.setuPassword(resultSet.getString(5));
                result.setBirthday(resultSet.getTimestamp(6));
                result.setUserEmail(resultSet.getString(7));
                result.setAddress(resultSet.getString(8));

                identityMap.put(result.getId(), result);
                DBConnection.close(preparedStatement);

                return result;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
