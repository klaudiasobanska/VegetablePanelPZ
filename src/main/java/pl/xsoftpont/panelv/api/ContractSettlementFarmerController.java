package pl.xsoftpont.panelv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.xsoftpont.panelv.exception.ResourceNotFoundException;
import pl.xsoftpont.panelv.model.ContractSettlementClient;
import pl.xsoftpont.panelv.model.ContractSettlementFarmer;
import pl.xsoftpont.panelv.model.SettlementFarmerMap;
import pl.xsoftpont.panelv.repository.ContractSettlementClientRepository;
import pl.xsoftpont.panelv.repository.ContractSettlementFarmerRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class ContractSettlementFarmerController extends AbstractController{

    @Autowired
    ContractSettlementFarmerRepository contractSettlementFarmerRepository;

    @Autowired
    SettlementFarmerMap settlementFarmerMap;



    @PostMapping("contract/settlement/farmer/add")
    public ContractSettlementFarmer createContractSettlementFarmer(@Valid @RequestBody ContractSettlementFarmer contractSettlementFarmer) {
        return contractSettlementFarmerRepository.save(contractSettlementFarmer);}


    @PutMapping("contract/settlement/farmer/{id}")
    public ContractSettlementFarmer updateContractSettlementFarmer(@PathVariable("id") Long id,
                                                                   @Valid @RequestBody ContractSettlementFarmer contractSettlementFarmerDetails){
        ContractSettlementFarmer contractSettlementFarmer = contractSettlementFarmerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ContractSettlementFarmer", "id", id));

        contractSettlementFarmer.setAmount(contractSettlementFarmerDetails.getAmount());
        contractSettlementFarmer.setContractId(contractSettlementFarmerDetails.getContractId());
        contractSettlementFarmer.setDate(contractSettlementFarmerDetails.getDate());
        //contractSettlementFarmer.setStatus(contractSettlementFarmerDetails.getStatus());


        ContractSettlementFarmer updatedContractSettlementFarmer = contractSettlementFarmerRepository.save(contractSettlementFarmer);
        return updatedContractSettlementFarmer;
    }

    @GetMapping("/farmer/sett")
    public List<ContractSettlementFarmer> getSett(@RequestParam("farmerId") Long farmerId){
        List<ContractSettlementFarmer> contractSettlementFarmer = contractSettlementFarmerRepository.findSett(farmerId);
        contractSettlementFarmer.forEach(f->settlementFarmerMap.map(f));
        return contractSettlementFarmer;
    }

    @PostMapping("/sett/farmer/delete")
    public Map<String, Object> deleteSettF(@RequestParam("id") Long id){
        contractSettlementFarmerRepository.deleteSettF(id);
        return simpleOkResult();
    }

    @GetMapping("/farmer/sett/vegetable")
    public List<ContractSettlementFarmer> getSettV(@RequestParam("vegetableId") Long vegetableId){
        List<ContractSettlementFarmer> contractSettlementFarmer = contractSettlementFarmerRepository.findSettV(vegetableId);
        contractSettlementFarmer.forEach(f->settlementFarmerMap.map(f));
        return contractSettlementFarmer;
    }

    @PostMapping("/farmer/sett/update")
    public Map<String, Object> updateStt(@RequestParam("id") Long id,
                                         @RequestParam("status") Integer status){
        contractSettlementFarmerRepository.endSett(id,status);
        return simpleOkResult();
    }
}
