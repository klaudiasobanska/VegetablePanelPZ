package pl.xsoftpont.panelv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.xsoftpont.panelv.exception.ResourceNotFoundException;
import pl.xsoftpont.panelv.model.Warehouse;
import pl.xsoftpont.panelv.repository.WarehouseRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
public class WarehouseController {

    @Autowired
    WarehouseRepository warehouseRepository;

    @RequestMapping("/warehouse/all")
    public List<Warehouse> finaAll(){return warehouseRepository.findAll();}

    @PostMapping("/warehouse/add")
    public Warehouse createWarehouse(@Valid @RequestBody Warehouse warehouse){
        return warehouseRepository.save(warehouse);
    }

    @GetMapping("/warehouse/{id}")
    public Warehouse getWarehouseById(@PathVariable("id") Long id){
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warhouse", "id", id));
    }

    @PutMapping("warehouse/{id}")
    public Warehouse updateWarehouse(@PathVariable("id") Long id,
                                     @Valid @RequestBody Warehouse warehouseDetails){

        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warhouse", "id", id));

        warehouse.setName(warehouseDetails.getName());
        warehouse.setAddress(warehouseDetails.getAddress());

        Warehouse updatedWarhouse = warehouseRepository.save(warehouse);
        return  updatedWarhouse;
    }

    @GetMapping("/warehouse/search")
    public List<Warehouse> getWarehouse(@RequestParam("param") String param,
                                        @RequestParam("centreId") Long centreId){
        List<Warehouse> w = warehouseRepository.searchWarehouse("%" + param + "%", centreId);
        return w;
    }
}
