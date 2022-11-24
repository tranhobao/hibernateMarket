package DAL;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class VegetableDAL {
    private Session session;
    private CategoryDAL categoryDAL;

    public VegetableDAL() {
        categoryDAL = new CategoryDAL();
    }

    private void openSession() {
        session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
    }

    private void closeSession() {
        session.getTransaction().commit();
        session.close();
    }

    public List<Vegetable> getVegetableList() {
        List<Vegetable> vegetables = new ArrayList<>();
        openSession();
        vegetables = session.createQuery("FROM Vegetable AS v ORDER BY v.id ASC", Vegetable.class).list();
        closeSession();
        return vegetables;
    }

    public List<Vegetable> getVegetableList(int catagoryID) {
        List<Vegetable> vegetables = new ArrayList<>();
        openSession();
        Query query = session.createQuery("FROM Vegetable AS v WHERE v.catagory.id = :id ORDER BY v.id ASC", Vegetable.class);
        query.setParameter("id", catagoryID);
        vegetables = query.list();
        closeSession();
        return vegetables;
    }

    public Vegetable getVegetable(Integer VegetableID) {
        Vegetable vegetable = null;
        openSession();
        vegetable = session.get(Vegetable.class, VegetableID);
        closeSession();
        return vegetable;
    }

    public List<Vegetable> getVegetableListWithName(String name) {
        List<Vegetable> vegetables = new ArrayList<>();
        openSession();
        Query query = session.createQuery("FROM Vegetable AS v WHERE v.VegetableName like CONCAT('%',:name,'%') ORDER BY v.id ASC", Vegetable.class);
        query.setParameter("name", name);
        vegetables = query.list();
        closeSession();
        return vegetables;
    }

    public void save(Vegetable vegetable) {
        openSession();
        session.save(vegetable);
        closeSession();
    }

    public void delete(Vegetable vegetable) {
        //categoryDAL.deleteVegetable(vegetable);
        openSession();
        session.delete(vegetable);
        closeSession();
    }

    public void update(Vegetable vegetable) {
        openSession();
        session.update(vegetable);
        closeSession();
    }

    public List getThongKeVegetable() {
        openSession();
        List list = session.createQuery("SELECT v.VegetableID, v.VegetableName, v.Amount, v.Price, SUM(od.quantity), SUM(od.price) FROM Vegetable AS v INNER JOIN Orderdetail AS od ON v.VegetableID=od.vegetableId GROUP BY v.VegetableID").list();
        closeSession();
        return list;
    }
}
