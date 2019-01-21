package pl.xsoftpont.panelv.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SettClientController {

    @RequestMapping("/settC")
    public String settClient() {
            return "settlementC";
        }

}
