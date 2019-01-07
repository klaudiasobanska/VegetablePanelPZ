package pl.xsoftpont.panelv.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.xsoftpont.panelv.repository.*;

@Service
public class ContractFarmerMap {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    FarmerRepository farmerRepository;
    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    VegetableCentreRepository vegetableCentreRepository;



    public ContractFarmer map(ContractFarmer cf) {

        if (cf.getProductId() != null) {
            Product product = productRepository.findById(cf.getProductId()).orElse(null);
            if (product != null)
                cf.setProductName(product.getName());
        }
        if (cf.getStatus() != null) {
            try {
               cf.setStatusName(StatusContract.fromId(cf.getStatus()).getName());
            } catch (Exception e) {
                cf.setStatusName("");
            }
        }
        if(cf.getFarmerId() != null){
            Farmer farmer = farmerRepository.findById(cf.getFarmerId()).orElse(null);
            if(farmer != null)
                cf.setFarmerName(farmer.getName());
        }
        if(cf.getProviderId() != null){
            Provider provider = providerRepository.findById(cf.getProviderId()).orElse(null);
            if(provider != null)
                cf.setProviderName(provider.getName());
        }
        if(cf.getVegetableCentreId() != null){
            VegetableCentre vegetableCentre = vegetableCentreRepository.findById(cf.getVegetableCentreId()).orElse(null);
            if(vegetableCentre != null)
                cf.setVegetableCentreName(vegetableCentre.getName());
        }
        return cf;
    }
}
