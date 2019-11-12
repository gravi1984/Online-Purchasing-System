package domain;

import mapper.ProductMapper;

import java.util.Date;
import java.util.UUID;

/**
 * @program: CoffeeWeb
 * @description: Product object
 * @author: DennyLee
 * @create: 2019-09-01 20:11
 **/
public class Product extends DomainObject {
    //product id
    private String productId;
    //product name
    private String productName;
    //product info
    private String info;
    //product price
    private double price;
    //product weight
    private int weight;
    //product create time
    private Date createdAt;
    //product inventory
    private int inventory;

    //constructor
    public Product() {
    }

    //constructor with product name, info, price, weight, inventory
    public Product(String pName, String info, double price, int weight, int inventory) {
        this.productId = UUID.randomUUID().toString();
        this.productName = pName;
        this.info = info;
        this.price = price;
        this.weight = weight;
        this.createdAt = new Date();
        this.inventory = inventory;
    }

    //getter and setter methods
    @Override
    public String getId() {
        return productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getInfo() {
        if (this.info == null)
            load();
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getPrice() {
        if (this.price == 0.0)
            load();
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getWeight() {
        if (this.weight == 0)
            load();
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getProductName() {
        if (this.productName == null)
            load();
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getCreatedAt() {
        if (this.createdAt == null)
            load();
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    //use lazy load to reduce request
    private void load() {
        ProductMapper productMapper = new ProductMapper();
        Product record = productMapper.findProductById(this);
        if (this.info == null)
            this.info = record.getInfo();
        if (this.price == 0)
            this.price = record.getPrice();
        if (this.weight == 0)
            this.weight = record.getWeight();
        if (this.productName == null)
            this.productName = record.getProductName();
        if (this.createdAt == null) {
            this.createdAt = record.createdAt;
        }

    }
}
