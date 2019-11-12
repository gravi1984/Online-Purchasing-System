package dto;

import com.google.gson.Gson;

/**
 * @program: CoffeeWeb
 * @description: OrderDetail data transfer object
 * @author: DennyLee
 * @create: 2019-10-09 23:04
 **/
public class OrderDetailDTO {

    //product dto of product in order detail
    private ProductDTO product;

    //quantity
    private int amount;

    //category dto of product in order detail
    private CategoryDTO category;

    //setter and getter methods
    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    //serialize method
    public static String serialize(OrderDetailDTO orderDetailDTO) {
        Gson gson = new Gson();
        return gson.toJson(orderDetailDTO);

    }

    //deserialize method
    public static OrderDetailDTO deserialize(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, OrderDetailDTO.class);
    }
}
