package pl.xsoftpont.panelv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.xsoftpont.panelv.model.ContractClient;

import java.util.List;

@Repository
public interface ContractClientRepository extends JpaRepository<ContractClient, Long> {

    List<ContractClient> findAll();
}
