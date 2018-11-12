package pl.xsoftpont.panelv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.xsoftpont.panelv.model.ProductInWarehouseView;
import pl.xsoftpont.panelv.repository.ProductInWarehouseRepository;

import java.util.List;

@RestController
public class ProductInWarehouseController {

    @Autowired
    ProductInWarehouseRepository productInWarehouseRepository;

    @RequestMapping("/product/warehouse/view/all")
    public List<ProductInWarehouseView> findAll() {
        return productInWarehouseRepository.findAll();
    }


    @GetMapping("/one/warehouse")
    public List<ProductInWarehouseView> getByWId(@RequestParam("wId") Long wId){
        return productInWarehouseRepository.findByWId(wId);
    }
}
