package pl.xsoftpont.panelv.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.xsoftpont.panelv.model.Client;
import pl.xsoftpont.panelv.model.Farmer;
import pl.xsoftpont.panelv.model.VegetableCentre;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {

    List<Farmer> findAll();

    @Query(value = "SELECT nextval('farmers_id_seq')", nativeQuery =
            true)
    Long getNextSeriesId();

    Farmer findByUserId(@Param("userId") Long userId);

    @Query(value = "select * from farmers f where ((f.id in ( select fv.farmer_id from farmers_vcentre fv where vegetable_centre_id = :centreId)) and ((f.name like :p) or (f.city like :p)))",
            countQuery = "select count(*) from farmers f where ((f.id in ( select fv.farmer_id from farmers_vcentre fv where vegetable_centre_id = :centreId)) and ((f.name like :p) or (f.city like :p)))",
            nativeQuery = true)
    Page<Farmer> searchFarmer(@Param("p") String p,
                              @Param("centreId") Long centreId,
                              Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from Farmer where id=:id")
    void deleteFarmer(@Param("id") Long id);


}
