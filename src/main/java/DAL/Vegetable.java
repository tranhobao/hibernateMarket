package DAL;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "vegetable")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vegetable {
    
    @Id
    private int VegetableID;
    @Column 
    private String VegetableName;
    @Column
    private String Unit;
    @Column
    private int Amount;
    @Column 
    private String Image;
    @Column
    private Double Price;

    /*
    @Column
    private int CatagoryID;
     */


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="CatagoryID", nullable = false)
    private Category catagory;
}
