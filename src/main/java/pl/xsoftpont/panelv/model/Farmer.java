package pl.xsoftpont.panelv.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "farmers")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Farmer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FARMERS_ID_SEQ")
    @SequenceGenerator(name = "FARMERS_ID_SEQ", sequenceName = "farmers_id_seq", allocationSize = 1)
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

    public String getFullAddress(){

        return StringUtils.isNoneBlank(city)?city +" "+address:"" +address;
    }

}
