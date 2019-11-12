package domain;

import mapper.CartMapper;

import java.util.UUID;

/**
 * @program: CoffeeWeb
 * @description: Cart Domain
 * @author: DennyLee
 * @create: 2019-09-14 14:00
 **/
public class Cart extends DomainObject{
    //cart id
    private String cartId;
    //customer id the cart belongs to
    private Customer customer;

    //constructor
    public Cart() {
    }

    //constructor with customer
    public Cart(Customer customer) {
        this.cartId = UUID.randomUUID().toString();
        this.customer = customer;
    }

    //getter and setter method
    @Override
    public String getId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Customer getCustomer() {
        if (this.customer == null){
            load();
        }
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    //use lazy load to reduce request
    private void load() {
        CartMapper cartMapper = new CartMapper();
        Cart record = cartMapper.findCartById(this);

        if (this.customer == null) {
            this.customer = record.getCustomer();
        }

    }
}


