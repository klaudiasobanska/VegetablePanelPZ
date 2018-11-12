package pl.xsoftpont.panelv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.xsoftpont.panelv.model.ProductInWarehouseView;

import java.util.List;

@Repository
public interface ProductInWarehouseRepository extends JpaRepository<ProductInWarehouseView, Long> {

    List<ProductInWarehouseView> findAll();

    @Query("select p from ProductInWarehouseView p where wId=:wId")
    public List<ProductInWarehouseView> findByWId(@Param("wId") Long wId);

}
