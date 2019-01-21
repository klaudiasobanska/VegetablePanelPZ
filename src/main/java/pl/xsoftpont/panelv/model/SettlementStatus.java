package pl.xsoftpont.panelv.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Getter
public enum SettlementStatus {

    START(1,"Niezapłacone"),
    END(2,"Zapłacone");

    private Integer id;
    private String name;

    SettlementStatus(Integer id, String name){
        this.id = id;
        this.name = name;
    }


    public static SettlementStatus fromId(Integer id) throws Exception {
        return Stream.of(values()).filter(p -> p.id.equals(id)).findFirst().orElseThrow(Exception::new);
    }

    public static List<Map<String, Object>> settlementStatusToList() {
        List<Map<String, Object>> status = new ArrayList<>();
        for (SettlementStatus s : values()) {
            Map<String, Object> v = new HashMap<>();
            v.put("id", s.getId());
            v.put("name", s.getName());
            status.add(v);
        }

        return status;
    }
}
