package pl.xsoftpont.panelv.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FarmerContractController {

    @RequestMapping("/fcontract")
    public String farmerContract() {
        return "farmerContract";
    }
}
