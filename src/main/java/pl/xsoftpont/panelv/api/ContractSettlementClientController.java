package pl.xsoftpont.panelv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.xsoftpont.panelv.exception.ResourceNotFoundException;
import pl.xsoftpont.panelv.model.ContractSettlementClient;
import pl.xsoftpont.panelv.repository.ContractSettlementClientRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ContractSettlementClientController {

    @Autowired
    ContractSettlementClientRepository contractSettlementClientRepository;

    @RequestMapping("contract/settlement/client/all")
    public List<ContractSettlementClient> findAll(){return contractSettlementClientRepository.findAll();}

    @PostMapping("contract/settlement/client/add")
    public ContractSettlementClient createContractSettlementClient(@Valid @RequestBody ContractSettlementClient contractSettlementClient) {
        return contractSettlementClientRepository.save(contractSettlementClient);}

    @GetMapping("contract/settlement/client/{id}")
    public ContractSettlementClient getContractSettlementClientById(@PathVariable(value="id") Long id){
        return contractSettlementClientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ContractSettlementClient", "id", id));
    }

    @PutMapping("contract/settlement/client/{id}")
    public ContractSettlementClient updateContractSettlementClient(@PathVariable("id") Long id,
                                               @Valid @RequestBody ContractSettlementClient contractSettlementClientDetails){
        ContractSettlementClient contractSettlementClient = contractSettlementClientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContractSettlementClient", "id", id));

        contractSettlementClient.setAmount(contractSettlementClientDetails.getAmount());
        contractSettlementClient.setContractId(contractSettlementClientDetails.getContractId());
        contractSettlementClient.setDate(contractSettlementClientDetails.getDate());
        contractSettlementClient.setStatus(contractSettlementClientDetails.getStatus());


        ContractSettlementClient updatedContractSettlementClient = contractSettlementClientRepository.save(contractSettlementClient);
        return updatedContractSettlementClient;
    }

}
