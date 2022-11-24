package BLL;

import DAL.*;
import GUI.Mess;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderedBLL {
    private OrderedDAL orderDAL;
    private Mess mess;

    private VegetableBLL vegetableBLL;

    public OrderedBLL() {
        orderDAL = new OrderedDAL();
        mess = new Mess();
        vegetableBLL = new VegetableBLL();
    }
    public void save(Customers customers, String text, Object[][] objects) {
        List<Ordered> list = orderDAL.getOrderList();
        Integer idOrder = list.get(list.size()-1).getOrderId()+1;
        Ordered order = Ordered.builder()
                .orderId(idOrder)
                .customerId(customers.getCustomerId())
                .date(Date.valueOf(LocalDate.now()))
                .note(text)
                .build();
        Set<Orderdetail> orderdetails = new HashSet<>();
        Double total = 0D;
        for (int i = 0; i < objects.length; i++) {
            Integer idVegetable = Integer.parseInt(objects[i][0].toString());
            Byte quantity = Byte.parseByte(objects[i][1].toString());
            Double price = Double.parseDouble(objects[i][2].toString());
            total = total + price;
            Orderdetail orderdetail = Orderdetail.builder()
                    .orderId(idOrder)
                    .orderByOrderId(order)
                    .vegetableId(idVegetable)
                    .quantity(quantity)
                    .price(price)
                    .build();
            orderdetails.add(orderdetail);
            vegetableBLL.updateAmount(idVegetable, Integer.valueOf(quantity));
        }
        order.setOrderdetailsByOrderId(orderdetails);
        order.setTotal(total);
        orderDAL.save(order);
    }
}
