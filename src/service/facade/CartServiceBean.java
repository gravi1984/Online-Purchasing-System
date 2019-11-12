package service.facade;

import domain.Cart;
import dto.CartAssembler;
import dto.CartDTO;
import service.CartService;

import java.rmi.RemoteException;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description:
 * @author: DennyLee
 * @create: 2019-10-10 01:18
 **/
public class CartServiceBean {
    public String findCartById(String id) throws RemoteException {
        Cart cart = new Cart();
        cart.setCartId(id);
        CartDTO cartDTO = CartAssembler.createCartDTO(new CartService().findCartById(cart));
        return CartDTO.serialize(cartDTO);
    }

    public boolean addCartDetail(String json){
        CartDTO cartDTO = CartDTO.deserialize(json);
        return CartAssembler.addCartDetail(cartDTO);
    }

    public boolean updateCartDetail(String json){
        CartDTO cartDTO = CartDTO.deserialize(json);
        return CartAssembler.updateCartDetail(cartDTO);
    }

}
