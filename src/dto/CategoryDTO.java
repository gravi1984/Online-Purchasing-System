package dto;


import com.google.gson.Gson;

/**
 * @program: CoffeeWeb
 * @description: Category Data transfer object
 * @author: DennyLee
 * @create: 2019-10-10 00:35
 **/
public class CategoryDTO {

    //category id
    private String categoryId;
    //category name
    private String categoryName;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    //serialize method
    public static String serialize(CategoryDTO categoryDTO) {
        Gson gson = new Gson();
        return gson.toJson(categoryDTO);

    }

    //deserialize method
    public static CategoryDTO deserialize(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, CategoryDTO.class);
    }

}
