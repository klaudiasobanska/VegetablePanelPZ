package pl.xsoftpont.panelv.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.xsoftpont.panelv.api.AbstractController;

@Controller
public class LoginController extends AbstractController {


    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}
