package pl.xsoftpont.panelv.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.xsoftpont.panelv.model.Provider;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProviderRepository  extends JpaRepository<Provider, Long> {

    List<Provider> findAll();

    @Query(value = "select * from provider p where ((p.name like :p) or (p.city like :p) or (p.nip like :p) and (p.vegetable_centre_id = :centreId))",
            countQuery = "select count(*) from provider p where ((p.name like :p) or (p.city like :p) or (p.nip like :p) and (p.vegetable_centre_id = :centreId))",
            nativeQuery = true)
    Page<Provider> searchProvider(@Param("p") String p,
                                  @Param("centreId") Long centreId,
                                  Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from Provider where id=:id")
    void deleteProvider(@Param("id") Long id);

    List<Provider> findByVegetableCentreId(@Param("centreId") Long centreId);
}
