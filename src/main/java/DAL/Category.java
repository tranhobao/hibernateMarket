package DAL;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Parent;


@Data
@Entity
@Table(name = "category")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    private int CatagoryID;
    @Column
    private String Name;
    @Column
    private String Description;


    @JsonManagedReference
    @OneToMany(mappedBy = "catagory")
    private Set<Vegetable> listVegetable;

    @Override
    public String toString()
    {
        return this.Name;
    }
}

