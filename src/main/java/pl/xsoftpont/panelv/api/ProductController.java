package pl.xsoftpont.panelv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.xsoftpont.panelv.exception.ResourceNotFoundException;
import pl.xsoftpont.panelv.model.Product;
import pl.xsoftpont.panelv.model.ProductMap;
import pl.xsoftpont.panelv.model.Provider;
import pl.xsoftpont.panelv.model.VAT_RATE;
import pl.xsoftpont.panelv.repository.ProductRepository;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMap productMap;

    @RequestMapping("/product/all")
    public List<Product> findAll() {
        List<Product> product = productRepository.findAll();
        product.forEach(p -> productMap.map(p));
        return product;
    }

    @PostMapping("/product/add")
    public Product createProduct(@Valid @RequestBody Product product){return productRepository.save(product);}

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable("id") Long id){
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
    }

    @PutMapping("/product/{id}")
    public Product updateProduct(@PathVariable("id") Long id,
                                 @Valid @RequestBody Product productDetails){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setUnitId(productDetails.getUnitId());
        product.setVat(productDetails.getVat());
        product.setIndex(productDetails.getIndex());

        Product updatedProduct = productRepository.save(product);
        return updatedProduct;
    }

    @GetMapping("/product/search")
    public Page<Product> getProduct(@RequestParam("param") String param,
                                    @RequestParam("centreId") Long centreId,
                                    Pageable pageable){
        Page<Product> page = productRepository.searchProduct("%" + param + "%",centreId, pageable);
        page.forEach(p -> productMap.map(p));
        return page;
    }

    @GetMapping("vat/all")
    public  List<Map<String,Object>> vat(){
        return VAT_RATE.toList();
    }

    @PostMapping("/product/delete")
    public ResponseEntity deleteProduct(@RequestParam("id") Long id){
        productRepository.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/vegetable")
    public List<Product> getProductByVegetableCentre(@RequestParam("centreId") Long centreId){
        List<Product> product = productRepository.findByVegetableCentreId(centreId);
        product.forEach(p -> productMap.map(p));
        return product;
    }


}
