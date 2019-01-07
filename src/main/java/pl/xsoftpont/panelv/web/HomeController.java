package pl.xsoftpont.panelv.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.xsoftpont.panelv.api.AbstractController;
import pl.xsoftpont.panelv.model.User;
import pl.xsoftpont.panelv.repository.UserRepository;

import java.security.Principal;

@Controller
public class HomeController extends AbstractController {

    @Autowired
    UserRepository usersRepository;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        Principal principal = req.getUserPrincipal();
        User user = usersRepository.findByLoginEquals(principal.getName());
        session.setAttribute("user", user);

        if(user.getRole() == 2){
            return "farmerHome";
        }
        if(user.getRole() == 3){
            return "clientHome";
        }
        return "home";


    }

    /*@RequestMapping("/")
    public String main() {
        return "home";
    }*/
}
