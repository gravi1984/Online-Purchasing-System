package service;

import domain.Category;
import domain.Product;
import mapper.CategoryMapper;
import util.IdentityMap;
import util.UnitOfWork;

import java.util.List;

/**
 * @program: CoffeeWeb
 * @description: Category service to manager logic method related to category
 * @author: DennyLee
 * @create: 2019-09-06 22:47
 **/
public class CategoryService {
    private CategoryMapper categoryMapper;

    public CategoryService() {
        categoryMapper = new CategoryMapper();
    }

    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

    /**
     * find a category by id, identity map applied
     *
     * @param category Category
     * @return a category object or null
     */
    public Category findCategroyById(Category category) {
        IdentityMap<Category> identityMap = IdentityMap.getInstance(category);
        Category categoryFinded = identityMap.get(category.getId());

        if (categoryFinded != null) {
            return categoryFinded;
        } else {
            return categoryMapper.findCategoryById(category);
        }

    }

    /**
     * find a category by name
     *
     * @param category Category
     * @return a category object or null
     */
    public Category findCategoryByName(Category category) {
        return categoryMapper.findCategoryByName(category);
    }

    /**
     * create a new category, apply unit of work
     *
     * @param category Category
     * @return result
     */
    public boolean newCategory(Category category) {
        UnitOfWork.newCurrent();
        UnitOfWork.getCurrent().registerNew(category);
        return UnitOfWork.getCurrent().commit();
    }

    /**
     * update a category, apply unit of work
     *
     * @param category Category
     * @return result
     */
    public boolean updateCategory(Category category) {
        UnitOfWork.newCurrent();
        UnitOfWork.getCurrent().registerDirty(category);
        return UnitOfWork.getCurrent().commit();
    }

    /**
     * delete a category, apply unit of work
     *
     * @param category Category
     * @return result
     */
    public boolean deleteCategory(Category category) {
        UnitOfWork.newCurrent();
        UnitOfWork.getCurrent().registerDelete(category);
        return UnitOfWork.getCurrent().commit();
    }

    /**
     * find all categories related to a product
     *
     * @param product Product
     * @return result
     */
    public List<Category> findCategoryByProduct(Product product) {
        return categoryMapper.findCategoryByProduct(product);
    }

}
