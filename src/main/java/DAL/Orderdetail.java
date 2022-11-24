package DAL;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(OrderdetailPK.class)
public class Orderdetail {
    @Id
    @Column(name = "OrderID")
    private int orderId;
    @Id
    @Column(name = "VegetableID")
    private int vegetableId;
    @Basic
    @Column(name = "Quantity")
    private byte quantity;
    @Basic
    @Column(name = "Price")
    private double price;
    @ManyToOne
    @JoinColumn(name = "OrderID", nullable = false, insertable=false, updatable=false)
    private Ordered orderByOrderId;
}
