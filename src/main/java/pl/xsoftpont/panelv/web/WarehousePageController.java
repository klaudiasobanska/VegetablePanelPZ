package pl.xsoftpont.panelv.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WarehousePageController {
    @RequestMapping("/warehouse")
    public String warehouse() {
        return "warehouse";
    }
}
