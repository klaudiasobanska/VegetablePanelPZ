package pl.xsoftpont.panelv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.xsoftpont.panelv.model.VegetableCentre;

import java.util.List;

@Repository
public interface VegetableCentreRepository extends JpaRepository<VegetableCentre, Long> {

    List<VegetableCentre> findAll();
}
