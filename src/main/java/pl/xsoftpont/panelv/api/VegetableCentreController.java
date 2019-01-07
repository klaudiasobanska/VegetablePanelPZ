package pl.xsoftpont.panelv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.xsoftpont.panelv.exception.ResourceNotFoundException;
import pl.xsoftpont.panelv.model.*;
import pl.xsoftpont.panelv.repository.ClientRepository;
import pl.xsoftpont.panelv.repository.FarmerRepository;
import pl.xsoftpont.panelv.repository.VegetableCentreRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class VegetableCentreController extends AbstractController{

    @Autowired
    VegetableCentreRepository vegetableCentreRepository;

    @Autowired
    FarmerRepository farmerRepository;

    @Autowired
    ClientRepository clientRepository;

    private Farmer faremrFromDto(FarmerDto farmerDto) {
        Farmer p = new Farmer();
        p.setId(farmerRepository.getNextSeriesId());
        p.setName(farmerDto.getName());
        p.setUserId(farmerDto.getUserId());
        p.setPhoneNumber(farmerDto.getPhoneNumber());
        p.setEmail(farmerDto.getEmail());
        p.setCity(farmerDto.getCity());
        p.setAddress(farmerDto.getAddress());

        return p;
    }

    private Client clientFromDto(ClientDto clientDto) {
        Client p = new Client();
        p.setId(clientRepository.getNextSeriesId());
        p.setName(clientDto.getName());
        p.setUserId(clientDto.getUserId());
        p.setPhoneNumber(clientDto.getPhoneNumber());
        p.setEmail(clientDto.getEmail());
        p.setCity(clientDto.getCity());
        p.setAddress(clientDto.getAddress());
        p.setNip(clientDto.getNip());

        return p;
    }

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


    @GetMapping("/vegetable/centre/user")
    public VegetableCentre getVegetableByUser(@RequestParam("userId") Long userId){
        return vegetableCentreRepository.findByUserId(userId);
    }




    @PostMapping("/vegetable/add/farmer")
    public ResponseEntity addFarmer( @RequestBody FarmerDto farmerDetails, @RequestParam("centreId") Long centreId) {

        VegetableCentre vegetableCentre = vegetableCentreRepository.findById(centreId)
                .orElseThrow(() -> new ResourceNotFoundException("VegetableCentre", "centreId", centreId));


        Farmer farmer = faremrFromDto(farmerDetails);

        farmerRepository.save(farmer);

        vegetableCentre.getCentreFarmer().add(farmer);
        farmer.getFarmerCentre().add(vegetableCentre);

        vegetableCentreRepository.save(vegetableCentre);

        return ResponseEntity.ok().build();

    }

    @PostMapping("/vegetable/delete/farmer")
    public Map<String, Object> deleteFarmer(@RequestParam("farmerId") Long farmerId, @RequestParam("centreId") Long centreId) {

        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() -> new ResourceNotFoundException("Farmer", "farmerId", farmerId));

        VegetableCentre vegetableCentre = vegetableCentreRepository.findById(centreId)
                .orElseThrow(() -> new ResourceNotFoundException("VegetableCentre", "centreId", centreId));

        vegetableCentre.getCentreFarmer().remove(farmer);

        farmer.getFarmerCentre().remove(vegetableCentre);

        vegetableCentreRepository.save(vegetableCentre);

        return simpleOkResult();
    }

    @PostMapping("/vegetable/delete/client")
    public Map<String, Object> deleteClient(@RequestParam("clientId") Long clientId, @RequestParam("centreId") Long centreId) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "clientId", clientId));

        VegetableCentre vegetableCentre = vegetableCentreRepository.findById(centreId)
                .orElseThrow(() -> new ResourceNotFoundException("VegetableCentre", "centreId", centreId));

        vegetableCentre.getCentreClient().remove(client);

        client.getClientCentre().remove(vegetableCentre);

        vegetableCentreRepository.save(vegetableCentre);

        return simpleOkResult();
    }


    @PostMapping("/vegetable/add/client")
    public ResponseEntity addClient( @RequestBody ClientDto clientDto, @RequestParam("centreId") Long centreId) {

        VegetableCentre vegetableCentre = vegetableCentreRepository.findById(centreId)
                .orElseThrow(() -> new ResourceNotFoundException("VegetableCentre", "centreId", centreId));


        Client client = clientFromDto(clientDto);

        clientRepository.save(client);

        vegetableCentre.getCentreClient().add(client);
        client.getClientCentre().add(vegetableCentre);

        vegetableCentreRepository.save(vegetableCentre);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/client/centre/search")
    public Page<VegetableCentre> getClient(@RequestParam("p") String p,
                                  @RequestParam("clientId") Long clientId,
                                  Pageable pageable){
        Page<VegetableCentre> page = vegetableCentreRepository.searchClientCentre("%" + p + "%",clientId, pageable);
        return page;
    }

    @GetMapping("/farmer/centre/search")
    public Page<VegetableCentre> getFarmerCentre(@RequestParam("p") String p,
                                           @RequestParam("farmerId") Long farmerId,
                                           Pageable pageable){
        Page<VegetableCentre> page = vegetableCentreRepository.searchFarmerCentre("%" + p + "%",farmerId, pageable);
        return page;
    }

}
