package pl.xsoftpont.panelv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.xsoftpont.panelv.model.KgDayView;

import java.util.List;

@Repository
public interface KgDayRepository extends JpaRepository<KgDayView, Long> {

    List<KgDayView> findAll();
}
