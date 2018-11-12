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

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {

    List<Farmer> findAll();

    @Query(value = "select * from farmers f where ((f.name like :p) or (f.city like :p))",
            countQuery = "select count(*) from farmers f where ((f.name like :p) or (f.city like :p))",
            nativeQuery = true)
    Page<Farmer> searchFarmer(@Param("p") String p, Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from Farmer where id=:id")
    void deleteFarmer(@Param("id") Long id);


}
