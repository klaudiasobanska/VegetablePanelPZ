package pl.xsoftpont.panelv.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "vegetable_centre")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class VegetableCentre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VEGETABLE_CENTRE_ID_SEQ")
    @SequenceGenerator(name = "VEGETABLE_CENTRE_ID_SEQ", sequenceName = "vegetable_centre_id_seq", allocationSize = 1)
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

    @Column(name = "nip")
    private String nip;

    @Column(name = "user_id")
    private Long userId;

    public String getFullAddress(){
        //return city + " " + address;
        return StringUtils.isNoneBlank(city)?city +" "+address:"" +address;
    }

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "clients_vcentre",
            joinColumns = { @JoinColumn(name = "vegetable_centre_id") },
            inverseJoinColumns = { @JoinColumn(name = "client_id") })
    @JsonManagedReference
    private Set<Client> centreClient = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "farmers_vcentre",
            joinColumns = { @JoinColumn(name = "vegetable_centre_id") },
            inverseJoinColumns = { @JoinColumn(name = "farmer_id") })
    @JsonManagedReference
    private Set<Farmer> centreFarmer = new HashSet<>();
}
