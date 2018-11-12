package pl.xsoftpont.panelv.api;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.xsoftpont.panelv.exception.ResourceNotFoundException;
import pl.xsoftpont.panelv.model.Unit;
import pl.xsoftpont.panelv.model.VAT_RATE;
import pl.xsoftpont.panelv.repository.UnitRepository;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

@RestController
public class UnitController {

    @Autowired
    UnitRepository unitRepository;

    @RequestMapping("/unit/all")
    public List<Unit> findAll() {return unitRepository.findAll();}

    @PostMapping("/unit/add")
    public Unit createUnit(@Valid @RequestBody Unit unit){return unitRepository.save(unit);}

    @GetMapping("/unit/id/s{id}")
    public Unit getUnitById(@PathVariable("id") Long id){
        return unitRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Unit", "id", id));
    }

    @PutMapping("unit/{id}")
    public Unit updateUnit(@PathVariable("id") Long id,
                                 @Valid @RequestBody Unit unitDetails){
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unit", "id", id));

        unit.setName(unitDetails.getName());


        Unit updatedUnit = unitRepository.save(unit);
        return updatedUnit;
    }

}
