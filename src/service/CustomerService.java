package service;

import domain.Customer;
import mapper.CustomerMapper;
import util.IdentityMap;
import util.UnitOfWork;

/**
 * @program: CoffeeWeb
 * @description: user  service to manager logic method related to user
 * @author: DennyLee
 * @create: 2019-09-06 22:54
 **/
public class CustomerService {
    //private userMapper object;
    private CustomerMapper userMapper;

    //constructor
    public CustomerService() {
        userMapper = new CustomerMapper();
    }

    /**
     * create a customer, apply unit of work
     *
     * @param customer Customer
     * @return result
     */
    public boolean insertUser(Customer customer) {
        UnitOfWork.newCurrent();
        UnitOfWork.getCurrent().registerNew(customer);
        return UnitOfWork.getCurrent().commit();
    }

    /**
     * delete a customer, apply unit of work
     *
     * @param customer Customer
     * @return result
     */
    public boolean deleteUser(Customer customer) {
        UnitOfWork.newCurrent();
        UnitOfWork.getCurrent().registerDelete(customer);
        return UnitOfWork.getCurrent().commit();
    }

    /**
     * update a customer, apply unit of work
     *
     * @param customer Customer
     * @return result
     */
    public boolean updateUser(Customer customer) {
        UnitOfWork.newCurrent();
        UnitOfWork.getCurrent().registerDirty(customer);
        return UnitOfWork.getCurrent().commit();
    }

    /**
     * find a customer by customer id
     *
     * @param customer Customer
     * @return a customer object or null
     */
    public Customer findUserById(Customer customer) {
        IdentityMap<Customer> identityMap = IdentityMap.getInstance(customer);
        Customer customerFinded = identityMap.get(customer.getId());
        if (customerFinded != null) {
            return customerFinded;
        } else {
            return userMapper.findUserById(customer);
        }
    }

    /**
     * find a customer by username
     *
     * @param customer Customer
     * @return a customer object or null
     */
    public Customer findUserByName(Customer customer) {
        try {
            return userMapper.findUserByName(customer);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
