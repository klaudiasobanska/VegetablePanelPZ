package pl.xsoftpont.panelv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.xsoftpont.panelv.exception.ResourceNotFoundException;
import pl.xsoftpont.panelv.model.ContractClient;
import pl.xsoftpont.panelv.repository.ContractClientRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ContractClientConroller {

    @Autowired
    ContractClientRepository contractClientRepository;

    @RequestMapping("contract/client/all")
    public List<ContractClient> findAll(){return contractClientRepository.findAll();}

    @PostMapping("contract/client/add")
    public ContractClient createContractClient(@Valid @RequestBody ContractClient contractClient) {return contractClientRepository.save(contractClient);}

    @GetMapping("contract/client/{id}")
    public ContractClient getContractClientById(@PathVariable(value="id") Long id){
        return contractClientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ContractClient", "id", id));
    }

    @PutMapping("contract/client/{id}")
    public ContractClient updateContractClient(@PathVariable("id") Long id,
                               @Valid @RequestBody ContractClient contractClientDetails){
        ContractClient contractClient = contractClientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContractClient", "id", id));

        contractClient.setAmount(contractClientDetails.getAmount());
        contractClient.setClientId(contractClientDetails.getClientId());
        contractClient.setEndDate(contractClientDetails.getEndDate());
        contractClient.setProductId(contractClientDetails.getProductId());
        contractClient.setProviderId(contractClientDetails.getProviderId());
        contractClient.setStartDate(contractClientDetails.getStartDate());
        contractClient.setStatus(contractClientDetails.getStatus());
        contractClient.setVegetableCentreId(contractClientDetails.getVegetableCentreId());


        ContractClient updatedContractClient = contractClientRepository.save(contractClient);
        return updatedContractClient;
    }
}
