package service.facade;

import domain.Product;
import dto.ProductAssebler;
import dto.ProductDTO;
import service.ProductService;

import java.rmi.RemoteException;

/**
 * @program: CoffeeWeb
 * @description:
 * @author: DennyLee
 * @create: 2019-10-10 01:24
 **/
public class ProductServiceBean {
    public String findProductById(String id) throws RemoteException{
        Product product = new Product();
        product.setProductId(id);
        product = new ProductService().findProductByID(product);
        ProductDTO productDTO =  ProductAssebler.createProductDTO(product);
        return ProductDTO.serialize(productDTO);
    }

    //remote call addCategory service
    public boolean addCustomer(String id, String json){
        ProductDTO productDTO = ProductDTO.deserialize(json);
        return ProductAssebler.createProduct(productDTO);

    }
    //remote call deleteCategory service
    public boolean deleteCustomer(String id, String json){
        ProductDTO productDTO = ProductDTO.deserialize(json);
        return ProductAssebler.deleteProduct(productDTO);

    }
    //remote call updateCategory service
    public boolean updateCustomer(String id, String json){
        ProductDTO productDTO = ProductDTO.deserialize(json);
        return ProductAssebler.updateProduct(productDTO);
    }
}
