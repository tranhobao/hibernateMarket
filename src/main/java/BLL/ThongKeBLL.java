package BLL;

import DAL.CategoryDAL;
import DAL.OrderedDAL;
import DAL.VegetableDAL;
import GUI.Mess;

import java.util.List;

public class ThongKeBLL {
    private OrderedDAL orderDAL;
    private VegetableDAL vegetableDAL;

    private CategoryDAL categoryDAL;
    private Mess mess;

    public ThongKeBLL() {
        orderDAL = new OrderedDAL();
        vegetableDAL = new VegetableDAL();
        categoryDAL = new CategoryDAL();
        mess = new Mess();
    }

    public Object[][] thongKeDoanhThu(int select) {
        Object[][] objects = null;
        List list = null;
        switch (select) {
            case 0:
                list =  orderDAL.getDoanhThuDay();
                objects = modelDoanhThu(list);
                break;
            case 1:
                list = orderDAL.getDoanhThuMonth();
                objects = modelDoanhThu(list);
                break;
            case 2:
                list = orderDAL.getDoanhThuYear();
                objects = modelDoanhThu(list);
                break;
            default:
                mess.message("Thong ke doanh thu", "Chua lua kieu thong ke doanh thu");
                break;
        }
        return objects;
    }

    private Object[][] modelDoanhThu(List list) {
        Object[][] objects = new Object[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            Object[] objects1 = (Object[]) list.get(i);
            objects[i][0] = objects1[0];
            objects[i][1] = objects1[1];
        }
        return objects;
    }

    public Object[][] thongKeSP(int select) {
        Object[][] objects = null;
        List list = null;
        switch (select) {
            case 0:
                list =  vegetableDAL.getThongKeVegetable();
                objects = modelThongKeVegetable(list);
                break;
            case 1:
                list = categoryDAL.getThongKeCategory();
                objects = modelThongKeCategory(list);
                break;
            default:
                mess.message("Thong ke doanh thu", "Chua lua kieu thong ke doanh thu");
                break;
        }
        return objects;
    }

    private Object[][] modelThongKeVegetable(List list) {
        Object[][] objects = new Object[list.size()][6];
        for (int i = 0; i < list.size(); i++) {
            Object[] objects1 = (Object[]) list.get(i);
            objects[i][0] = objects1[0];
            objects[i][1] = objects1[1];
            objects[i][2] = objects1[2];
            objects[i][3] = objects1[3];
            objects[i][4] = objects1[4];
            objects[i][5] = objects1[5];
        }
        return objects;
    }

    private Object[][] modelThongKeCategory(List list) {
        Object[][] objects = new Object[list.size()][4];
        for (int i = 0; i < list.size(); i++) {
            Object[] objects1 = (Object[]) list.get(i);
            objects[i][0] = objects1[0];
            objects[i][1] = objects1[1];
            objects[i][2] = objects1[2];
            objects[i][3] = objects1[3];
        }
        return objects;
    }
}
