package pl.xsoftpont.panelv.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "units")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Unit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UNITS_ID_SEQ")
    @SequenceGenerator(name = "UNITS_ID_SEQ", sequenceName = "units_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}
