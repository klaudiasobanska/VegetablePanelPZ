package pl.xsoftpont.panelv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.xsoftpont.panelv.exception.ResourceNotFoundException;
import pl.xsoftpont.panelv.model.Provider;
import pl.xsoftpont.panelv.repository.ProviderRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProviderController {

    @Autowired
    ProviderRepository providerRepository;

    @RequestMapping("/provider/all")
    public List<Provider> findAll(){return providerRepository.findAll();}

    @PostMapping("/provider/add")
    public Provider createProvider(@Valid @RequestBody Provider provider) {return providerRepository.save(provider);}

    @GetMapping("provider/{id}")
    public Provider getProviderById(@PathVariable(value="id") Long id){
        return providerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Provider", "id", id));
    }

    @PutMapping("provider/{id}")
    public Provider updateProvider(@PathVariable("id") Long id,
                               @Valid @RequestBody Provider providerDetails){
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Provider", "id", id));

        provider.setName(providerDetails.getName());
        provider.setCity(providerDetails.getCity());
        provider.setAddress(providerDetails.getAddress());
        provider.setEmail(providerDetails.getEmail());
        provider.setPhoneNumber(providerDetails.getPhoneNumber());
        provider.setNip(providerDetails.getNip());

        Provider updatedProvider = providerRepository.save(provider);
        return updatedProvider;
    }

    @GetMapping("/provider/search")
    public Page<Provider> getProvider(@RequestParam("p") String p,
                                      @RequestParam("centreId") Long centreId,
                                      Pageable pageable){
        Page<Provider> page = providerRepository.searchProvider("%" + p + "%",centreId, pageable);
        return page;
    }

    @PostMapping("/provider/delete")
    public ResponseEntity deleteProvider(@RequestParam("id") Long id){
        providerRepository.deleteProvider(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/provider/vegetable")
    public List<Provider> getProviderByVegetableCentre(@RequestParam("centreId") Long centreId){
        return providerRepository.findByVegetableCentreId(centreId);
    }
}
