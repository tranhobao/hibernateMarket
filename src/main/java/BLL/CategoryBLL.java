
package BLL;

import DAL.Category;
import DAL.CategoryDAL;
import DAL.Vegetable;
import GUI.Mess;

import java.util.List;

public class CategoryBLL {
    
    private CategoryDAL cateDAL;

    private Mess mess;
    
    public CategoryBLL()
    {
        cateDAL = new CategoryDAL();
        mess = new Mess();
    }
    
    public List loadCategory()
    {
        List list;
        list = cateDAL.loadCategory();
        
        return list;
    }

    public Category[] convertList(List<Category> list)
    {
        int rows = list.size();
        Category[] newList = new Category[rows];
        for(int i = 0; i < rows; i++)
        {
            newList[i] = list.get(i);
            
        }
        return newList;
    }


    public void update(Category category) {
        cateDAL.updateCategory(category);
    }

    public Category getCategory(Integer id) {
        return cateDAL.getCategory(id);
    }
    public void add(Category category) {
        List<Category> list = loadCategory();
        category.setCatagoryID(list.get(list.size()-1).getCatagoryID()+1);
        cateDAL.addCategory(category);
    }

    public void delete(Integer id) {
        Category category = getCategory(id);
        cateDAL.deleteCategory(category);
    }
    public Category getCategory(int categoryID) {
        return cateDAL.getCategory(categoryID);
    }

    public Category getCategoryWithName(String name) {
        Category category = cateDAL.getCategoryWithName(name);
        if (category == null) {
            mess.message("search category with name", String.format("Not found with name %s", name));
        }
        return category;
    }
}
