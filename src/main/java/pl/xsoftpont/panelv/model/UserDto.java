package pl.xsoftpont.panelv.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Long id;

    private String login;

    private Integer role;


}
