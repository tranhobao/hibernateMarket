package DAL;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Ordered {
    @Id
    @Column(name = "OrderID")
    private int orderId;
    @Basic
    @Column(name = "CustomerID")
    private int customerId;
    @Basic
    @Column(name = "Date")
    private Date date;
    @Basic
    @Column(name = "Total")
    private double total;
    @Basic
    @Column(name = "Note")
    private String note;
    @OneToMany(mappedBy = "orderByOrderId", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Orderdetail> orderdetailsByOrderId;
}
