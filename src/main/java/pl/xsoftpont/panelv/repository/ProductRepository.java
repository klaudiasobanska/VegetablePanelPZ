package pl.xsoftpont.panelv.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;
import pl.xsoftpont.panelv.model.Product;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();

    List<Product> findByVegetableCentreId(@Param("centreId") Long centreId);

    @Query(value = "select * from products p where ((p.name like :param) or (p.index like :param) and (vegetable_centre_id = :centreId)) /*or (p.price like :param) or (c.amount like :param))*/",
            countQuery = "select count(*) from products p where ((p.name like :param) or (p.index like :param) and (vegetable_centre_id = :centreId))/* or (p.price like :param) or (c.amount like :param))*/",
            nativeQuery = true)
    Page<Product> searchProduct(@Param("param") String param,
                                @Param("centreId") Long centreId,
                                Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from Product where id=:id")
    void deleteProduct(@Param("id") Long id);

}
