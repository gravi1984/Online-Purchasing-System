package mapper;

import domain.Category;
import domain.DomainObject;
import domain.Product;
import util.DBConnection;
import util.IdentityMap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: data mapper for product
 * @author: DennyLee
 * @create: 2019-09-02 23:56
 **/
public class ProductMapper extends DataMapper {

    /**
     * insert a product into table product
     *
     * @param domainObject Product
     * @return result
     */
    @Override
    public boolean insert(DomainObject domainObject) {
        Product product = (Product) domainObject;
        String insertProduct = "INSERT INTO public.product " +
                "(product_id, product_name, info, price, weight, created_at,inventory)" +
                "VALUES(?,?,?,?,?,?,?)";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(insertProduct);
            preparedStatement.setString(1, product.getId());
            preparedStatement.setString(2, product.getProductName());
            preparedStatement.setString(3, product.getInfo());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setInt(5, product.getWeight());
            preparedStatement.setTimestamp(6, new Timestamp(new Date().getTime()));
            preparedStatement.setInt(7, product.getInventory());
            result = preparedStatement.executeUpdate()==1;

            DBConnection.dbConnection.commit();
        } catch (SQLException e) {
            try {
                System.out.println("Rollback");
                DBConnection.dbConnection.rollback();
            } catch (SQLException ignored) {
                System.out.println("Rollback failed.");
            }
            result = false;
            e.printStackTrace();
        } finally {
            try {
                if (DBConnection.dbConnection != null) DBConnection.dbConnection.close();
            } catch (SQLException ignored) {
            }
        }
        return result;
    }

    /**
     * delete a product from table product
     *
     * @param domainObject Product
     * @return result
     */
    @Override
    public boolean delete(DomainObject domainObject) {
        Product product = (Product) domainObject;
        String deleteProductById = "DELETE FROM public.product WHERE product_id = ?";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(deleteProductById);
            preparedStatement.setString(1, product.getId());
            result = preparedStatement.executeUpdate()==1;

            DBConnection.dbConnection.commit();
        } catch (SQLException e) {
            try {
                System.out.println("Rollback");
                DBConnection.dbConnection.rollback();
            } catch (SQLException ignored) {
                System.out.println("Rollback failed.");
            }
            result = false;
            e.printStackTrace();
        } finally {
            try {
                if (DBConnection.dbConnection != null) DBConnection.dbConnection.close();
            } catch (SQLException ignored) {
            }
        }
        return result;
    }

    /**
     * update a product in table product
     *
     * @param domainObject Product
     * @return result
     */
    @Override
    public boolean update(DomainObject domainObject) {
        Product product = (Product) domainObject;
        String updateProductById = "UPDATE public.product SET " +
                "product_name=?, info=?, price=?, weight=?, created_at=? ,inventory=?" +
                "WHERE product_id=?";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(updateProductById);
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setString(2, product.getInfo());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getWeight());
            preparedStatement.setTimestamp(5, new Timestamp(product.getCreatedAt().getTime()));
            preparedStatement.setInt(6, product.getInventory());
            preparedStatement.setString(7, product.getId());
            result = preparedStatement.executeUpdate() ==1;

            DBConnection.dbConnection.commit();
        } catch (SQLException e) {
            try {
                System.out.println("Rollback");
                DBConnection.dbConnection.rollback();
            } catch (SQLException ignored) {
                System.out.println("Rollback failed.");
            }
            result = false;
            e.printStackTrace();
        } finally {
            try {
                if (DBConnection.dbConnection != null) DBConnection.dbConnection.close();
            } catch (SQLException ignored) {
            }
        }
        return result;
    }

    /**
     * find all products belongs to a category from product_category relation table
     *
     * @param category Category
     * @return a list of products or null
     */
    public List<Product> findProductsByCategory(Category category) {
        String findProductByCategoryId = "SELECT product_id FROM public.product_category WHERE " +
                "category_id = ?";
        List<Product> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findProductByCategoryId);
            preparedStatement.setString(1, category.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getString(1));
                product = findProductById(product);
                result.add(product);
            }
            DBConnection.close(preparedStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get all products in table product
     *
     * @return a list of products or null
     */
    public List<Product> getAllProducts() {
        String findProduct = "SELECT * FROM public.product";
        List<Product> result = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findProduct);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product1 = new Product();
                IdentityMap<Product> identityMap = IdentityMap.getInstance(product1);

                product1.setProductId(resultSet.getString(1));
                product1.setProductName(resultSet.getString(2));
                product1.setInfo(resultSet.getString(3));
                product1.setPrice(resultSet.getDouble(4));
                product1.setWeight(resultSet.getInt(5));
                product1.setCreatedAt(resultSet.getTimestamp(6));
                product1.setInventory(resultSet.getInt(7));

                identityMap.put(product1.getId(), product1);
                result.add(product1);
            }
            DBConnection.close(preparedStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get all available products in table product
     *
     * @return a list of products or null
     */
    public List<Product> getAllAvailableProducts() {
        String findProduct = "SELECT * FROM public.product WHERE inventory > 0";
        List<Product> result = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findProduct);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product1 = new Product();
                IdentityMap<Product> identityMap = IdentityMap.getInstance(product1);

                product1.setProductId(resultSet.getString(1));
                product1.setProductName(resultSet.getString(2));
                product1.setInfo(resultSet.getString(3));
                product1.setPrice(resultSet.getDouble(4));
                product1.setWeight(resultSet.getInt(5));
                product1.setCreatedAt(resultSet.getTimestamp(6));
                product1.setInventory(resultSet.getInt(7));

                identityMap.put(product1.getId(), product1);
                result.add(product1);
            }
            DBConnection.close(preparedStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * find a product by product id in product table
     *
     * @param domainObject Product
     * @return a product object or null
     */
    public Product findProductById(DomainObject domainObject) {
        Product product = (Product) domainObject;
        String findProductByCategoryId = "SELECT * FROM public.product WHERE product_id = ?";
        Product result = new Product();

        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findProductByCategoryId);
            preparedStatement.setString(1, product.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                IdentityMap<Product> identityMap = IdentityMap.getInstance(result);
                result.setProductId(resultSet.getString(1));
                result.setProductName(resultSet.getString(2));
                result.setInfo(resultSet.getString(3));
                result.setPrice(resultSet.getDouble(4));
                result.setWeight(resultSet.getInt(5));
                result.setCreatedAt(resultSet.getTimestamp(6));
                result.setInventory(resultSet.getInt(7));
                identityMap.put(result.getId(), result);
            }
            DBConnection.close(preparedStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * find a product by product name
     *
     * @param domainObject Product
     * @return a product object or null
     */
    public Product findProductByName(DomainObject domainObject) {
        Product product = (Product) domainObject;
        String findProductByCategoryName = "SELECT * FROM public.product WHERE product_name = ?";

        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findProductByCategoryName);
            preparedStatement.setString(1, product.getProductName());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Product result = new Product();
                IdentityMap<Product> identityMap = IdentityMap.getInstance(result);

                result.setProductId(resultSet.getString(1));
                result.setProductName(resultSet.getString(2));
                result.setInfo(resultSet.getString(3));
                result.setPrice(resultSet.getDouble(4));
                result.setWeight(resultSet.getInt(5));
                result.setCreatedAt(resultSet.getTimestamp(6));
                result.setInventory(resultSet.getInt(7));

                identityMap.put(result.getId(), result);
                return result;
            }
            DBConnection.close(preparedStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * delete a category product relation in category relation table
     *
     * @param product  Product
     * @param category Category
     * @return result
     */
    public boolean deleteRelation(Product product, Category category) {
        String deleteRelationByProduct = "DELETE FROM public.product_category WHERE " +
                "category_id = ? and product_id = ?";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(deleteRelationByProduct);
            preparedStatement.setString(1, category.getId());
            preparedStatement.setString(2, product.getId());
             result = preparedStatement.executeUpdate() ==1;

            DBConnection.dbConnection.commit();
        } catch (SQLException e) {
            try {
                System.out.println("Rollback");
                DBConnection.dbConnection.rollback();
            } catch (SQLException ignored) {
                System.out.println("Rollback failed.");
            }
            result = false;
            e.printStackTrace();
        } finally {
            try {
                if (DBConnection.dbConnection != null) DBConnection.dbConnection.close();
            } catch (SQLException ignored) {
            }
        }
        return result;
    }

    /**
     * delete all category product relations in category relation table
     *
     * @param product Product
     * @return result
     */
    public boolean deleteAllRelationsByProduct(Product product) {
        String deleteRelationByProduct = "DELETE FROM public.product_category WHERE " +
                "product_id = ?";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(deleteRelationByProduct);
            preparedStatement.setString(1, product.getId());
             result = preparedStatement.executeUpdate() ==1;

            DBConnection.dbConnection.commit();
        } catch (SQLException e) {
            try {
                System.out.println("Rollback");
                DBConnection.dbConnection.rollback();
            } catch (SQLException ignored) {
                System.out.println("Rollback failed.");
            }
            result = false;
            e.printStackTrace();
        } finally {
            try {
                if (DBConnection.dbConnection != null) DBConnection.dbConnection.close();
            } catch (SQLException ignored) {
            }
        }
        return result;
    }

    /**
     * add a category product relation into category product relation table
     *
     * @param product  Product
     * @param category Category
     * @return result
     */
    public boolean addRelation(Product product, Category category) {
        String deleteRelationByProduct = "INSERT INTO public.product_category " +
                "(category_id, product_id) " +
                "VALUES (?,?)";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(deleteRelationByProduct);
            preparedStatement.setString(1, category.getId());
            preparedStatement.setString(2, product.getId());
             result = preparedStatement.executeUpdate() ==1;

            DBConnection.dbConnection.commit();
        } catch (SQLException e) {
            try {
                System.out.println("Rollback");
                DBConnection.dbConnection.rollback();
            } catch (SQLException ignored) {
                System.out.println("Rollback failed.");
            }
            result = false;
            e.printStackTrace();
        } finally {
            try {
                if (DBConnection.dbConnection != null) DBConnection.dbConnection.close();
            } catch (SQLException ignored) {
            }
        }
        return result;
    }

    /**
     * find a category product relation into category product relation table
     *
     * @param product  Product
     * @param category Category
     * @return result
     */
    public boolean findRelation(Product product, Category category) {
        String findRelation = "SELECT * FROM public.product_category " +
                "WHERE category_id = ? and product_id = ?";
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findRelation);
            preparedStatement.setString(1, category.getId());
            preparedStatement.setString(2, product.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return true;
            DBConnection.close(preparedStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

