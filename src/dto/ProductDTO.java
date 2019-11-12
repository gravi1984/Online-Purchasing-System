package dto;

import com.google.gson.Gson;
import domain.Category;
import domain.Product;

import java.util.Date;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: Product Data transfer object
 * @author: DennyLee
 * @create: 2019-10-10 00:35
 **/
public class ProductDTO {

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
    //categoryDTOs of category the product has
    private List<CategoryDTO> categoryDTO;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getCreatedAt() {
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


    public List<CategoryDTO> getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(List<CategoryDTO> categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    //serialize method
    public static String serialize(ProductDTO productDTO) {
        Gson gson = new Gson();
        return gson.toJson(productDTO);

    }

    //deserialize method
    public static ProductDTO deserialize(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, ProductDTO.class);
    }
}
