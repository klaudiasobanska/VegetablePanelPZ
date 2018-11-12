package pl.xsoftpont.panelv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;
import pl.xsoftpont.panelv.model.ContractSettlementClient;

import java.util.List;

@RestController
public interface ContractSettlementClientRepository extends JpaRepository<ContractSettlementClient, Long> {


    List<ContractSettlementClient> findAll();
}
