package security;

import domain.Clerk;
import domain.Customer;
import domain.Manager;
import domain.Staff;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import service.CustomerService;
import service.StaffService;
import util.Params;

import java.util.HashSet;
import java.util.Set;

/**
 * @program: CoffeeWeb
 * @description:
 * @author: DennyLee
 * @create: 2019-10-01 22:14
 **/
public class AppRealm extends JdbcRealm {
    /**
     * authentication method
     * @param token username and password token
     * @return Authentication info
     * @throws AuthenticationException authentiacation fails
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        final String username = usernamePasswordToken.getUsername();

        Customer customer = new Customer();
        customer.setUsername(username);
        customer = new CustomerService().findUserByName(customer);
        Staff manager = new Manager();
        manager.setStaffUName(username);
        manager = new StaffService().findStaffByName(manager);
        Staff clerk = new Clerk();
        clerk.setStaffUName(username);
        clerk = new StaffService().findStaffByName(clerk);

        if (customer != null) {
            return new SimpleAuthenticationInfo(customer.getId(), customer.getuPassword(), getName());
        } else if (manager != null) {
            return new SimpleAuthenticationInfo(manager.getId(), manager.getStaffPassword(), getName());
        } else if (clerk != null) {
            return new SimpleAuthenticationInfo(clerk.getId(), clerk.getStaffPassword(), getName());
        }
        return null;
    }

    /**
     * Authorization method
     * @param principals authorization principals
     * @return Authorization info
     */
    @Override
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
        Set<String> roles = new HashSet<>();
        if (principals.isEmpty()) {
            System.out.println("Given principals to authorize are empty");
            return null;
        }
        //get primary principal (id)
        String id = (String) principals.getPrimaryPrincipal();
        //find a user by id
        Customer customer = new Customer();
        customer.setUserId(id);
        customer = new CustomerService().findUserById(customer);
        Staff manager = new Manager();
        manager.setStaffId(id);
        manager = new StaffService().findStaffById(manager);
        Staff clerk = new Clerk();
        clerk.setStaffId(id);
        clerk = new StaffService().findStaffById(clerk);

        //add role according to user type
        if (customer != null) {
            roles.add(Params.CUSTOMER_ROLE);
            return new SimpleAuthorizationInfo(roles);
        } else if (manager != null) {
            roles.add(Params.MANAGER_ROLE);
            return new SimpleAuthorizationInfo(roles);
        } else if (clerk != null) {
            roles.add(Params.CLERK_ROLE);
            return new SimpleAuthorizationInfo(roles);
        } else {
            System.out.println("No account found for customer with id " + id);
            return null;
        }
    }
}
