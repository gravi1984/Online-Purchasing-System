package dto;

import domain.*;
import service.CategoryService;
import service.CustomerService;
import service.OrderService;
import service.ProductService;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description:Functions of OrderDTO
 * @author: DennyLee
 * @create: 2019-10-09 23:18
 **/
public class OrderAssebler {
    /**
     * create OrderDTO from order
     *
     * @param order Order
     * @return OrderDTO
     */
    public static OrderDTO createOrderDTO(Order order) {
        OrderDTO result = new OrderDTO();
        result.setOrderId(order.getId());
        result.setCustomerDTO(CustomerAssembler.createCustomerDTO(order.getCustomer()));
        result.setTotalPrice(order.getTotalPrice());
        result.setOrderTime(order.getOrderTime());
        result.setAddress(order.getAddress());
        result.setStatus(order.getStatus());
        result.setUpdateTime(order.getUpdateTime());
        List<OrderDetail> orderDetails = new OrderService().findOrderDetailsByOrderId(order);
        List<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
        for (OrderDetail orderdetail : orderDetails) {
            orderDetailDTOS.add(createOrderDetailDTO(orderdetail));
        }
        result.setOrderDetails(orderDetailDTOS);
        return result;
    }

    /**
     * create OrderDetailDTO from order
     *
     * @param orderDetail OrderDetail
     * @return OrderDetailDTO
     */
    private static OrderDetailDTO createOrderDetailDTO(OrderDetail orderDetail) {
        OrderDetailDTO result = new OrderDetailDTO();
        result.setProduct(ProductAssebler.createProductDTO(orderDetail.getProduct()));
        result.setAmount(orderDetail.getProductAmount());
        result.setCategory(CategoryAssembler.createCategoryDTO(orderDetail.getProductCategory()));
        return result;
    }

    /**
     * create order from remote call
     *
     * @param orderDTO OrderDTO
     * @return result
     */
    public static boolean createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        boolean result = true;
        order.setOrderId(orderDTO.getOrderId());
        Customer customer = new Customer();
        customer.setUserId(orderDTO.getCustomerDTO().getCustomerId());
        order.setCustomer(new CustomerService().findUserById(customer));
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setUpdateTime(orderDTO.getUpdateTime());
        order.setOrderTime(orderDTO.getOrderTime());
        order.setAddress(orderDTO.getAddress());
        order.setStatus(orderDTO.getStatus());
        OrderService orderService = new OrderService();
        result = orderService.insertOrder(order);
        List<OrderDetailDTO> orderDetailDTOS = orderDTO.getOrderDetails();
        for (OrderDetailDTO orderDetailDTO : orderDetailDTOS
        ) {
            result = result && createOrderDetail(orderDetailDTO);
        }
        return result;
    }

    /**
     * create orderDetail from remote call
     *
     * @param orderDetailDTO OrederDetailDTO
     * @return result
     */
    private static boolean createOrderDetail(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = new OrderDetail();
        Order order = new Order();
        orderDetail.setOrder(new OrderService().findOrderById(order));
        Product product = new Product();
        product.setProductId(orderDetailDTO.getProduct().getProductId());
        orderDetail.setProduct(new ProductService().findProductByID(product));
        orderDetail.setProductAmount(orderDetailDTO.getAmount());
        Category category = new Category();
        category.setCategoryId(orderDetailDTO.getCategory().getCategoryId());
        orderDetail.setProductCategory(new CategoryService().findCategroyById(category));

        return new OrderService().insertOrderDetail(orderDetail);
    }

    /**
     * update order from remote call
     *
     * @param orderDTO OrderDTO
     * @return result
     */
    public static boolean updateOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderId(orderDTO.getOrderId());
        Customer customer = new Customer();
        customer.setUserId(orderDTO.getCustomerDTO().getCustomerId());
        order.setCustomer(new CustomerService().findUserById(customer));
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setUpdateTime(orderDTO.getUpdateTime());
        order.setOrderTime(orderDTO.getOrderTime());
        order.setAddress(orderDTO.getAddress());
        order.setStatus(orderDTO.getStatus());
        return new OrderService().updateOrderById(order);
    }

}
