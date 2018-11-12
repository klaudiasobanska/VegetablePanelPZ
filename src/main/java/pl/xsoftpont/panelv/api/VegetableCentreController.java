package pl.xsoftpont.panelv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.xsoftpont.panelv.exception.ResourceNotFoundException;
import pl.xsoftpont.panelv.model.VegetableCentre;
import pl.xsoftpont.panelv.repository.VegetableCentreRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class VegetableCentreController {

    @Autowired
    VegetableCentreRepository vegetableCentreRepository;

    @RequestMapping("/vegetable/centre/all")
    public List<VegetableCentre> findAll(){return vegetableCentreRepository.findAll();}

    @PostMapping("/vegetable/centre/add")
    public VegetableCentre createVegetableCentre(@Valid @RequestBody VegetableCentre vegetableCentre) {
        return vegetableCentreRepository.save(vegetableCentre);}

    @GetMapping("/vegetable/centre/{id}")
    public VegetableCentre getVegetableCentreById(@PathVariable(value="id") Long id){
        return vegetableCentreRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("VegetableCentre", "id", id));
    }

    @PutMapping("/vegetable/centre/{id}")
    public VegetableCentre updateVegetableCentre(@PathVariable("id") Long id,
                               @Valid @RequestBody VegetableCentre vegetableCentreDetails){
        VegetableCentre vegetableCentre = vegetableCentreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VegetableCentre", "id", id));

        vegetableCentre.setName(vegetableCentreDetails.getName());
        vegetableCentre.setCity(vegetableCentreDetails.getCity());
        vegetableCentre.setAddress(vegetableCentreDetails.getAddress());
        vegetableCentre.setEmail(vegetableCentreDetails.getEmail());
        vegetableCentre.setPhoneNumber(vegetableCentreDetails.getPhoneNumber());


        VegetableCentre updatedVegetableCentre = vegetableCentreRepository.save(vegetableCentre);
        return updatedVegetableCentre;
    }

}
