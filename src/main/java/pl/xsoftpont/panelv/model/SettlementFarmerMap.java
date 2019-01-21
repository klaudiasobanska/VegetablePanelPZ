package pl.xsoftpont.panelv.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.xsoftpont.panelv.repository.*;

@Service
public class SettlementFarmerMap {


    @Autowired
    FarmerRepository farmerRepository;

    @Autowired
    VegetableCentreRepository vegetableCentreRepository;

    @Autowired
    ContractFarmerRepository contractFarmerRepository;



    public ContractSettlementFarmer map(ContractSettlementFarmer cc) {

        if (cc.getStatus() != null) {
            try {
                cc.setStatusName(SettlementStatus.fromId(cc.getStatus()).getName());
            } catch (Exception e) {
                e.printStackTrace();
                cc.setStatusName("");
            }
        }
        if(cc.getContractId() != null){
            ContractFarmer contractFarmer = contractFarmerRepository.findById(cc.getContractId().longValue()).orElse(null);
            if(contractFarmer != null){
                cc.setLp(contractFarmer.getLp());
                Integer d = contractFarmer.getPrice()*cc.getAmount()/contractFarmer.getAmount();
                Double dd = Double.valueOf(d);
                cc.setPrice(dd);
            }

        }
        
        return cc;
    }

}
