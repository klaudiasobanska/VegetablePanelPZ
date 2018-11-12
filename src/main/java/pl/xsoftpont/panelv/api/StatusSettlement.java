package pl.xsoftpont.panelv.api;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum StatusSettlement {

    NEW(1,"Nowy"),
    INPROGRESS(2,"W trakcie"),
    END(3,"Zakończony");

    private Integer id;
    private String name;

    StatusSettlement(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public static StatusSettlement fromName(String name) throws Exception {
        return Stream.of(values()).filter(p -> p.name.equals(name)).findFirst().orElseThrow(Exception::new);
    }

    public static StatusSettlement fromId(Integer id) throws Exception {
        return Stream.of(values()).filter(p -> p.id.equals(id)).findFirst().orElseThrow(Exception::new);
    }

}
