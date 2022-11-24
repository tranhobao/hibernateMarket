package GUI;

import BLL.CategoryBLL;
import BLL.OrderedBLL;
import BLL.VegetableBLL;
import DAL.Category;
import DAL.Customers;
import DAL.Vegetable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

public class BanHangGui extends JFrame{
    private JPanel jPanel1;
    private JComboBox SearchSPComboBox;
    private JButton resetButtonSP;
    private JButton searchButtonSP;
    private JTextField searchSPTextField;
    private JTable SPTable;
    private JTable tableOrder;
    private JButton orderButton;
    private JButton delete1Button;
    private JComboBox categorytComboBox;
    private JButton addButton;
    private JLabel labelCustomer;
    private JTextArea textAreaNote;
    private JSpinner spinner1;
    private VegetableBLL vegetableBLL;
    private CategoryBLL categoryBLL;
    private OrderedBLL orderBLL;
    private Mess mess;

    private Customers customers;

    public BanHangGui(Customers customers) {
        this.vegetableBLL = new VegetableBLL();
        this.categoryBLL = new CategoryBLL();
        this.orderBLL = new OrderedBLL();
        this.mess = new Mess();
        this.customers = customers;
        init();
    }

    private void init() {
        labelCustomer.setText(String.format("id = %s, full name: %s", customers.getCustomerId(), customers.getFullname()));
        setContentPane(jPanel1);
        setSize(1200, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        initLoandData();
        initButton();
        initCombobox();
        initTable();
    }

    private void initButton() {
        setResetButtonVegetable();
        setSearchButtonVegatable();
        setAddButton();
        setDelete1Button();
        setOrderButton();
    }

    private void initCombobox() {
        setComboBoxVegatable();
    }

    private void initTable() {
        tableOrder.setModel(toTableModelOrder());
    }

    private void initLoandData() {
        setUpLoadDataVegatable();
    }


//Vegatable:
    /**
     * Set up data vegatable
     */
    private void setUpLoadDataVegatable() {
        SPTable.setModel(toTableModelVegetable(vegetableBLL.getVegetableList()));
    }

    /**
     * Set List vegatable to table model
     * @param list
     * @return TableModel
     */
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

    /**
     * Button reset vegatable
     */
    private void setResetButtonVegetable() {
        resetButtonSP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUpLoadDataVegatable();
            }
        });
    }

    /**
     * Cobobox vegatable
     */
    private void setComboBoxVegatable() {
        SearchSPComboBox.addItem("id");
        SearchSPComboBox.addItem("Name");
        categorytComboBox.setModel(new CategoryCombobox(categoryBLL.convertList(categoryBLL.loadCategory())));

        categorytComboBox.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Category s = (Category) categorytComboBox.getModel().getSelectedItem();
                    SPTable.setModel(toTableModelVegetable(vegetableBLL.getListWithCategory(s.getCatagoryID())));
                }
                catch (ClassCastException ex) {
                    mess.message("Vegetables with category", "againt choose combobox category !!!");
                }
            }
        });
    }

    /**
     * Set searchButton vegatable
     */
    private void setSearchButtonVegatable() {
        searchButtonSP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer select = SearchSPComboBox.getSelectedIndex();
                if (select != null && select > -1) {
                    String textSearch = searchSPTextField.getText();
                    List<Vegetable> list = vegetableBLL.getSearch(textSearch, select);
                    if (!list.isEmpty()) {
                        SPTable.setModel(toTableModelVegetable(list));
                    }
                }
            }
        });
    }

//order:
    private TableModel toTableModelOrder(){
        Vector header = new Vector();
        header.add("Id");
        header.add("Name");
        header.add("Quantity");
        header.add("Price with Quantity");
        DefaultTableModel defaultTableModel = new DefaultTableModel(header,0);
        return defaultTableModel;
    }

    private void addOneVegetableToTableOrder(Vegetable vegetable, Integer quantity) {
        DefaultTableModel defaultTableModel = (DefaultTableModel) tableOrder.getModel();
        int f = 0;
        for (int i = 0; i < defaultTableModel.getRowCount(); i++) {
            if (defaultTableModel.getValueAt(i, 0).toString().equals(String.format("%s", vegetable.getVegetableID()))) {
                Integer oldQuantity = (Integer) defaultTableModel.getValueAt(i, 2);
                Integer newQuantity = oldQuantity + quantity;
                defaultTableModel.setValueAt(newQuantity, i, 2);
                Double price = newQuantity * vegetable.getPrice();
                defaultTableModel.setValueAt(price, i, 3);
                f++;
                break;
            }
        }
        if (f == 0) {
            Vector row = new Vector();
            row.add(vegetable.getVegetableID());
            row.add(vegetable.getVegetableName());
            row.add(quantity);
            Double price = quantity * vegetable.getPrice();
            row.add(price);
            defaultTableModel.addRow(row);
        }
        tableOrder.setModel(defaultTableModel);
    }

    private void setAddButton() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer row = SPTable.getSelectedRow();
                if (row != null && row >=0) {
                    try {
                        spinner1.commitEdit();
                        Integer quantity = Integer.parseInt(spinner1.getValue().toString());
                        if (quantity > 0) {
                            List<Vegetable> vegetables = vegetableBLL.getSearch(SPTable.getModel().getValueAt(row, 0).toString(), 0);
                            if (!vegetables.isEmpty()) {
                                Vegetable vegetable = vegetables.get(0);
                                if (vegetable.getAmount() > quantity) {
                                    addOneVegetableToTableOrder(vegetable, quantity);
                                }
                                else {
                                    mess.message("Add button","Quantity must be number < amount vegetable !!!");
                                }
                            }
                        }
                        else {
                            mess.message("Add button","Quantity must be number > 0 !!!");
                        }
                    } catch (ParseException ex1) {
                        mess.message("Add button","Quantity must be number !!!");
                    }
                }
                else {
                    mess.message("Add button","No choose vegetable to add order");
                }
            }
        });
    }

    private void setDelete1Button() {
        delete1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer row = tableOrder.getSelectedRow();
                if (row != null && row >=0) {
                    DefaultTableModel tableModel = (DefaultTableModel) tableOrder.getModel();
                    tableModel.removeRow(row);
                    tableOrder.setModel(tableModel);
                }
                else {
                    mess.message("Delete one Button","No choose vegetable in table order to delete");
                }
            }
        });
    }

    private void setOrderButton() {
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tableModel = (DefaultTableModel) tableOrder.getModel();
                Object[][] objects = new Object[tableModel.getRowCount()][3];
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    objects[i][0] = tableModel.getValueAt(i, 0);
                    objects[i][1] = tableModel.getValueAt(i, 2);
                    objects[i][2] = tableModel.getValueAt(i, 3);
                }
                orderBLL.save(customers, textAreaNote.getText(), objects);
                mess.message("order","Success");
                setVisible(false);
            }
        });
    }

}
