package pl.xsoftpont.panelv.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "contracts_clients")
@EntityListeners(AuditingEntityListener.class)
public class ContractClient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRACTS_CLIENTS_ID_SEQ")
    @SequenceGenerator(name = "CONTRACTS_CLIENTS_ID_SEQ", sequenceName = "contracts_clients_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "vegetable_centre_id")
    private Long vegetableCentreId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Poland")
    @Column(name = "start_date")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Poland")
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "provider_id")
    private Long providerId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "price")
    private Integer price;

    @Column(name = "lp")
    private Integer lp;

    @Transient
    private String clientName;

    @Transient
    private String statusName;

    @Transient
    private String productName;

    @Transient
    private String providerName;

    @Transient
    private String vegetableCentreName;



}
