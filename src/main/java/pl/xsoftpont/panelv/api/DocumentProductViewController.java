package pl.xsoftpont.panelv.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.xsoftpont.panelv.model.DocumentProductView;
import pl.xsoftpont.panelv.repository.DocumentProductViewRepository;


import java.util.List;

@RestController
public class DocumentProductViewController {

    @Autowired
    DocumentProductViewRepository documentProductViewRepository;

    @RequestMapping("/view/all")
    public List<DocumentProductView> findAll() {
        return documentProductViewRepository.findAll();
    }

    @GetMapping("view/document/product/search")
    public Page<DocumentProductView> getDocumentProduct(@RequestParam("warehouseId") Long warehouseId,
                                                        @RequestParam(value = "docDate", required = false) String docDate,
                                                        @RequestParam("centreId") Long centreId,
                                                        Pageable pageable) {
        Page<DocumentProductView> page = documentProductViewRepository.searchDocumentProduct(warehouseId, StringUtils.isEmpty(docDate)?"":docDate,centreId, pageable);
        return page;
    }
}
