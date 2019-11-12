package service;

import domain.Category;
import domain.Product;
import mapper.ProductMapper;
import util.IdentityMap;
import util.UnitOfWork;

import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: Product service to manager logic method related to product
 * @author: DennyLee
 * @create: 2019-09-06 00:15
 **/
public class ProductService {
    private ProductMapper productMapper;

    public ProductService() {
        productMapper = new ProductMapper();
    }

    /**
     * get all products
     *
     * @return a list of product objects or null
     */
    public List<Product> getAll() {
        return productMapper.getAllProducts();
    }

    /**
     * get products where inventory is greater than 0
     *
     * @return a list of product objects or null
     */
    public List<Product> getAllAvailableProducts() {
        return productMapper.getAllAvailableProducts();
    }

    /**
     * find a product by product id
     *
     * @param product Product
     * @return a product object or null
     */
    public Product findProductByID(Product product) {
        IdentityMap<Product> identityMap = IdentityMap.getInstance(product);
        Product productFinded = identityMap.get(product.getId());
        if (productFinded != null) {
            return productFinded;
        }
        return productMapper.findProductById(product);
    }

    /**
     * find a product by product name
     *
     * @param product Product
     * @return a product object or null
     */
    public Product findProductByName(Product product) {
        return productMapper.findProductByName(product);
    }

    /**
     * find all products related to a category
     *
     * @param category Category
     * @return a product object or null
     */
    public List<Product> findProductByCategory(Category category) {
        return productMapper.findProductsByCategory(category);
    }


    /**
     * create a product, apply unit of work
     *
     * @param product Staff
     * @return result
     */
    public boolean insertProduct(Product product) {
        UnitOfWork.newCurrent();
        UnitOfWork.getCurrent().registerNew(product);
        return UnitOfWork.getCurrent().commit();
    }

    /**
     * delete a product, apply unit of work
     *
     * @param product Staff
     * @return result
     */
    public boolean deleteProduct(Product product) {
        UnitOfWork.newCurrent();
        UnitOfWork.getCurrent().registerDelete(product);
        return UnitOfWork.getCurrent().commit();
    }

    /**
     * update a product, apply unit of work
     *
     * @param product Staff
     * @return result
     */
    public boolean updateProduct(Product product) {
        UnitOfWork.newCurrent();
        UnitOfWork.getCurrent().registerDirty(product);
        return UnitOfWork.getCurrent().commit();
    }

    /**
     * delete a relation between a product and a category
     *
     * @param product  Product
     * @param category Category
     * @return result
     */
    public boolean deleteRelation(Product product, Category category) {
        return productMapper.deleteRelation(product, category);

    }

    /**
     * add a relation between a product and a category
     *
     * @param product  Product
     * @param category Category
     * @return result
     */
    public boolean addRelation(Product product, Category category) {
        return productMapper.addRelation(product, category);
    }

    /**
     * add a relation between a product and a category
     *
     * @param product  Product
     * @param category Category
     * @return result
     */
    public boolean findRelation(Product product, Category category) {
        return productMapper.findRelation(product, category);
    }

    /**
     * delete all relations between a product and categories
     *
     * @param product Product
     * @return result
     */
    public boolean deleteAllRelations(Product product) {
        return productMapper.deleteAllRelationsByProduct(product);
    }

}
