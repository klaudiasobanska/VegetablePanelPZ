package pl.xsoftpont.panelv.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "kg_day_sum_warehouse_view")
@Getter
@Setter
public class KgDayView implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;


    @Column(name = "name")
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Poland")
    @Column(name = "doc_date")
    private Date docDate;


    @Column(name = "sum")
    private Integer sum;
}
