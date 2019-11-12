package service.facade;

import domain.Category;
import domain.Product;
import dto.CategoryAssembler;
import dto.CategoryDTO;
import dto.ProductDTO;
import service.CategoryService;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CategoryServiceBean {

    //decide granularity: getAll, find, update???

    //remote call findCategoryById service
    public String findCategoryByID(String id) throws RemoteException {
        Category category = new Category();
        category.setCategoryId(id);
        CategoryDTO categoryDTO =
                CategoryAssembler.createCategoryDTO(new CategoryService().findCategroyById(category));
        return CategoryDTO.serialize(categoryDTO);
    }

    //remote call getAllCategories service
    public List<CategoryDTO> getAllCategories() throws RemoteException {
        //get all Categories list locally
        List<Category> categories=  new CategoryService().getAllCategories();
        List<CategoryDTO> allCategoriesDTO = new ArrayList<CategoryDTO>();
        for (Category category:categories) {
            allCategoriesDTO.add(CategoryAssembler.createCategoryDTO(category));
        }
        //return all category DTO list
        return allCategoriesDTO;
    }

    //remote call addCategory service
    public boolean addCategory(String id, String json){
        CategoryDTO categoryDTO = CategoryDTO.deserialize(json);
        return CategoryAssembler.createCategory(categoryDTO);

    }
    //remote call deleteCategory service
    public boolean deleteCategory(String id, String json){
        CategoryDTO categoryDTO = CategoryDTO.deserialize(json);
        return CategoryAssembler.deleteCategory(categoryDTO);

    }
    //remote call updateCategory service
    public boolean updateCategory(String id, String json){
        CategoryDTO categoryDTO = CategoryDTO.deserialize(json);
        return CategoryAssembler.updateCategory(categoryDTO);

    }

}
