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
@Table(name = "contracts_settlement_farmers")
@EntityListeners(AuditingEntityListener.class)
public class ContractSettlementFarmer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRACTS_SETTLEMENT_CLIENTS_ID_SEQ")
    @SequenceGenerator(name = "CONTRACTS_SETTLEMENT_CLIENTS_ID_SEQ", sequenceName = "contracts_settlement_clients_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "contract_id")
    private Integer contractId;

    @Column(name = "amount")
    private Integer amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Poland")
    @Column(name = "date")
    private Date date;

    @Column(name = "status")
    private Integer status;

    @Transient
    private Double price;

    @Transient
    private String statusName;

    @Transient
    private Integer lp;




}
