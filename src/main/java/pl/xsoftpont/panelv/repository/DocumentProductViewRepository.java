package pl.xsoftpont.panelv.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.xsoftpont.panelv.model.DocumentProductView;


import java.util.List;

@Repository
public interface DocumentProductViewRepository extends JpaRepository<DocumentProductView, Long> {

    List<DocumentProductView> findAll();

    @Query(value="select * from document_product_view where ((warehouse_id = :warehouseId) and (:docDate ='' or  doc_date  = cast(:docDate as date)))",
            countQuery ="select count(*) from document_product_view where ((warehouse_id = :warehouseId) and (:docDate ='' or doc_date =  cast(:docDate as date)))",
            nativeQuery = true)
    Page<DocumentProductView> searchDocumentProduct(@Param("warehouseId") Long warehouseId,
                                                           @Param("docDate") String docDate,
                                                           Pageable pageable);
}
