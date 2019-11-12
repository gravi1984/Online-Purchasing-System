package mapper;

import domain.*;
import util.DBConnection;
import util.IdentityMap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: Data mapper for cart detail
 * @author: DennyLee
 * @create: 2019-09-02 23:54
 **/
public class CartDetailMapper extends DataMapper {

    /**
     * insert a cart item to table cart_detail
     *
     * @param domainObject CartDetail
     * @return result
     */
    @Override
    public boolean insert(DomainObject domainObject) {
        CartDetail cartDetail = (CartDetail) domainObject;
        String insertCart = "INSERT INTO public.cart_detail " +
                "(cart_detail_id, product_id, amount, sub_total, cart_id, category_id)" +
                "VALUES(?,?,?,?,?,?)";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(insertCart);
            preparedStatement.setString(1, cartDetail.getId());
            preparedStatement.setString(2, cartDetail.getProduct().getId());
            preparedStatement.setInt(3, cartDetail.getProductAmount());
            preparedStatement.setDouble(4, cartDetail.getTotalPrice());
            preparedStatement.setString(5, cartDetail.getCart().getId());
            preparedStatement.setString(6, cartDetail.getCategory().getId());
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
     * delete a cart to table cart_detail
     *
     * @param domainObject CartDetail
     * @return result
     */
    @Override
    public boolean delete(DomainObject domainObject) {
        CartDetail cartDetail = (CartDetail) domainObject;
        String deleteCart = "DELETE FROM public.cart_detail WHERE cart_detail_id = ?";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(deleteCart);
            preparedStatement.setString(1, cartDetail.getId());
            result = preparedStatement.executeUpdate() ==1 ;
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
     * update a cart detail by id
     *
     * @param domainObject CartDetail
     * @return result
     */
    @Override
    public boolean update(DomainObject domainObject) {
        CartDetail cartDetail = (CartDetail) domainObject;
        String updateCartById = "UPDATE public.cart_detail SET " +
                "product_id = ?, amount = ?, sub_total = ?, cart_id = ?, category_id = ?" +
                "WHERE cart_detail_id = ?";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(updateCartById);
            preparedStatement.setString(1, cartDetail.getProduct().getId());
            preparedStatement.setInt(2, cartDetail.getProductAmount());
            preparedStatement.setDouble(3, cartDetail.getTotalPrice());
            preparedStatement.setString(4, cartDetail.getCart().getId());
            preparedStatement.setString(5, cartDetail.getCategory().getId());
            preparedStatement.setString(6, cartDetail.getId());
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
     * find a cart detail by id in table cart_detail
     *
     * @param domainObject CartDetail
     * @return a CartDetail object or null
     */
    public CartDetail findCartDetailById(DomainObject domainObject) {
        CartDetail cartDetail = (CartDetail) domainObject;
        String findCartById = "SELECT * FROM public.cart_detail WHERE cart_detail_id = ?";
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findCartById);
            preparedStatement.setString(1, cartDetail.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                CartDetail result = new CartDetail();
                IdentityMap<CartDetail> identityMap = IdentityMap.getInstance(result);
                result.setCartDetailId(resultSet.getString(1));

                Product product1 = new Product();
                product1.setProductId(resultSet.getString(2));
                ProductMapper productMapper = new ProductMapper();
                result.setProduct(productMapper.findProductById(product1));

                result.setProductAmount(resultSet.getInt(3));
                result.setTotalPrice(resultSet.getDouble(4));

                Cart cart = new Cart();
                cart.setCartId(resultSet.getString(5));
                CartMapper cartMapper = new CartMapper();
                result.setCart(cartMapper.findCartById(cart));

                Category category = new Category();
                category.setCategoryId(resultSet.getString(6));
                CategoryMapper categoryMapper = new CategoryMapper();
                result.setCategory(categoryMapper.findCategoryById(category));
                identityMap.put(result.getId(), result);

                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * find if a product exist in cart in cart_detail table
     *
     * @param cart     Cart
     * @param product  Product
     * @param category Category
     * @return a CartDetail object or null
     */
    public CartDetail findProductInCart(Cart cart, Product product, Category category) {
        String findProductInCart = "SELECT * FROM public.cart_detail " +
                "WHERE product_id=? AND cart_id=? and category_id = ?;";
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findProductInCart);
            preparedStatement.setString(1, product.getId());
            preparedStatement.setString(2, cart.getId());
            preparedStatement.setString(3, category.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                CartDetail result = new CartDetail();
                IdentityMap<CartDetail> identityMap = IdentityMap.getInstance(result);
                result.setCartDetailId(resultSet.getString(1));

                Product product1 = new Product();
                product1.setProductId(resultSet.getString(2));
                ProductMapper productMapper = new ProductMapper();
                result.setProduct(productMapper.findProductById(product1));

                result.setProductAmount(resultSet.getInt(3));
                result.setTotalPrice(resultSet.getDouble(4));

                Cart cart1 = new Cart();
                cart.setCartId(resultSet.getString(5));
                CartMapper cartMapper = new CartMapper();
                result.setCart(cartMapper.findCartById(cart1));

                Category category1 = new Category();
                category1.setCategoryId(resultSet.getString(6));
                CategoryMapper categoryMapper = new CategoryMapper();
                result.setCategory(categoryMapper.findCategoryById(category1));
                identityMap.put(result.getId(), result);

                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * find cart details by cart id in cart_detail table
     *
     * @param cart cart
     * @return list of cartDetail or null
     */
    public List<CartDetail> findCartDetailByCartId(Cart cart) {
        String findCartById = "SELECT * FROM public.cart_detail WHERE cart_id = ?";
        List<CartDetail> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findCartById);
            preparedStatement.setString(1, cart.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CartDetail cartDetail1 = new CartDetail();
                IdentityMap<CartDetail> identityMap = IdentityMap.getInstance(cartDetail1);

                cartDetail1.setCartDetailId(resultSet.getString(1));

                Product product1 = new Product();
                product1.setProductId(resultSet.getString(2));
                ProductMapper productMapper = new ProductMapper();
                product1 = productMapper.findProductById(product1);
                cartDetail1.setProduct(product1);

                cartDetail1.setProductAmount(resultSet.getInt(3));
                cartDetail1.setTotalPrice(resultSet.getDouble(4));

                Cart cart1 = new Cart();
                cart.setCartId(resultSet.getString(5));
                CartMapper cartMapper = new CartMapper();
                cartDetail1.setCart(cartMapper.findCartById(cart1));

                Category category = new Category();
                category.setCategoryId(resultSet.getString(6));
                CategoryMapper categoryMapper = new CategoryMapper();
                cartDetail1.setCategory(categoryMapper.findCategoryById(category));

                identityMap.put(cartDetail1.getId(), cartDetail1);
                result.add(cartDetail1);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * update price by product id
     * @param product Product
     * @return result
     */
    public boolean updatePrice(Product product) {
        String updateCartPrice = "UPDATE public.cart_detail SET " +
                "sub_total = amount * ? WHERE product_id = ?";
        int result = 0;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(updateCartPrice);
            preparedStatement.setDouble(1, product.getPrice());
            preparedStatement.setString(2, product.getId());
            result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result != 0;
    }

}
