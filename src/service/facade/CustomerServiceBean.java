package service.facade;

import domain.Category;
import domain.Customer;
import dto.CategoryAssembler;
import dto.CategoryDTO;
import dto.CustomerAssembler;
import dto.CustomerDTO;
import service.CategoryService;
import service.CustomerService;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceBean {

    //remote call findCategoryByName service
    public String findCustomerByName(String name) throws RemoteException {
        Customer customer = new Customer();
        customer.setUsername(name);
        CustomerDTO customerDTO =
                CustomerAssembler.createCustomerDTO(new CustomerService().findUserByName(customer));
        return CustomerDTO.serialize(customerDTO);
    }

    //remote call findCategoryById service
    public String findCustomerByID(String id) throws RemoteException {
        Customer customer = new Customer();
        customer.setUsername(id);
        CustomerDTO customerDTO =
                CustomerAssembler.createCustomerDTO(new CustomerService().findUserById(customer));
        return CustomerDTO.serialize(customerDTO);
    }

    //remote call addCategory service
    public boolean addCustomer(String id, String json){
        CustomerDTO customerDTO = CustomerDTO.deserialize(json);
        return CustomerAssembler.createCustomer(customerDTO);

    }
    //remote call deleteCategory service
    public boolean deleteCustomer(String id, String json){
        CustomerDTO customerDTO = CustomerDTO.deserialize(json);
        return CustomerAssembler.deleteCustomer(customerDTO);

    }
    //remote call updateCategory service
    public boolean updateCustomer(String id, String json){
        CustomerDTO customerDTO = CustomerDTO.deserialize(json);
        return CustomerAssembler.updateCustomer(customerDTO);

    }

}
