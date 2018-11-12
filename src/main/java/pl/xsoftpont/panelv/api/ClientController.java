package pl.xsoftpont.panelv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.xsoftpont.panelv.exception.ResourceNotFoundException;
import pl.xsoftpont.panelv.model.Client;
import pl.xsoftpont.panelv.repository.ClientRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @RequestMapping("/client/all")
    public List<Client> findAll(){return clientRepository.findAll();}

    @PostMapping("/client/add")
    public Client createClient(@Valid @RequestBody Client client) {return clientRepository.save(client);}

    @GetMapping("client/{id}")
    public Client getClientById(@PathVariable(value="id") Long id){
        return clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
    }

    @PutMapping("/client/{id}")
    public Client updateClient(@PathVariable("id") Long id,
                               @Valid @RequestBody Client clientDetails){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));

        client.setName(clientDetails.getName());
        client.setCity(clientDetails.getCity());
        client.setAddress(clientDetails.getAddress());
        client.setEmail(clientDetails.getEmail());
        client.setPhoneNumber(clientDetails.getPhoneNumber());
        client.setNip(clientDetails.getNip());

        Client updatedClient = clientRepository.save(client);
        return updatedClient;
    }

    @GetMapping("/client/search")
    public Page<Client> getClient(@RequestParam("p") String p, Pageable pageable){
        Page<Client> page = clientRepository.searchClient("%" + p + "%", pageable);
        return page;
    }

    @PostMapping("/client/delete")
    public ResponseEntity deleteClient(@RequestParam("id") Long id){
        clientRepository.deleteClient(id);
        return ResponseEntity.ok().build();
    }
}
