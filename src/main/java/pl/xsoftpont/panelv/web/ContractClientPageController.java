package pl.xsoftpont.panelv.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContractClientPageController {

    @RequestMapping("/contractC")
    public String facontractClient() {
        return "contractC";
    }
}
