package GUI;

import BLL.CategoryBLL;
import BLL.VegetableBLL;
import DAL.Category;
import DAL.Vegetable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

public class NhapHangGui extends JFrame{
    private JPanel jPanel1;
    private JTabbedPane tabbedPane1;
    private JTable table1;
    private JTextField txtcaname;
    private JTextField txtcades;
    private JButton insertButton1;
    private JButton updateButton1;
    private JButton resetButton1;
    private JButton refreshButton1;
    private JButton deleteButton1;
    private JTable table2;
    private JComboBox comboBox1;
    private JTextField txtgetname;
    private JTextField txtgetamount;
    private JTextField txtgetimage;
    private JTextField txtgetprice;
    private JTextField txtgetsearch;
    private JComboBox categoryCombobox;
    private JComboBox unitCombobox;
    private JButton vegetableWithCategoryButton;
    private JComboBox comboBox4;
    private JButton searchButton;
    private JButton insertButton;
    private JButton updateButton;
    private JButton resetButton;
    private JButton refreshButton;
    private JButton deleteButton;
    private VegetableBLL vegetableBLL;
    private CategoryBLL categoryBLL;
    private Mess mess;

    public NhapHangGui() {
        vegetableBLL = new VegetableBLL();
        categoryBLL = new CategoryBLL();
        mess = new Mess();
        initNhapHangGui();
    }

    private void initNhapHangGui() {
        setContentPane(jPanel1);
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        //Category
        setClickTableCategory();

        showCategory();


        setRefreshButton1();
        setInsertButton1();
        setDeleteButton1();
        setUpdateButton1();

        //Vegetable
        setClickTableVegetable();

        showVegetable();

        setCategoryCombobox();
        setUnitCombobox();
        setComboBox4();

        setRefreshButton();
        setVegetableWithCategoryButton();
        setSearchButton();
        setInsertButton();
        setDeleteButton();
        setUpdateButton();
        setResetButton();


    }

    //Category

    private void showCategory() {
        table1.setModel(toTableModelCategory(categoryBLL.loadCategory()));
    }

