package GUI;

import BLL.CustomersBLL;
import DAL.Customers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

public class CustomersGui extends JFrame{
    private JPanel jPanel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTable table1;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton searchButton;
    private JComboBox comboBox1;
    private JButton resetButton;
    private JButton orderButton;
    private CustomersBLL customersBLL;
    private Mess mess;

    public CustomersGui() {
        customersBLL = new CustomersBLL();
        initCustomersGui();
        mess = new Mess();
    }

    /**
     * Set Jframe
     */
    private void initCustomersGui() {
        setContentPane(jPanel1);
        setSize(500, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        //set comboBox
        setComboBox1();

        //set buttons
        setResetButton();
        setAddButton();
        setUpdateButton();
        setDeleteButton();
        setSearchButton();
        setOrderButton();

        //Set click table
        setClickTable();

        //Set data to table
        setUpData();
    }

    /**
     * Set click table
     */
    private void setClickTable() {
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Integer row = table1.getSelectedRow();
                if (row != null && row > -1) {
                    String pw = table1.getModel().getValueAt(row, 1).toString();
                    String fullName = table1.getModel().getValueAt(row, 2).toString();
                    String address = table1.getModel().getValueAt(row, 3).toString();
                    String city = table1.getModel().getValueAt(row, 4).toString();
                    textField1.setText(pw);
                    textField2.setText(fullName);
                    textField3.setText(address);
                    textField4.setText(city);
                }
            }
        });
    }

    /**
     * Load data to table
     */
    private void setUpData() {
        table1.setModel(toTableModel(customersBLL.getList()));
    }

    /**
     * Set List customers to table model
     * @param customersList
     * @return
     */
    private TableModel toTableModel(List<Customers> customersList) {
        Vector header = new Vector<>();
        header.add("id");
        header.add("password");
        header.add("full name");
        header.add("address");
        header.add("city");
        DefaultTableModel model = new DefaultTableModel(header, 0);
        for (Customers customers : customersList) {
            Vector row = new Vector();
            row.add(customers.getCustomerId());
            row.add(customers.getPassword());
            row.add(customers.getFullname());
            row.add(customers.getAddress());
            row.add(customers.getCity());
            model.addRow(row);
        }
        return model;
    }

    /**
     * Get text in textField
     */
    private Customers getTextField() {
        String pw = textField1.getText();
        String fullName = textField2.getText();
        String address = textField3.getText();
        String city = textField4.getText();

        if(pw.trim().length()==0 ||
            pw.trim().isEmpty() ||
            fullName.trim().isEmpty() ||
            fullName.trim().length()==0 ||
            address.trim().isEmpty() ||
            address.trim().length()==0 ||
            city.trim().isEmpty() ||
            city.trim().length()==0) {
            mess.message("Text Field", "Some text filed is empty");
            return null;
        }

        return Customers.builder()
            .password(pw)
            .fullname(fullName)
            .address(address)
            .city(city)
            .build();
    }

    /**
     * Set resetButton
     */
    private void setResetButton() {
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setUpData();
            }
        });
    }

    /**
     * Set addButton
     */
    private void setAddButton() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customers customers = getTextField();
                if(customers != null) {
                    customersBLL.addCustomers(customers);
                    setUpData();
                }
            }
        });
    }

    /**
     * Set updateButton
     */
    private void setUpdateButton() {
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer row = table1.getSelectedRow();
                if (row != null && row > -1) {
                    Customers customers = getTextField();
                    if(customers != null) {
                        customers.setCustomerId(Integer.parseInt(table1.getModel().getValueAt(row, 0).toString()));
                        customersBLL.updateCustomers(customers);
                        setUpData();
                    }
                }
                else {
                    mess.message("Update customers", "No select to update");
                }
            }
        });
    }

    /**
     * Set deleteButton
     */
    private void setDeleteButton() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer row = table1.getSelectedRow();
                if (row != null && row > -1) {
                    Integer id = Integer.parseInt(table1.getModel().getValueAt(row,0).toString());
                    customersBLL.deleteCustomers(id);
                    setUpData();
                }
                else {
                    mess.message("Delete customers", "No select to delete");
                }
            }
        });
    }

    /**
     * Set combobox
     */
    private void setComboBox1() {
        comboBox1.addItem("id");
        comboBox1.addItem("Name");
    }

    /**
     * Set searchButton
     */
    private void setSearchButton() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer select = comboBox1.getSelectedIndex();
                if (select != null && select > -1) {
                    String textSearch = textField5.getText();
                    List<Customers> list = customersBLL.searchCustomers(textSearch, select);
                    if(!list.isEmpty()) {
                        table1.setModel(toTableModel(list));
                    }
                }
            }
        });
    }

    private void setOrderButton() {
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer row = table1.getSelectedRow();
                if (row != null && row > -1) {
                    List<Customers> list = customersBLL.searchCustomers(table1.getModel().getValueAt(row,0).toString(), 0);
                    if(!list.isEmpty()) {
                        Customers customers = list.get(0);
                        BanHangGui banHangGui = new BanHangGui(customers);
                    }
                }
                else {
                    mess.message("Ordered customers", "No select to order");
                }

            }
        });
    }
}
