package dto;


import domain.Category;
import service.CategoryService;

/**
 * @program: CoffeeWeb
 * @description: Functions of CategoryDTO
 * @author: DennyLee
 * @create: 2019-10-10 00:39
 **/
public class CategoryAssembler {

    /**
     * create CategoryDTO from category
     *
     * @param category Category
     * @return CategoryDTO
     */
    public static CategoryDTO createCategoryDTO(Category category) {
        CategoryDTO result = new CategoryDTO();
        result.setCategoryId(category.getCategoryId());
        result.setCategoryName(category.getCategoryName());
        return result;
    }


    /**
     * Add category from remote call
     *
     * @param categoryDTO CategoryDTO
     * @return result
     */
    public static boolean createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();

        category.setCategoryId(categoryDTO.getCategoryId());
        category.setCategoryName(categoryDTO.getCategoryName());

        return new CategoryService().newCategory(category);
    }

    /**
     * update category from remote call
     *
     * @param categoryDTO CategoryDTO
     * @return result
     */
    public static boolean updateCategory(CategoryDTO categoryDTO) {
        Category category = new Category();

        category.setCategoryId(categoryDTO.getCategoryId());
        category.setCategoryName(categoryDTO.getCategoryName());

        return new CategoryService().updateCategory(category);
    }

    /**
     * delete category from remote call
     *
     * @param categoryDTO CategoryDTO
     * @return result
     */
    public static boolean deleteCategory(CategoryDTO categoryDTO) {
        Category category = new Category();

        category.setCategoryId(categoryDTO.getCategoryId());
        category.setCategoryName(categoryDTO.getCategoryName());

        return new CategoryService().deleteCategory(category);
    }

}
