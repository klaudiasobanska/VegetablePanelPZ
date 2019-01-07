package pl.xsoftpont.panelv.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "warehouses")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Warehouse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WAREHOUSES_ID_SEQ")
    @SequenceGenerator(name = "WAREHOUSES_ID_SEQ", sequenceName = "warehouses_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "vegetable_centre_id")
    private Long vegetableCentreId;
}
