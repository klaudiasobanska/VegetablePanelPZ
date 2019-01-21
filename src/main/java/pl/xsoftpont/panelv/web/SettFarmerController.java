package pl.xsoftpont.panelv.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SettFarmerController {

    @RequestMapping("/settF")
    public String settClient() {
        return "settlementF";
    }
}
