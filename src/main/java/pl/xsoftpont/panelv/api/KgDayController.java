package pl.xsoftpont.panelv.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.xsoftpont.panelv.model.KgDayView;
import pl.xsoftpont.panelv.repository.KgDayRepository;

import java.util.List;

@RestController
public class KgDayController {

    @Autowired
    KgDayRepository kgDayRepository;

    @RequestMapping("/kgDay/all")
    public List<KgDayView> findAll() {
        return kgDayRepository.findAll();
    }
}
