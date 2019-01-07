package pl.xsoftpont.panelv.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.xsoftpont.panelv.repository.*;

@Service
public class ContractClientMap {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    VegetableCentreRepository vegetableCentreRepository;



    public ContractClient map(ContractClient cc) {

        if (cc.getProductId() != null) {
            Product product = productRepository.findById(cc.getProductId()).orElse(null);
            if (product != null)
                cc.setProductName(product.getName());
        }
        if (cc.getStatus() != null) {
            try {
                cc.setStatusName(StatusContract.fromId(cc.getStatus()).getName());
            } catch (Exception e) {
                e.printStackTrace();
                cc.setStatusName("");
            }
        }
        if(cc.getClientId() != null){
            Client client = clientRepository.findById(cc.getClientId()).orElse(null);
            if(client != null)
                cc.setClientName(client.getName());
        }
        if(cc.getProviderId() != null){
            Provider provider = providerRepository.findById(cc.getProviderId()).orElse(null);
            if(provider != null)
                cc.setProviderName(provider.getName());
        }
        if(cc.getVegetableCentreId() != null){
            VegetableCentre vegetableCentre = vegetableCentreRepository.findById(cc.getVegetableCentreId()).orElse(null);
            if(vegetableCentre != null)
                cc.setVegetableCentreName(vegetableCentre.getName());
        }
        return cc;
    }
}
