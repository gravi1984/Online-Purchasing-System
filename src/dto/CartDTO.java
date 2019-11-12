package dto;

import com.google.gson.Gson;

import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: Cart Data transfer object
 * @author: DennyLee
 * @create: 2019-10-10 00:35
 **/
public class CartDTO {
    //id
    private String cartId;
    //customerDTO of the customer who has the cart
    private CustomerDTO customerDTO;
    //cartDetailDTOs of cartDetails in cart
    private List<CartDetailDTO> cartDetailDTOs;

    //getter and setter method
    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }


    public List<CartDetailDTO> getCartDetailDTOs() {
        return cartDetailDTOs;
    }


    public void setCartDetailDTOs(List<CartDetailDTO> cartDetailDTOs) {
        this.cartDetailDTOs = cartDetailDTOs;
    }

    //serialize method
    public static String serialize(CartDTO cartDTO) {
        Gson gson = new Gson();
        return gson.toJson(cartDTO);

    }

    //deserialize method
    public static CartDTO deserialize(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, CartDTO.class);
    }
}
