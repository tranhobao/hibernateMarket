package DAL;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class OrderedDAL {
    private Session session;

    public OrderedDAL() {

    }

    private void openSession() {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
    }

    private void closeSession() {
        session.getTransaction().commit();
        session.close();
    }

    public List<Ordered> getOrderList() {
        List<Ordered> list = new ArrayList<>();
        openSession();
        list = session.createQuery("FROM Ordered o ORDER BY o.orderId asc", Ordered.class).list();
        closeSession();
        return list;
    }

    public void save(Ordered order) {
        openSession();
        session.save(order);
        closeSession();
    }

    public List getDoanhThuDay() {
        openSession();
        List list = session.createQuery("SELECT o.date, sum(o.total) FROM Ordered AS o GROUP BY o.date").list();
        closeSession();
        return list;
    }

    public List getDoanhThuMonth() {
        openSession();
        List list = session.createQuery("SELECT CONCAT(Year(o.date),'-',Month(o.date)), SUM(o.total) FROM Ordered AS o GROUP BY CONCAT(Year(o.date),'-',Month(o.date))").list();
        closeSession();
        return list;
    }

    public List getDoanhThuYear() {
        openSession();
        List list = session.createQuery("SELECT Year(o.date), SUM(o.total) FROM Ordered AS o GROUP BY Year(o.date)").list();
        closeSession();
        return list;
    }
}
