package BLL;

import GUI.Mess;
import DAL.Customers;
import DAL.CustomersDAL;

import java.util.ArrayList;
import java.util.List;

public class CustomersBLL {
    private CustomersDAL customersDAL;
    private Mess mess;

    public CustomersBLL() {
        customersDAL = new CustomersDAL();
        mess = new Mess();
    }

    public List<Customers> getList() {
        return customersDAL.getList();
    }

    public void addCustomers(Customers customers) {
        List<Customers> customersList = getList();
        customers.setCustomerId(customersList.get(customersList.size()-1).getCustomerId()+1);
        customersDAL.addCustomers(customers);
    }

    public void updateCustomers(Customers obj) {
        Customers customers = getCustomers(obj.getCustomerId());
        if (customers == null) {
            mess.message("Update customers", "No found customer id to Update");
        }
        else {
            customers.setPassword(obj.getPassword());
            customers.setFullname(obj.getFullname());
            customers.setAddress(obj.getAddress());
            customers.setCity(obj.getCity());
            customersDAL.updateCustomers(customers);
        }
    }

    public void deleteCustomers(Integer id) {
        Customers customers = getCustomers(id);
        if (customers == null) {
            mess.message("Delete customers", "No found customer id to delete");
        }
        else {
            customersDAL.deleteCustomers(customers);
        }
    }

    public List<Customers> searchCustomers(String search, Integer select) {
        List<Customers> customersList = new ArrayList<>();
        switch (select) {
            case 0:
                try {
                    Customers customers = getCustomers(Integer.parseInt(search));
                    if (customers == null) {
                        mess.message("Search customers", "No found customer id: " + search);
                    }
                    else {
                        customersList.add(customers);
                    }
                }
                catch (NumberFormatException e) {
                    mess.message("Search customers", "Customer id must not be: " + search + "(must be number)");
                }
                break;
            case 1:
                customersList.addAll(getCustomersWithLikeName(search));
                break;
            default:
                mess.message("Search customers", "No select combobox");
                break;
        }
        return customersList;
    }

    private Customers getCustomers(Integer id) {
        return customersDAL.getCustomers(id);
    }

    private List<Customers> getCustomersWithLikeName(String name) {
        return customersDAL.getCustomersInName(name);
    }
}
