package pl.xsoftpont.panelv.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClientCentreController {

    @RequestMapping("/centre")
    public String clientCentre() {
        return "clientCentre";
    }

}
