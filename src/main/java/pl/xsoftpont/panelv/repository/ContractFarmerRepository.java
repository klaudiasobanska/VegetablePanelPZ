package pl.xsoftpont.panelv.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.xsoftpont.panelv.model.ContractClient;
import pl.xsoftpont.panelv.model.ContractFarmer;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ContractFarmerRepository extends JpaRepository<ContractFarmer, Long> {

    List<ContractFarmer> findAll();

    @Query(value = "select * from contracts_farmers c where (((c.farmer_id in (select cc.id from farmers cc where cc.name like :param)) " +
            " or (c.product_id in (select p.id from products p where p.name like :param)))  and (c.vegetable_centre_id = :centreId)) ",
            countQuery = "select count(*) from contracts_farmers c where (((c.farmer_id in (select cc.id from farmers cc where cc.name like :param)) " +
                    " or (c.product_id in (select p.id from products p where p.name like :param))) and (c.vegetable_centre_id = :centreId))",
            nativeQuery = true)
    Page<ContractFarmer> searchContractFarmer(@Param("param") String param,
                                              @Param("centreId") Long centreId,
                                              Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from ContractFarmer where id=:id")
    void deleteContractF(@Param("id") Long id);

    @Query(value = "select * from contracts_farmers c where ((c.vegetable_centre_id in (select cc.id from vegetable_centre cc where cc.name like :param)) and (c.farmer_id = :farmerId)) ",
            countQuery = "select count(*) from contracts_farmers c where ((c.vegetable_centre_id in (select cc.id from vegetable_centre cc where cc.name like :param)) and (c.farmer_id = :farmerId)) ",
            nativeQuery = true)
    Page<ContractFarmer> searchFarmerContract(@Param("param") String param,
                                              @Param("farmerId") Long farmerId, Pageable pageable);



    List<ContractFarmer>findContractFarmerByFarmerId(@Param("farmerId") Long farmerId);
}
