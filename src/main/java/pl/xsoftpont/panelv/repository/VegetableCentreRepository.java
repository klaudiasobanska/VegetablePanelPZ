package pl.xsoftpont.panelv.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.xsoftpont.panelv.model.VegetableCentre;

import java.util.List;

@Repository
public interface VegetableCentreRepository extends JpaRepository<VegetableCentre, Long> {

    List<VegetableCentre> findAll();

    VegetableCentre findByUserId(@Param("userId") Long userId);


    @Query(value = "select * from vegetable_centre c where ((c.id in ( select cv.vegetable_centre_id from clients_vcentre cv where client_id = :clientId)) and((c.name like :p) or (c.city like :p) or (c.nip like :p)))",
            countQuery = "select count(*) from clients c where ((c.id in ( select cv.vegetable_centre_id from clients_vcentre cv where client_id = :clientId)) and ((c.name like :p) or (c.city like :p) or (c.nip like :p)))",
            nativeQuery = true)
    Page<VegetableCentre> searchClientCentre(@Param("p") String p,
                              @Param("clientId") Long clientId,
                              Pageable pageable);

    @Query(value = "select * from vegetable_centre c where ((c.id in ( select cv.vegetable_centre_id from farmers_vcentre cv where farmer_id = :farmerId)) and((c.name like :p) or (c.city like :p) or (c.nip like :p)))",
            countQuery = "select count(*) from clients c where ((c.id in ( select cv.vegetable_centre_id from farmers_vcentre cv where farmer_id = :farmerId)) and ((c.name like :p) or (c.city like :p) or (c.nip like :p)))",
            nativeQuery = true)
    Page<VegetableCentre> searchFarmerCentre(@Param("p") String p,
                                             @Param("farmerId") Long farmerId,
                                             Pageable pageable);

}
