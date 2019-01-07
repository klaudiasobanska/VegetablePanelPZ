package pl.xsoftpont.panelv.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FarmerCentreController {

    @RequestMapping("/fcentre")
    public String farmer() {
        return "farmerCentre";
    }
}
