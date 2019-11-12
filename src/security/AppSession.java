package security;

import domain.Customer;
import domain.Staff;
import org.apache.shiro.SecurityUtils;
import util.Params;

/**
 * @program: CoffeeWeb
 * @description:
 * @author: DennyLee
 * @create: 2019-10-01 22:21
 **/
public class AppSession {

    /**
     * verify if the user of current session has a particular role
     *
     * @param role a role
     * @return true or false
     */
    public static boolean hasRole(String role) {
        return SecurityUtils.getSubject().hasRole(role);
    }

    /**
     * check if the user of current session is authenticated
     *
     * @return true or false
     */
    public static boolean isAuthenticated() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    /**
     * init a session
     *
     * @param customer Customer
     */
    public static void init(Customer customer) {
        SecurityUtils.getSubject().getSession().setAttribute(Params.USER_ATTRIBUTE_NAME, customer);
    }

    /**
     * get the customer of current session
     *
     * @return
     */
    public static Customer getUser() {
        return (Customer) SecurityUtils.getSubject().getSession().getAttribute(Params.USER_ATTRIBUTE_NAME);
    }

    /**
     * init a session
     *
     * @param staff
     */
    public static void init(Staff staff) {
        SecurityUtils.getSubject().getSession().setAttribute(Params.STAFF_ATTRIBUTE_NAME, staff);
    }

    /**
     * get the staff of current session
     *
     * @return
     */
    public static Staff getStaff() {
        return (Staff) SecurityUtils.getSubject().getSession().getAttribute(Params.STAFF_ATTRIBUTE_NAME);
    }

    /**
     * logout function
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

}
