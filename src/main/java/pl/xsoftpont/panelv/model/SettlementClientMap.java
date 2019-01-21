package pl.xsoftpont.panelv.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.xsoftpont.panelv.repository.ClientRepository;
import pl.xsoftpont.panelv.repository.ContractClientRepository;
import pl.xsoftpont.panelv.repository.ContractFarmerRepository;

@Service
public class SettlementClientMap {

    @Autowired
    ContractFarmerRepository contractFarmerRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ContractClientRepository contractClientRepository;


    public ContractSettlementClient map(ContractSettlementClient c) {

        if(c.getStatus() != null){
            try {
                c.setStatusName(SettlementStatus.fromId(c.getStatus()).getName());
            }catch (Exception e) {
                e.printStackTrace();
                c.setStatusName("");
            }
        }
        if (c.getContractId() != null) {
            ContractClient contractClient = contractClientRepository.findById(c.getContractId().longValue()).orElse(null);
            if (contractClient != null) {
                c.setLp(contractClient.getLp());
                Double dd = ((contractClient.getPrice() * c.getAmount()) / contractClient.getAmount());
                c.setPrice(dd);
            }

        }

        return c;
    }
}