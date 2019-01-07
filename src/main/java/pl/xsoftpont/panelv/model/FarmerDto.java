package pl.xsoftpont.panelv.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FarmerDto {

    private Long id;
    private String name;
    private String city;
    private String address;
    private String email;
    private String phoneNumber;
    private Long userId;
}
