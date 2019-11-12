package dto;

import com.google.gson.Gson;
import domain.Cart;
import domain.CartDetail;
import domain.Category;

/**
 * @program: CoffeeWeb
 * @description: CartDetail Data transfer object
 * @author: DennyLee
 * @create: 2019-10-10 00:36
 **/
public class CartDetailDTO {

    //id
    private String cartDetailId;
    //productDTO of the product in cart
    private ProductDTO productDTO;
    //quantity of product in cart
    private int amount;
    //total price of cart detail
    private double totalPrice;
    //categoryDTO of category of product in cart
    private CategoryDTO categoryDTO;

    //getter and setter method
    public String getCartDetailId() {
        return cartDetailId;
    }

    public void setCartDetailId(String cartDetailId) {
        this.cartDetailId = cartDetailId;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    //serialize method
    public static String serialize(CartDetailDTO cartDetailDTO){
        Gson gson = new Gson();
        return gson.toJson(cartDetailDTO);

    }

    //serialize method
    public static CartDetailDTO deserialize(String jsonString){
        Gson gson = new Gson();
        return gson.fromJson(jsonString, CartDetailDTO.class);
    }
}
