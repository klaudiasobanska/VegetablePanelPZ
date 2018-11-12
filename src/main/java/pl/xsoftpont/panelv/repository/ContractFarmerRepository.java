package pl.xsoftpont.panelv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.xsoftpont.panelv.model.ContractFarmer;

import java.util.List;

@Repository
public interface ContractFarmerRepository extends JpaRepository<ContractFarmer, Long> {


    List<ContractFarmer> findAll();
}
