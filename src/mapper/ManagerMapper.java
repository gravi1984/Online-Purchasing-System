package mapper;

import domain.DomainObject;
import domain.Manager;
import util.DBConnection;
import util.IdentityMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @program: CoffeeWeb
 * @description: Data mapper for manager
 * @author: DennyLee
 * @create: 2019-09-13 23:59
 **/
public class ManagerMapper extends DataMapper {
    /**
     * insert a manager to manager table
     *
     * @param domainObject Manager
     * @return result
     */
    @Override
    public boolean insert(DomainObject domainObject) {
        Manager manager = (Manager) domainObject;
        String insertManager = "INSERT INTO public.manager (manager_id, manager_email) VALUES" +
                "(?,?)";
        String insertToUser = "INSERT INTO public.user " +
                "(user_id,username,password) " +
                "VALUES(?,?,?)";
        boolean result;
        Connection dbConnection = DBConnection.getDBConnection();

        try {
            dbConnection.setAutoCommit(false);
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertManager);
            preparedStatement.setString(1, manager.getId());
            preparedStatement.setString(2, manager.getManagerEmail());

            PreparedStatement preparedStatement2 = dbConnection.prepareStatement(insertToUser);
            preparedStatement2.setString(1, manager.getId());
            preparedStatement2.setString(2, manager.getStaffUName());
            preparedStatement2.setString(3, manager.getStaffPassword());
            result = preparedStatement.executeUpdate()==1 && preparedStatement2.executeUpdate()==1;
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
     * delete a manager from manager table by id
     *
     * @param domainObject Manager
     * @return result
     */
    @Override
    public boolean delete(DomainObject domainObject) {
        Manager manager = (Manager) domainObject;
        String deleteManagerById = "DELETE FROM public.manager WHERE manager_id = ?";
        int result = 0;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(deleteManagerById);
            preparedStatement.setString(1, manager.getId());
            result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result != 0;
    }

    /**
     * update a manager in manager table by id
     *
     * @param domainObject Manager
     * @return result
     */
    @Override
    public boolean update(DomainObject domainObject) {
        Manager manager = (Manager) domainObject;
        String updateManagerById = "UPDATE public.manager SET manager_username = ?, " +
                "manager_password = ?, manager_email = ? WHERE manager_id = ?";
        int result = 0;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(updateManagerById);
            preparedStatement.setString(1, manager.getStaffUName());
            preparedStatement.setString(2, manager.getStaffPassword());
            preparedStatement.setString(3, manager.getManagerEmail());
            preparedStatement.setString(4, manager.getId());

            result = preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result != 0;
    }

    /**
     * find a manager by id in manager table
     *
     * @param domainObject Manager
     * @return a Manager object or null
     */
    public Manager findManagerById(DomainObject domainObject) {
        Manager manager = (Manager) domainObject;
        String findManagerById = "SELECT manager_id, username, password, manager_email " +
                "FROM public.manager INNER JOIN public.user " +
                "ON public.manager.manager_id = public.user.user_id " +
                "WHERE manager_id = ?";
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findManagerById);
            preparedStatement.setString(1, manager.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Manager manager1 = new Manager();
                IdentityMap<Manager> identityMap = IdentityMap.getInstance(manager1);

                manager1.setStaffId(resultSet.getString(1));
                manager1.setStaffUName(resultSet.getString(2));
                manager1.setStaffPassword(resultSet.getString(3));
                manager1.setManagerEmail(resultSet.getString(4));

                identityMap.put(manager1.getId(), manager1);
                return manager1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Manager findManagerByName(DomainObject domainObject) {
        Manager manager = (Manager) domainObject;
        String findManagerById = "SELECT manager_id, username, password, manager_email " +
                "FROM public.manager INNER JOIN public.user " +
                "ON public.manager.manager_id = public.user.user_id " +
                "WHERE username = ?";
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findManagerById);
            preparedStatement.setString(1, manager.getStaffUName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Manager manager1 = new Manager();
                IdentityMap<Manager> identityMap = IdentityMap.getInstance(manager1);

                manager1.setStaffId(resultSet.getString(1));
                manager1.setStaffUName(resultSet.getString(2));
                manager1.setStaffPassword(resultSet.getString(3));
                manager1.setManagerEmail(resultSet.getString(4));

                identityMap.put(manager1.getId(), manager1);
                return manager1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
