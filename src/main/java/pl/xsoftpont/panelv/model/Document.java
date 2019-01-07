package pl.xsoftpont.panelv.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "documents")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Document implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DOCUMENTS_ID_SEQ")
    @SequenceGenerator(name = "DOCUMENTS_ID_SEQ", sequenceName = "documents_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "provider_id")
    private Integer providerId;

    @Column(name = "product_id")
    private Long productId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "doc_date")
    private Date docDate;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "doc_price")
    private BigDecimal docPrice;

    @Column(name = "vegetable_centre_id")
    private Long vegetableCentreId;

}
