package pl.xsoftpont.panelv.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTS_ID_SEQ")
    @SequenceGenerator(name = "PRODUCTS_ID_SEQ", sequenceName = "products_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name="index")
    private String index;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "vat")
    private Integer vat;

    @Transient
    private String unitName;

    @Transient
    private String vatName;

    @Column(name = "vegetable_centre_id")
    private Long vegetableCentreId;
}
