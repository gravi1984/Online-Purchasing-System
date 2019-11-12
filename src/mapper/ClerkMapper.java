package mapper;

import domain.Clerk;
import domain.DomainObject;
import domain.TimeRange;
import util.DBConnection;
import util.IdentityMap;

import java.sql.*;

/**
 * @program: CoffeeWeb
 * @description: Data mapper for clerk
 * @author: DennyLee
 * @create: 2019-09-13 22:56
 **/
public class ClerkMapper extends DataMapper {
    /**
     * insert a clerk into clerk table
     *
     * @param domainObject Clerk
     * @return result
     */
    @Override
    public boolean insert(DomainObject domainObject) {
        Clerk clerk = (Clerk) domainObject;
        String insertClerk = "INSERT INTO public.clerk " +
                "(clerk_id,clerk_fname,clerk_lname,start_date,end_date) " +
                "VALUES(?,?,?,?,?)";
        String insertToUser = "INSERT INTO public.user " +
                "(user_id,username,password) " +
                "VALUES(?,?,?)";
        boolean result;
        Connection dbConnection = DBConnection.getDBConnection();
        try {
            dbConnection.setAutoCommit(false);
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertClerk);
            preparedStatement.setString(1, clerk.getId());
            preparedStatement.setString(2, clerk.getClerkFirstname());
            preparedStatement.setString(3, clerk.getClerkLastName());
            preparedStatement.setTimestamp(4,
                    new Timestamp(clerk.getTimeRange().getStartDate().getTime()));
            preparedStatement.setTimestamp(5,
                    new Timestamp(clerk.getTimeRange().getEndDate().getTime()));

            PreparedStatement preparedStatement2 = dbConnection.prepareStatement(insertToUser);
            preparedStatement2.setString(1, clerk.getId());
            preparedStatement2.setString(2, clerk.getStaffUName());
            preparedStatement2.setString(3, clerk.getStaffPassword());
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
     * delete a clerk from clerk table
     *
     * @param domainObject Clerk
     * @return result
     */
    @Override
    public boolean delete(DomainObject domainObject) {
        Clerk clerk = (Clerk) domainObject;
        String deleteClerkById = "DELETE FROM public.clerk WHERE clerk_id = ?";
        int result = 0;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(deleteClerkById);
            preparedStatement.setString(1, clerk.getId());

            result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result != 0;
    }

    /**
     * update a clerk from clerk table
     *
     * @param domainObject Clerk
     * @return result
     */
    @Override
    public boolean update(DomainObject domainObject) {
        Clerk clerk = (Clerk) domainObject;
        String updateClerkById = "UPDATE public.clerk SET " +
                "clerk_fname = ?, clerk_lname = ?, " +
                "start_date = ?, end_date=? WHERE clerk_id = ?";
        String updateUser = "UPDATE public.user SET username = ?, password = ? " +
                "WHERE user_id = ?";
                Connection connection = DBConnection.getDBConnection();
        boolean result;
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(updateClerkById);

            preparedStatement.setString(1, clerk.getClerkFirstname());
            preparedStatement.setString(2, clerk.getClerkLastName());
            preparedStatement.setTimestamp(3,
                    new Timestamp(clerk.getTimeRange().getStartDate().getTime()));
            preparedStatement.setTimestamp(4,
                    new Timestamp(clerk.getTimeRange().getEndDate().getTime()));
            preparedStatement.setString(5, clerk.getId());

            PreparedStatement preparedStatement2 = connection.prepareStatement(updateUser);
            preparedStatement2.setString(1, clerk.getStaffUName());
            System.out.println(clerk.getStaffUName());
            preparedStatement2.setString(2, clerk.getStaffPassword());
            preparedStatement2.setString(3, clerk.getId());

            result =
                    preparedStatement.executeUpdate() ==1 && preparedStatement2.executeUpdate() ==1;
            connection.commit();
        } catch (SQLException e) {
            try {
                System.out.println("Rollback");
                connection.rollback();
            } catch (SQLException ignored) {
                System.out.println("Rollback failed.");
            }
            result = false;
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException ignored) {
            }
        }
        return result;
    }

    /**
     * find a clerk from table clerk by id
     *
     * @param domainObject Clerk
     * @return Clerk object or null
     */
    public Clerk findClerkById(DomainObject domainObject) {
        Clerk clerk = (Clerk) domainObject;
        String findClerkById = "SELECT clerk_id, username, password, clerk_fname, clerk_lname, " +
                "start_date, end_date " +
                "FROM public.clerk INNER JOIN public.user " +
                "ON public.clerk.clerk_id = public.user.user_id " +
                "WHERE clerk_id = ?";

        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findClerkById);
            preparedStatement.setString(1, clerk.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Clerk clerk1 = new Clerk();
                IdentityMap<Clerk> identityMap = IdentityMap.getInstance(clerk1);

                clerk1.setStaffId(resultSet.getString(1));
                clerk1.setStaffUName(resultSet.getString(2));
                clerk1.setStaffPassword(resultSet.getString(3));
                clerk1.setClerkFirstname(resultSet.getString(4));
                clerk1.setClerkLastName(resultSet.getString(5));
                clerk1.setTimeRange(new TimeRange(resultSet.getTimestamp(6),
                        resultSet.getTimestamp(7)));
                identityMap.put(clerk1.getId(), clerk1);

                DBConnection.close(preparedStatement);
                return clerk1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Clerk findClerkByName(DomainObject domainObject) {
        Clerk clerk = (Clerk) domainObject;
        String findClerkById = "SELECT clerk_id, username, password, clerk_fname, clerk_lname, " +
                "start_date, end_date " +
                "FROM public.clerk INNER JOIN public.user " +
                "ON public.clerk.clerk_id = public.user.user_id " +
                "WHERE username = ?";

        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findClerkById);
            preparedStatement.setString(1, clerk.getStaffUName());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Clerk clerk1 = new Clerk();
                IdentityMap<Clerk> identityMap = IdentityMap.getInstance(clerk1);

                clerk1.setStaffId(resultSet.getString(1));
                clerk1.setStaffUName(resultSet.getString(2));
                clerk1.setStaffPassword(resultSet.getString(3));
                clerk1.setClerkFirstname(resultSet.getString(4));
                clerk1.setClerkLastName(resultSet.getString(5));
                clerk1.setTimeRange(new TimeRange(resultSet.getTimestamp(6),
                        resultSet.getTimestamp(7)));
                identityMap.put(clerk1.getId(), clerk1);

                DBConnection.close(preparedStatement);
                return clerk1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
