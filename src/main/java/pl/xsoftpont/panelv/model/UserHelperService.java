package pl.xsoftpont.panelv.model;

import org.springframework.stereotype.Service;

@Service
public class UserHelperService {



    public UserDto mapToDto(User user){

        UserDto userDto = new UserDto();
        userDto.setLogin(user.getLogin());
        userDto.setId(user.getId());
        userDto.setRole(user.getRole());

        return userDto;
    }
}
