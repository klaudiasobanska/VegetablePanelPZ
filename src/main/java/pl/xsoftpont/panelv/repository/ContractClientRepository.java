package pl.xsoftpont.panelv.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.xsoftpont.panelv.model.ContractClient;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ContractClientRepository extends JpaRepository<ContractClient, Long> {

    List<ContractClient> findAll( );

    @Query(value = "select * from contracts_clients c where ((c.client_id in (select cc.id from clients cc where cc.name like :param)) and (c.vegetable_centre_id = :centreId)) " +
            " or (c.product_id in (select p.id from products p where p.name like :param)) ",
            countQuery = "select count(*) from contracts_clients c where ((c.client_id in (select cc.id from clients cc where cc.name like :param))and (c.vegetable_centre_id = :centreId)) " +
                    " or (c.product_id in (select p.id from products p where p.name like :param))",
            nativeQuery = true)
    Page<ContractClient> searchContractClient(@Param("param") String param,
                                              @Param("centreId") Long centreId,Pageable pageable);


    @Query(value = "select * from contracts_clients c where ((c.vegetable_centre_id in (select cc.id from vegetable_centre cc where cc.name like :param)) and (c.client_id = :clientId)) ",
            countQuery = "select count(*) from contracts_clients c where ((c.vegetable_centre_id in (select cc.id from vegetable_centre cc where cc.name like :param)) and (c.client_id = :clientId)) ",
            nativeQuery = true)
    Page<ContractClient> searchClientContract(@Param("param") String param,
                                              @Param("clientId") Long clientId,Pageable pageable);


    @Modifying
    @Transactional
    @Query("delete from ContractClient where id=:id")
    void deleteContractC(@Param("id") Long id);


   List<ContractClient> findByVegetableCentreId(@Param("vegetableId") Long vegetableId);
}
