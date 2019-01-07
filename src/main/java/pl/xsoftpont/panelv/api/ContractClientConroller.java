package pl.xsoftpont.panelv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.xsoftpont.panelv.exception.ResourceNotFoundException;
import pl.xsoftpont.panelv.model.*;
import pl.xsoftpont.panelv.repository.ContractClientRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ContractClientConroller {

    @Autowired
    ContractClientRepository contractClientRepository;

    @Autowired
    ContractClientMap contractClientMap;

    @RequestMapping("contract/client/all")
    public List<ContractClient> findAll(){
        return contractClientRepository.findAll();

    }

    @PostMapping("/contract/client/add")
    public ContractClient createContractClient(@Valid @RequestBody ContractClient contractClient) {return contractClientRepository.save(contractClient);}

    @GetMapping("/contract/client/{id}")
    public ContractClient getContractClientById(@PathVariable(value="id") Long id){
        return contractClientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ContractClient", "id", id));
    }

    @PutMapping("/contract/client/{id}")
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
        contractClient.setEndDate(contractClientDetails.getEndDate());
        contractClient.setPrice(contractClientDetails.getPrice());


        ContractClient updatedContractClient = contractClientRepository.save(contractClient);
        return updatedContractClient;
    }

    @GetMapping("/contract/client/search")
    public Page<ContractClient> getContractClientByParam(@RequestParam("param") String param,
                                                         @RequestParam("centreId") Long centreId,
                                                         Pageable pageable){
        Page<ContractClient> page = contractClientRepository.searchContractClient("%" + param + "%",centreId, pageable);
        page.forEach(p -> contractClientMap.map(p));

        return page;
    }

    @GetMapping("/status/contract/all")
    public  List<Map<String,Object>> status(){

        return StatusContract.statusToList();
    }

    @PostMapping("/contract/client/delete")
    public ResponseEntity deleteContractC(@RequestParam("id") Long id){
        contractClientRepository.deleteContractC(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/client/contract/search")
    public Page<ContractClient> getClientContract(@RequestParam("p") String p,
                                           @RequestParam("clientId") Long clientId,
                                           Pageable pageable){
        Page<ContractClient> page = contractClientRepository.searchClientContract("%" + p + "%",clientId, pageable);
        page.forEach(pa -> contractClientMap.map(pa));
        return page;
    }
}
