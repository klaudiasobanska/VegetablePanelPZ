package pl.xsoftpont.panelv.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "contracts_farmers")
@EntityListeners(AuditingEntityListener.class)
public class ContractFarmer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRACTS_FARMERS_ID_SEQ")
    @SequenceGenerator(name = "CONTRACTS_FARMERS_ID_SEQ", sequenceName = "contracts_farmers_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "farmer_id")
    private Integer farmerId;

    @Column(name = "vegetable_centre_id")
    private Integer vegetableCentreId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Poland")
    @Column(name = "start_date")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Poland")
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "provider_id")
    private Integer providerId;

    @Column(name = "status")
    private Integer status;


}
