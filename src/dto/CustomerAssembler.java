package dto;

import domain.Customer;
import service.CustomerService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @program: CoffeeWeb
 * @description:Functions of CustomerDTO
 * @author: DennyLee
 * @create: 2019-10-09 13:44
 **/
public class CustomerAssembler {

    /**
     * create CustomerDTO from customer
     *
     * @param customer Customer
     * @return CustomerDTO
     */
    public static CustomerDTO createCustomerDTO(Customer customer) {
        CustomerDTO result = new CustomerDTO();
        result.setCustomerId(customer.getId());
        result.setUsername(customer.getUsername());
        result.setuPassword(customer.getuPassword());
        result.setuFname(customer.getuFname());
        result.setuLname(customer.getuLname());
        result.setBirthday(customer.getBirthday());
        result.setUserEmail(customer.getUserEmail());
        result.setAddress(customer.getAddress());
        return result;
    }

    /**
     * add customer from remote call
     *
     * @param customerDTO Customer DTO
     * @return result
     */
    public static boolean createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();

        customer.setUserId(customerDTO.getCustomerId());
        customer.setUsername(customerDTO.getUsername());
        customer.setuPassword(customerDTO.getuPassword());
        customer.setuFname(customerDTO.getuFname());
        customer.setuLname(customerDTO.getuLname());
        customer.setBirthday(customerDTO.getBirthday());
        customer.setUserEmail(customerDTO.getUserEmail());
        customer.setAddress(customerDTO.getAddress());

        return new CustomerService().insertUser(customer);
    }

    /**
     * update customer from remote call
     *
     * @param customerDTO Customer DTO
     * @return result
     */
    public static boolean updateCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();

        customer.setUserId(customerDTO.getCustomerId());
        customer.setUsername(customerDTO.getUsername());
        customer.setuPassword(customerDTO.getuPassword());
        customer.setuFname(customerDTO.getuFname());
        customer.setuLname(customerDTO.getuLname());
        customer.setBirthday(customerDTO.getBirthday());
        customer.setUserEmail(customerDTO.getUserEmail());
        customer.setAddress(customerDTO.getAddress());

        return new CustomerService().updateUser(customer);
    }

    /**
     * delete customer from remote call
     *
     * @param customerDTO Customer DTO
     * @return result
     */
    public static boolean deleteCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();

        customer.setUserId(customerDTO.getCustomerId());
        customer.setUsername(customerDTO.getUsername());
        customer.setuPassword(customerDTO.getuPassword());
        customer.setuFname(customerDTO.getuFname());
        customer.setuLname(customerDTO.getuLname());
        customer.setBirthday(customerDTO.getBirthday());
        customer.setUserEmail(customerDTO.getUserEmail());
        customer.setAddress(customerDTO.getAddress());

        return new CustomerService().deleteUser(customer);
    }

}
