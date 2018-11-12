package pl.xsoftpont.panelv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.xsoftpont.panelv.exception.ResourceNotFoundException;
import pl.xsoftpont.panelv.model.ContractFarmer;
import pl.xsoftpont.panelv.repository.ContractFarmerRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ContractFarmerController  {

    @Autowired
    ContractFarmerRepository contractFarmerRepository;

    @RequestMapping("contract/farmer/all")
    public List<ContractFarmer> findAll(){return contractFarmerRepository.findAll();}

    @PostMapping("contract/farmer/add")
    public ContractFarmer createContractFarmer(@Valid @RequestBody ContractFarmer contractFarmer) {return contractFarmerRepository.save(contractFarmer);}

    @GetMapping("contract/farmer/{id}")
    public ContractFarmer getContractFarmerById(@PathVariable(value="id") Long id){
        return contractFarmerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ContractFarmer", "id", id));
    }

    @PutMapping("contract/farmer/{id}")
    public ContractFarmer updateContractFarmer(@PathVariable("id") Long id,
                                               @Valid @RequestBody ContractFarmer contractFarmerDetails){
        ContractFarmer contractFarmer = contractFarmerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContractFarmer", "id", id));

        contractFarmer.setAmount(contractFarmerDetails.getAmount());
        contractFarmer.setFarmerId(contractFarmerDetails.getFarmerId());
        contractFarmer.setEndDate(contractFarmerDetails.getEndDate());
        contractFarmer.setProductId(contractFarmerDetails.getProductId());
        contractFarmer.setProviderId(contractFarmerDetails.getProviderId());
        contractFarmer.setStartDate(contractFarmerDetails.getStartDate());
        contractFarmer.setStatus(contractFarmerDetails.getStatus());
        contractFarmer.setVegetableCentreId(contractFarmerDetails.getVegetableCentreId());


        ContractFarmer updatedContractFarmer = contractFarmerRepository.save(contractFarmer);
        return updatedContractFarmer;
    }

}
