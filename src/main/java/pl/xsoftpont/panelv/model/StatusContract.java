package pl.xsoftpont.panelv.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Getter
public enum StatusContract {

    START(1,"Rozpoczęta"),
    INPROGRESS(2,"W trakcie"),
    END(3,"Zakończona");

    private Integer id;
    private String name;

    StatusContract(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    public static StatusContract fromName(String name) throws Exception {
        return Stream.of(values()).filter(p -> p.name.equals(name)).findFirst().orElseThrow(Exception::new);
    }

    public static StatusContract fromId(Integer id) throws Exception {
        return Stream.of(values()).filter(p -> p.id.equals(id)).findFirst().orElseThrow(Exception::new);
    }

    public static List<Map<String, Object>> statusToList() {
        List<Map<String, Object>> status = new ArrayList<>();
        for (StatusContract s : values()) {
            Map<String, Object> v = new HashMap<>();
            v.put("id", s.getId());
            v.put("name", s.getName());
            status.add(v);
        }

        return status;
    }

}
