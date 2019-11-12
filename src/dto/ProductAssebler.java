package dto;

import domain.Category;
import domain.Product;
import service.CategoryService;
import service.ProductService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description:Functions of ProductDTO
 * @author: DennyLee
 * @create: 2019-10-09 13:45
 **/
public class ProductAssebler {

    /**
     * create ProductDTO from product
     *
     * @param product Product
     * @return ProductDTO
     */
    public static ProductDTO createProductDTO(Product product) {
        ProductDTO result = new ProductDTO();
        result.setProductName(product.getProductName());
        result.setProductId(product.getProductId());
        result.setInfo(product.getInfo());
        result.setPrice(product.getPrice());
        result.setWeight(product.getWeight());
        result.setCreatedAt(product.getCreatedAt());
        result.setInventory(product.getInventory());
        List<Category> categories = new CategoryService().findCategoryByProduct(product);
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        for (Category category : categories
        ) {
            categoryDTOs.add(CategoryAssembler.createCategoryDTO(category));
        }
        result.setCategoryDTO(categoryDTOs);
        return result;
    }

    /**
     * create product form remote call
     *
     * @param productDTO ProductDTO
     * @return result
     */
    public static boolean createProduct(ProductDTO productDTO) {
        Product product = new Product();

        product.setProductId(productDTO.getProductId());
        product.setProductName(productDTO.getProductName());
        product.setInfo(productDTO.getInfo());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setCreatedAt(new Date());
        product.setInventory(productDTO.getInventory());
        boolean result = updateProductCategoryRelation(product, productDTO.getCategoryDTO());
        if (result)
            return new ProductService().insertProduct(product);
        else
            return false;
    }

    /**
     * update product form remote call
     *
     * @param productDTO ProductDTO
     * @return result
     */
    public static boolean updateProduct(ProductDTO productDTO) {
        Product product = new Product();

        product.setProductId(productDTO.getProductId());
        product.setProductName(productDTO.getProductName());
        product.setInfo(productDTO.getInfo());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setCreatedAt(new Date());
        product.setInventory(productDTO.getInventory());
        boolean result = updateProductCategoryRelation(product, productDTO.getCategoryDTO());
        if (result)
            return new ProductService().updateProduct(product);
        else
            return false;
    }

    /**
     * delete product form remote call
     *
     * @param productDTO ProductDTO
     * @return result
     */
    public static boolean deleteProduct(ProductDTO productDTO) {
        Product product = new Product();

        product.setProductId(productDTO.getProductId());
        product.setProductName(productDTO.getProductName());
        product.setInfo(productDTO.getInfo());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setCreatedAt(new Date());
        product.setInventory(productDTO.getInventory());

        return new ProductService().deleteProduct(product);
    }

    /**
     * update product and category relations by remote call
     * @param product Product
     * @param categoryDTOs list of category dto
     * @return result
     */
    private static boolean updateProductCategoryRelation(Product product, List<CategoryDTO> categoryDTOs) {
        ProductService productService = new ProductService();
        boolean result = productService.deleteAllRelations(product);
        for (CategoryDTO categoryDTO : categoryDTOs) {
            Category category = new Category();
            category.setCategoryId(categoryDTO.getCategoryId());
            result = result && productService.addRelation(product, category);
        }
        return result;
    }
}
