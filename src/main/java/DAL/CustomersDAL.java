package DAL;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CustomersDAL {
    private Session session;

    public CustomersDAL(){}

    private void openSession() {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
    }

    private void closeSession() {
        session.getTransaction().commit();
        session.close();
    }

    public List<Customers> getList() {
        List<Customers> customers = new ArrayList<>();
        openSession();
        customers = session.createQuery("FROM Customers ORDER BY customerId ASC", Customers.class).list();
        closeSession();
        return customers;
    }

    public void addCustomers(Customers obj)
    {
        openSession();
        session.saveOrUpdate(obj);
        closeSession();
    }

    public void updateCustomers(Customers obj)
    {
        openSession();
        session.update(obj);
        closeSession();
    }

    public void deleteCustomers(Customers obj)
    {
        openSession();
        session.delete(obj);
        closeSession();
    }

    public Customers getCustomers(int CustomersID)
    {
        Customers obj;
        openSession();
        obj = session.get(Customers.class, CustomersID);
        closeSession();
        return obj;
    }


    public List getCustomersInName(String name)
    {
        List list;
        openSession();
        Query q = session.createQuery("FROM Customers WHERE fullname LIKE CONCAT('%',:name,'%')");
        q.setParameter("name", name);
        list = q.list();
        closeSession();
        return list;
    }
}
