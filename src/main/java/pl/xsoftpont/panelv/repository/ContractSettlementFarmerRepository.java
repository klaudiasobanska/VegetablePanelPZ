package pl.xsoftpont.panelv.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.xsoftpont.panelv.model.ContractFarmer;
import pl.xsoftpont.panelv.model.ContractSettlementClient;
import pl.xsoftpont.panelv.model.ContractSettlementFarmer;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ContractSettlementFarmerRepository extends JpaRepository<ContractSettlementFarmer, Long> {

    List<ContractSettlementFarmer> findAll();

    @Query(value = "select * from contracts_settlement_farmers f where f.contract_id in (select c.id from contracts_farmers c where c.farmer_id = :farmerId )",
            nativeQuery = true)
    List<ContractSettlementFarmer> findSett(@Param("farmerId") Long farmerId);

    @Modifying
    @Transactional
    @Query("delete from ContractSettlementFarmer where id=:id")
    void deleteSettF(@Param("id") Long id);

    @Query(value = "select * from contracts_settlement_farmers f where f.contract_id in (select c.id from contracts_farmers c where c.vegetable_centre_id = :vegetableId )",
            nativeQuery = true)
    List<ContractSettlementFarmer> findSettV(@Param("vegetableId") Long vegetableId);

    @Modifying
    @Transactional
    @Query("update ContractSettlementFarmer set status = :status where id=:id")
    void endSett(@Param("id") Long id,
                 @Param("status") Integer status);


}
