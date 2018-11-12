package pl.xsoftpont.panelv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.xsoftpont.panelv.exception.ResourceNotFoundException;
import pl.xsoftpont.panelv.model.Document;
import pl.xsoftpont.panelv.repository.DocumentRepository;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
public class DocumentController {

    @Autowired
    DocumentRepository documentRepository;

    @RequestMapping("/document/all")
    public List<Document> findAll() {return documentRepository.findAll();}

    @PostMapping("/document/add")
    public Document createDocument(@Valid @RequestBody Document document){return documentRepository.save(document);}

    @GetMapping("/document/{id}")
    public Document getDocumentById(@PathVariable("id") Long id){
        return documentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Document", "id", id));
    }

    @PutMapping("/document/{id}")
    public Document updateDocument(@PathVariable("id") Long id,
                                 @Valid @RequestBody Document documentDetails){
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document", "id", id));

        document.setWarehouseId(documentDetails.getWarehouseId());
        document.setProviderId(documentDetails.getProviderId());
        document.setProductId(documentDetails.getProductId());
        document.setDocDate(documentDetails.getDocDate());
        document.setAmount(documentDetails.getAmount());
        document.setDocPrice(documentDetails.getDocPrice());

        Document updatedDocument = documentRepository.save(document);
        return updatedDocument;
    }

    @PostMapping("/document/delete")
    public ResponseEntity deleteProduct(@RequestParam("id") Long id){
        documentRepository.deleteDocument(id);
        return ResponseEntity.ok().build();
    }
}
