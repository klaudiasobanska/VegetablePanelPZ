package pl.xsoftpont.panelv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.xsoftpont.panelv.model.Warehouse;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    List<Warehouse> findAll();

    @Query(value = "select * from warehouses w where ((w.name like :param))",
            countQuery = "select count(*) from warehouses w where ((w.name like :param))",
            nativeQuery = true)
    List<Warehouse> searchWarehouse(@Param("param") String param);


}
