package pl.xsoftpont.panelv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;
import pl.xsoftpont.panelv.model.ContractSettlementClient;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public interface ContractSettlementClientRepository extends JpaRepository<ContractSettlementClient, Long> {


    List<ContractSettlementClient> findAll();

    @Query(value = "select * from contracts_settlement_clients f where f.contract_id in (select c.id from contracts_clients c where c.client_id = :clientId )",
            nativeQuery = true)
    List<ContractSettlementClient> findSett(@Param("clientId") Long clientId);

    @Modifying
    @Transactional
    @Query("update ContractSettlementClient set status = :status where id=:id")
    void endSett(@Param("id") Long id,
                 @Param("status") Integer status);

    @Query(value = "select * from contracts_settlement_clients f where f.contract_id in (select c.id from contracts_clients c where c.vegetable_centre_id = :vegetableId )",
            nativeQuery = true)
    List<ContractSettlementClient> findSettV(@Param("vegetableId") Long vegetableId);

    @Modifying
    @Transactional
    @Query("delete from ContractSettlementClient where id=:id")
    void deleteSettC(@Param("id") Long id);
}
