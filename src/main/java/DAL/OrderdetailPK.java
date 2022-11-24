package DAL;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class OrderdetailPK implements Serializable {
    @Column(name = "OrderID")
    @Id
    private int orderId;
    @Column(name = "VegetableID")
    @Id
    private int vegetableId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getVegetableId() {
        return vegetableId;
    }

    public void setVegetableId(int vegetableId) {
        this.vegetableId = vegetableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderdetailPK that = (OrderdetailPK) o;

        if (orderId != that.orderId) return false;
        if (vegetableId != that.vegetableId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + vegetableId;
        return result;
    }
}
