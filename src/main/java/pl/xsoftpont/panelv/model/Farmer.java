package pl.xsoftpont.panelv.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "farmers")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Farmer implements Serializable {

    @Id
    /*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FARMERS_ID_SEQ")
    @SequenceGenerator(name = "FARMERS_ID_SEQ", sequenceName = "farmers_id_seq", allocationSize = 1)*/
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "user_id")
    private Long userId;

    public String getFullAddress(){

        return StringUtils.isNoneBlank(city)?city +" "+address:"" +address;
    }

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "centreFarmer")
    @JsonBackReference
    private Set<VegetableCentre> farmerCentre = new HashSet<>();

    

}
