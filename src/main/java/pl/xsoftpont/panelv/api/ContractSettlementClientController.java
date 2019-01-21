package pl.xsoftpont.panelv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.xsoftpont.panelv.exception.ResourceNotFoundException;
import pl.xsoftpont.panelv.model.*;
import pl.xsoftpont.panelv.repository.ContractSettlementClientRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class ContractSettlementClientController extends AbstractController{

    @Autowired
    ContractSettlementClientRepository contractSettlementClientRepository;

    @Autowired
    SettlementClientMap settlementClientMap;


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

    @GetMapping("/client/sett")
    public List<ContractSettlementClient> getSett(@RequestParam("clientId") Long clientId){
        List<ContractSettlementClient> contractSettlementClients = contractSettlementClientRepository.findSett(clientId);
        contractSettlementClients.forEach(f->settlementClientMap.map(f));
        return contractSettlementClients;
    }

    @PostMapping("/client/sett/update")
    public Map<String, Object> updateStt(@RequestParam("id") Long id,
                                         @RequestParam("status") Integer status){
        contractSettlementClientRepository.endSett(id,status);
        return simpleOkResult();
    }

    @GetMapping("/status/sett/all")
    public  List<Map<String,Object>> status(){

        return SettlementStatus.settlementStatusToList();
    }

    @GetMapping("/client/sett/vegetable")
    public List<ContractSettlementClient> getSettV(@RequestParam("vegetableId") Long vegetableId){
        List<ContractSettlementClient> contractSettlementClients = contractSettlementClientRepository.findSettV(vegetableId);
        contractSettlementClients.forEach(f->settlementClientMap.map(f));
        return contractSettlementClients;
    }

    @PostMapping("/sett/user/delete")
    public Map<String, Object> deleteSettF(@RequestParam("id") Long id){
        contractSettlementClientRepository.deleteSettC(id);
        return simpleOkResult();
    }

}
