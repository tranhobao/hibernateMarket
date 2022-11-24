package DAL;

import com.google.gson.Gson;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customers {
    @Id
    @Column(name = "CustomerID")
    private int customerId;

    @Column(name = "Password")
    private String password;

    @Column(name = "Fullname")
    private String fullname;

    @Column(name = "Address")
    private String address;

    @Column(name = "City")
    private String city;

    public String toString() {
        return this.fullname;
    }

}
