package xyz.edwardp.gc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.edwardp.gc.type.Garbage;
import xyz.edwardp.gc.type.GarbageForm;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AddController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/add")
    public String display(GarbageForm garbageForm) {
        garbageForm.getGarbages().add(new Garbage());
        return "add_page";
    }

    @RequestMapping(value = "/add", params = {"add"})
    public String add(GarbageForm garbageForm) {
        garbageForm.getGarbages().add(new Garbage());
        return "add_page";
    }

    @RequestMapping(value = "/add", params = {"remove"})
    public String remove(GarbageForm garbageForm, HttpServletRequest req) {
        int rowId = Integer.parseInt(req.getParameter("remove"));
        garbageForm.getGarbages().remove(rowId);
        return "add_page";
    }

    @RequestMapping(value = "/add", params = {"submit"})
    public String submit(GarbageForm garbageForm, Model model) {

        for (Garbage garbage : garbageForm.getGarbages()) {
            if (garbage.getName().equals("") || garbage.getCategory().equals("")) {
                model.addAttribute("result", "failed");
                return "add_page";
            }
        }

        for (Garbage garbage : garbageForm.getGarbages()) {
            jdbcTemplate.execute(String.format("insert ignore into garbage values('%s',%d)", garbage.getName(), garbage.getCategoryid()));
        }
        model.addAttribute("result", "success");
        return "add_page";
    }
}
