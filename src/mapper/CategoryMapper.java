package mapper;

import domain.Category;
import domain.DomainObject;
import domain.Product;
import util.DBConnection;
import util.IdentityMap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: Data mapper for category
 * @author: DennyLee
 * @create: 2019-09-02 23:54
 **/
public class CategoryMapper extends DataMapper {

    /**
     * insert a new category to table.category
     *
     * @param domainObject Category
     * @return result
     */
    @Override
    public boolean insert(DomainObject domainObject) {
        Category category = (Category) domainObject;
        String insertNewCategory = "INSERT INTO public.category " +
                "(category_id, category_name)" +
                "VALUES (?,?)";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(insertNewCategory);
            preparedStatement.setString(1, category.getId());
            preparedStatement.setString(2, category.getCategoryName());
            result = preparedStatement.executeUpdate() == 1;
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
     * delete a category from table category
     *
     * @param domainObject Category
     * @return result
     */
    @Override
    public boolean delete(DomainObject domainObject) {
        Category category = (Category) domainObject;
        String deletCategoryById = "DELETE FROM public.category WHERE category_id = ?";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(deletCategoryById);
            preparedStatement.setString(1, category.getId());
            result = preparedStatement.executeUpdate() == 1;
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
     * update a category by category id in table category
     *
     * @param domainObject Category
     * @return result
     */
    @Override
    public boolean update(DomainObject domainObject) {
        Category category = (Category) domainObject;
        String updateCategoryById = "UPDATE public.category SET " +
                "category_name=? WHERE category_id = ?";
        boolean result;
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(updateCategoryById);
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setString(2, category.getId());
            result = preparedStatement.executeUpdate() == 1;

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
     * find a category by category id in table category
     *
     * @param domainObject Category
     * @return a Category object or null
     */
    public Category findCategoryById(DomainObject domainObject) {
        Category category = (Category) domainObject;
        String findCategoryById = "SELECT * FROM public.category WHERE category_id = ?";
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findCategoryById);
            preparedStatement.setString(1, category.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Category result = new Category();
                IdentityMap<Category> identityMap = IdentityMap.getInstance(result);
                result.setCategoryId(resultSet.getString(1));
                result.setCategoryName(resultSet.getString(2));
                identityMap.put(result.getId(), result);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get all categories in table category
     *
     * @return a list of category object or null
     */
    public List<Category> getAllCategories() {
        String findCategoryById = "SELECT * FROM public.category";
        List<Category> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findCategoryById);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category1 = new Category();
                IdentityMap<Category> identityMap = IdentityMap.getInstance(category1);

                category1.setCategoryId(resultSet.getString(1));
                category1.setCategoryName(resultSet.getString(2));

                identityMap.put(category1.getId(), category1);
                result.add(category1);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * find a category by category name in table category
     *
     * @param domainObject Category
     * @return a category object or null
     */
    public Category findCategoryByName(DomainObject domainObject) {
        Category category = (Category) domainObject;
        String findCategoryByCategoryName = "SELECT * FROM public.category WHERE " +
                "category_name = ?";
        try {
            PreparedStatement preparedStatement =
                    DBConnection.prepare(findCategoryByCategoryName);
            preparedStatement.setString(1, category.getCategoryName());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Category result = new Category();
                IdentityMap<Category> identityMap = IdentityMap.getInstance(result);

                result.setCategoryId(resultSet.getString(1));
                result.setCategoryName(resultSet.getString(2));

                //register it into identityMap
                identityMap.put(result.getId(), result);
                DBConnection.close(preparedStatement);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * find all categories of a product in category product relation table
     *
     * @param domainObject Category
     * @return a list of category object or null
     */
    public List<Category> findCategoryByProduct(DomainObject domainObject) {
        Product product = (Product) domainObject;
        String findCategoryByProduct = "SELECT category_id FROM public.product_category WHERE " +
                "product_id = ?";
        try {
            PreparedStatement preparedStatement = DBConnection.prepare(findCategoryByProduct);
            preparedStatement.setString(1, product.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Category> result = new ArrayList<>();
            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getString(1));

                result.add(findCategoryById(category));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
