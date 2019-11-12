package dto;

import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: Order Data transfer object
 * @author: DennyLee
 * @create: 2019-10-10 00:35
 **/
public class OrderDTO {

    //id
    private String orderId;
    //customer who ordered the id
    private CustomerDTO orderedBy;
    //total price
    private double totalPrice;
    //order time
    private Date orderTime;
    //address
    private String address;
    //status
    private String status;
    //update time
    private Date updateTime;
    //orderDetailDTOs of order details in the order
    private List<OrderDetailDTO> orderDetails;

    //getter and setter method
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public CustomerDTO getCustomerDTO() {
        return orderedBy;
    }

    public void setCustomerDTO(CustomerDTO customer) {
        this.orderedBy = customer;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    //serialize method
    public static String serialize(OrderDTO orderDTO) {
        Gson gson = new Gson();
        return gson.toJson(orderDTO);

    }

    //deserialize method
    public static OrderDTO deserialize(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, OrderDTO.class);
    }
}
