package domain;/**
 * Created by DennyLee on 2019/9/1.
 */

import mapper.OrderMapper;

import java.util.Date;
import java.util.UUID;

/**
 * @program: CoffeeWeb
 * @description: Order object
 * @author: DennyLee
 * @create: 2019-09-01 22:51
 **/
public class Order extends DomainObject {
    //order id
    private String orderId;
    //the customer a order belongs to
    private Customer customer;
    //total price of order
    private double totalPrice;
    //date of the order
    private Date orderTime;
    private String address;
    private String status;
    private Date updateTime;


    //constructor
    public Order() {
    }

    //constructor with customer, total price, order time
    public Order(Customer customer, double totalPrice, String address, String status) {
        this.orderId = UUID.randomUUID().toString();
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.orderTime = this.updateTime = new Date();
        this.address = address;
        this.status = status;
    }

    //getter and setter method
    @Override
    public String getId() {
        return orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        if (this.customer == null)
            load();
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getTotalPrice() {
        if (this.totalPrice == 0.0) {
            load();
        }
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderTime() {
        if (this.orderTime == null)
            load();
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getAddress() {
        if (this.address == null)
            load();
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        if (this.status == null)
            load();
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        if (this.updateTime == null)
            load();
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    //use lazy load to reduce request
    private void load() {
        OrderMapper orderMapper = new OrderMapper();
        Order record = orderMapper.findOrderById(this);
        if (this.orderTime == null) {
            this.orderTime = record.getOrderTime();
        }
        if (this.totalPrice == 0.0) {
            this.totalPrice = record.getTotalPrice();
        }
        if (this.customer == null) {
            this.customer = record.getCustomer();
        }
        if (this.address == null) {
            this.address = record.getAddress();
        }
        if (this.status == null) {
            this.status = record.getStatus();
        }
        if (this.updateTime == null) {
            this.updateTime = record.getUpdateTime();
        }
    }
}
