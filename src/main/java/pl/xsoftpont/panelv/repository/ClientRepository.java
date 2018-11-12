package pl.xsoftpont.panelv.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.xsoftpont.panelv.model.Client;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAll();

    @Query(value = "select * from clients c where ((c.name like :p) or (c.city like :p) or (c.nip like :p))",
            countQuery = "select count(*) from clients c where ((c.name like :p) or (c.city like :p) or (c.nip like :p))",
            nativeQuery = true)
    Page<Client> searchClient(@Param("p") String p, Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from Client where id=:id")
    void deleteClient(@Param("id") Long id);


}
