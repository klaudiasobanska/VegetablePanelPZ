package pl.xsoftpont.panelv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.xsoftpont.panelv.exception.ResourceNotFoundException;
import pl.xsoftpont.panelv.model.Farmer;
import pl.xsoftpont.panelv.repository.FarmerRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FarmerController {

    @Autowired
    FarmerRepository farmerRepository;

    @RequestMapping("/farmer/all")
    public List<Farmer> findAll(){return farmerRepository.findAll();}

    @PostMapping("/farmer/add")
    public Farmer createFarmer(@Valid @RequestBody Farmer farmer) {return farmerRepository.save(farmer);}

    @GetMapping("/farmer/{id}")
    public Farmer getFarmerById(@PathVariable(value="id") Long id){
        return farmerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Farmer", "id", id));
    }

    @PutMapping("/farmer/{id}")
    public Farmer updateFarmer(@PathVariable("id") Long id,
                               @Valid @RequestBody Farmer farmerDetails){
        Farmer farmer = farmerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer", "id", id));

        farmer.setName(farmerDetails.getName());
        farmer.setCity(farmerDetails.getCity());
        farmer.setAddress(farmerDetails.getAddress());
        farmer.setEmail(farmerDetails.getEmail());
        farmer.setPhoneNumber(farmerDetails.getPhoneNumber());


        Farmer updatedFarmer = farmerRepository.save(farmer);
        return updatedFarmer;
    }

    @GetMapping("/farmer/search")
    public Page<Farmer> getFarmer(@RequestParam("p") String p, Pageable pageable){
        Page<Farmer> page = farmerRepository.searchFarmer("%" + p + "%", pageable);
        return page;
    }

    @PostMapping("/farmer/delete")
    public ResponseEntity deleteFarmer(@RequestParam("id") Long id){
        farmerRepository.deleteFarmer(id);
        return ResponseEntity.ok().build();
    }
}