    private void setClickTableCategory() {
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Integer row = table1.getSelectedRow();
                if (row != null && row > -1) {
                    String name = table1.getModel().getValueAt(row, 1).toString();
                    String description = table1.getModel().getValueAt(row, 2).toString();

                    txtcaname.setText(name);
                    txtcades.setText(description);
                }
            }
        });
    }

    private TableModel toTableModelCategory(List<Category> list) {
        Vector header = new Vector<>();
        header.add("id");
        header.add("Name");
        header.add("Description");
        DefaultTableModel model = new DefaultTableModel(header, 0);
        for (Category obj : list) {
            Vector row = new Vector();
            row.add(obj.getCatagoryID());
            row.add(obj.getName());
            row.add(obj.getDescription());
            model.addRow(row);
        }
        return model;
    }

    private void setRefreshButton1() {
        refreshButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCategory();
            }
        });
    }

    private Category getTextFieldCategory() {
        String name = txtcaname.getText();
        String description = txtcades.getText();
        if (name.trim().length()==0 || name.trim().isEmpty() ||
                description.trim().length()==0 || description.trim().isEmpty()) {
            mess.message("Text Field", "Some text field is empty");
            return null;
        }
        return Category.builder()
                .Name(name)
                .Description(description)
                .build();
    }

    private void setInsertButton1() {
        insertButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Category category = getTextFieldCategory();
                if (category != null ) {
                    categoryBLL.add(category);
                    showCategory();
                }
            }
        });
    }

    private void setDeleteButton1() {
        deleteButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer row = table1.getSelectedRow();
                if (row != null && row > -1) {
                    Integer id = Integer.parseInt(table1.getModel().getValueAt(row, 0).toString());
                    categoryBLL.delete(id);
                    showCategory();
                }
            }
        });
    }

    private void setUpdateButton1() {
        updateButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer row = table1.getSelectedRow();
                if (row != null && row > -1) {
                    Category category = getTextFieldCategory();
                    Integer id = Integer.parseInt(table1.getModel().getValueAt(row, 0).toString());
                    category.setCatagoryID(id);
                    categoryBLL.update(category);
                    showCategory();
                }
            }
        });
    }

    //Vegetable

    private void showVegetable() {
        table2.setModel(toTableModelVegetable(vegetableBLL.getVegetableList()));
    }

    private void setClickTableVegetable() {
        table2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Integer row = table2.getSelectedRow();
                if (row != null && row > -1) {
                    String name = table2.getModel().getValueAt(row, 1).toString();
                    String nameCategory = table2.getModel().getValueAt(row, 2).toString();
                    String unit = table2.getModel().getValueAt(row, 3).toString();
                    Integer amount = Integer.parseInt(table2.getModel().getValueAt(row, 4).toString());
                    String image = table2.getModel().getValueAt(row, 5).toString();
                    Double price = Double.parseDouble(table2.getModel().getValueAt(row, 6).toString());

                    txtgetname.setText(name);
                    categoryCombobox.getModel().setSelectedItem(nameCategory);
                    unitCombobox.getModel().setSelectedItem(unit);
                    txtgetamount.setText(String.format("%s", amount));
                    txtgetimage.setText(image);
                    txtgetprice.setText(String.format("%s", price));


                }
            }
        });
    }

    private TableModel toTableModelVegetable(List<Vegetable> list) {
        Vector header = new Vector<>();
        header.add("id");
        header.add("Name");
        header.add("Category");
        header.add("Unit");
        header.add("Amount");
        header.add("Image");
        header.add("Price");
        DefaultTableModel model = new DefaultTableModel(header, 0);
        for (Vegetable obj : list) {
            Vector row = new Vector();
            row.add(obj.getVegetableID());
            row.add(obj.getVegetableName());
            row.add(obj.getCatagory().getName());
            row.add(obj.getUnit());
            row.add(obj.getAmount());
            row.add(obj.getImage());
            row.add(obj.getPrice());
            model.addRow(row);
        }
        return model;
    }

    private void setRefreshButton() {
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showVegetable();
            }
        });
    }

    private void setCategoryCombobox() {
        categoryCombobox.setModel(new CategoryCombobox(categoryBLL.convertList(categoryBLL.loadCategory())));
    }

    private void setVegetableWithCategoryButton() {
        vegetableWithCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Category s = (Category) categoryCombobox.getModel().getSelectedItem();
                    table2.setModel(toTableModelVegetable(vegetableBLL.getListWithCategory(s.getCatagoryID())));
                }
                catch (ClassCastException ex) {
                    mess.message("Vegetable with category", "again choose combobox category !!!");
                }
            }
        });
    }

    private void setComboBox4() {
        comboBox4.addItem("id");
        comboBox4.addItem("Name");
    }

    private void setSearchButton() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer select = comboBox4.getSelectedIndex();
                if (select != null && select > -1) {
                    String textSearch = txtgetsearch.getText();
                    List<Vegetable> list = vegetableBLL.getSearch(textSearch, select);
                    if (!list.isEmpty()) {
                        table2.setModel(toTableModelVegetable(list));
                    }
                }
            }
        });
    }

    private void setUnitCombobox() {
        unitCombobox.addItem("Kg");
        unitCombobox.addItem("Bag");
        unitCombobox.addItem("per fruit");
        unitCombobox.addItem("bunch");
    }

    private Vegetable getTextFieldVegetable() {
        String name = txtgetname.getText();
        String category = categoryCombobox.getModel().getSelectedItem().toString();
        String unit = unitCombobox.getSelectedItem().toString();
        Integer amount = null;
        String image = txtgetimage.getText();
        Double price = null;
        int i = 0;
        try {
            amount = Integer.parseInt(txtgetamount.getText());
            i++;
            price = Double.parseDouble(txtgetprice.getText());
        }
        catch (NumberFormatException e) {
            if (i==0) {
                mess.message("TextField", "amount must be number");
            }
            else {
                mess.message("TextField", "price must be number");
            }
            return null;
        }
        if (name.trim().length()==0 || name.trim().isEmpty() ||
                unit.trim().isEmpty() || unit.trim().length()==0 ||
                image.trim().isEmpty() || image.trim().length()==0) {
            mess.message("Text Field", "Some text field is empty");
            return null;
        }
        return Vegetable.builder()
                .VegetableName(name)
                .catagory(categoryBLL.getCategoryWithName(category))
                .Unit(unit)
                .Amount(amount)
                .Image(image)
                .Price(price)
                .build();
    }

    private void setInsertButton() {
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vegetable vegetable = getTextFieldVegetable();
                if (vegetable != null) {
                    vegetableBLL.save(vegetable);
                    showVegetable();
                }
            }
        });
    }

    private void setDeleteButton() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer row = table2.getSelectedRow();
                if (row != null && row > -1) {
                    Integer id = Integer.parseInt(table2.getModel().getValueAt(row, 0).toString());
                    vegetableBLL.delete(id);
                    showVegetable();
                }
                else {
                    mess.message("Delete vegetable", "Nos select to vegetable");
                }
            }
        });
    }

    private void setUpdateButton() {
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer row = table2.getSelectedRow();
                if (row != null && row > -1) {
                    Vegetable vegetable = getTextFieldVegetable();
                    Integer id = Integer.parseInt(table2.getModel().getValueAt(row, 0).toString());
                    vegetable.setVegetableID(id);
                    vegetableBLL.update(vegetable);
                    showVegetable();
                }
            }
        });
    }

    private void setResetButton() {
        txtgetname.setText("");
        txtgetamount.setText("");
        txtgetimage.setText("");
        txtgetprice.setText("");
        showVegetable();
    }

}
