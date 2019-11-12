package domain;/**
 * Created by DennyLee on 2019/9/1.
 */

import mapper.CartDetailMapper;

import java.util.UUID;

/**
 * @program: CoffeeWeb
 * @description: Shopping cart item object
 * @author: DennyLee
 * @create: 2019-09-01 23:30
 **/
public class CartDetail extends DomainObject {
    //cart id
    private String cartDetailId;
    //product in the cart
    private Product product;
    //amount of the product
    private int productAmount;
    //total price of the product
    private double totalPrice;
    //the cart a cart detail belongs to
    private Cart cart;
    //the category of the product
    private Category category;

    //constructor
    public CartDetail() {
    }

    //constructor with product, amount, price, user, category
    public CartDetail(Product product, int productAmount, double totalPrice, Cart cart,
                      Category category) {
        this.cartDetailId = UUID.randomUUID().toString();
        this.product = product;
        this.productAmount = productAmount;
        this.totalPrice = totalPrice;
        this.cart = cart;
        this.category = category;
    }

    //getter and setter methods
    @Override
    public String getId() {
        return cartDetailId;
    }

    public void setCartDetailId(String cartDetailId) {
        this.cartDetailId = cartDetailId;
    }

    public Product getProduct() {
        if (this.product == null) {
            load();
        }
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductAmount() {
        if (this.productAmount == 0) {
            load();
        }
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public double getTotalPrice() {
        if (this.totalPrice == 0.0)
            load();
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Cart getCart() {
        if (this.cart == null)
            load();
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getCartDetailId() {
        return cartDetailId;
    }

    public Category getCategory() {
        if (this.category == null)
            load();
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    //use lazy load to reduce request
    private void load() {
        CartDetailMapper cartDetailMapper = new CartDetailMapper();
        CartDetail record = cartDetailMapper.findCartDetailById(this);

        if (this.product == null) {
            this.product = record.getProduct();
        }
        if (this.productAmount == 0) {
            this.productAmount = record.getProductAmount();
        }
        if (this.totalPrice == 0.0) {
            this.totalPrice = record.getTotalPrice();
        }
        if (this.cart == null) {
            this.cart = record.getCart();
        }
        if (this.category == null) {
            this.category = record.getCategory();
        }
    }
}
