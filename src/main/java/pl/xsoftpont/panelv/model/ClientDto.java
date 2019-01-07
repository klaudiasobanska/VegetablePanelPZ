package pl.xsoftpont.panelv.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto {


    private Long id;
    private String name;
    private String city;
    private String address;
    private String email;
    private String phoneNumber;
    private String nip;
    private Long userId;


}
