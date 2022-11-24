package GUI;

import DAL.Category;

import javax.swing.*;

public class CategoryCombobox extends DefaultComboBoxModel<Category> {
    public CategoryCombobox(Category[] list) {
        super(list);
    }
}
