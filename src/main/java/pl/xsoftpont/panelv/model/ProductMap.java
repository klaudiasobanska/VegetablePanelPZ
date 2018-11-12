package pl.xsoftpont.panelv.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.xsoftpont.panelv.repository.UnitRepository;

@Service
public class ProductMap {

    @Autowired
    UnitRepository unitRepository;

    public Product map(Product p) {

        if (p.getUnitId() != null) {
            Unit unit = unitRepository.findById(p.getUnitId()).orElse(null);
            if (unit != null)
                p.setUnitName(unit.getName());
        }
        if (p.getVat() != null) {
            try {
                p.setVatName(VAT_RATE.fromId(p.getVat()).getName());
            } catch (Exception e) {
                e.printStackTrace();
                p.setVatName("");
            }
        }
        return p;
    }
}
