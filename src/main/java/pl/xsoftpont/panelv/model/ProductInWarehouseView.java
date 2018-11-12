package pl.xsoftpont.panelv.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "product_in_warehouse_view")
@Getter
@Setter
public class ProductInWarehouseView implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "w_id")
    private Long wId;

    @Column(name = "name")
    private String name;

    @Column(name = "product")
    private String product;

    @Column(name = "sum")
    private Integer sum;


}
