package pl.xsoftpont.panelv.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.xsoftpont.panelv.model.User;
import pl.xsoftpont.panelv.model.UserDto;
import pl.xsoftpont.panelv.model.UserHelperService;
import pl.xsoftpont.panelv.repository.VegetableCentreRepository;

@RestController
public class UserController extends AbstractController {

    @Autowired
    UserHelperService userHelperService;


    @GetMapping("/users/current")
    public UserDto getCurrentUser() {
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            return userHelperService.mapToDto(user);
        } else {
            return new UserDto();
        }

    }
}
