package pl.xsoftpont.panelv.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "vegetable_centre")
@EntityListeners(AuditingEntityListener.class)
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
    private Integer userId;

    public String getFullAddress(){
        //return city + " " + address;
        return StringUtils.isNoneBlank(city)?city +" "+address:"" +address;
    }
}
