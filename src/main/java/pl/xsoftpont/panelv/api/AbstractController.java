package pl.xsoftpont.panelv.api;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController {

    @Autowired
    protected HttpServletRequest req;

    @Autowired
    protected HttpSession session;

    public Map<String, Object> simpleOkResult() {
        Map<String, Object> m = new HashMap<>();
        m.put("success", "true");
        return m;
    }

    public Map<String, Object> simpleErrorResult(String error) {
        Map<String, Object> m = new HashMap<>();
        m.put("errorMsg", error);
        return m;
    }




}