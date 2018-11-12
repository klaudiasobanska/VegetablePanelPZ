package pl.xsoftpont.panelv.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Getter
public enum VAT_RATE {

    TWENTYTHREE(1, "23"),
    EIGHT(2, "8"),
    ZW(3, "zw");


    private Integer id;
    private String name;

    VAT_RATE(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static VAT_RATE fromName(String name) throws Exception {
        return Stream.of(values()).filter(p -> p.name.equals(name)).findFirst().orElseThrow(Exception::new);
    }

    public static VAT_RATE fromId(Integer id) throws Exception {
        return Stream.of(values()).filter(p -> p.id.equals(id)).findFirst().orElseThrow(Exception::new);
    }

    public static List<Map<String, Object>> toList() {
        List<Map<String, Object>> vatRate = new ArrayList<>();
        for (VAT_RATE r : values()) {
            Map<String, Object> v = new HashMap<>();
            v.put("id", r.getId());
            v.put("name", r.getName());
            vatRate.add(v);
        }

        return vatRate;
    }
}
