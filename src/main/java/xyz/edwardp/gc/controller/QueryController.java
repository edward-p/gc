package xyz.edwardp.gc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.edwardp.gc.execption.UnkownGarbageCategoryException;
import xyz.edwardp.gc.type.Garbage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Controller
public class QueryController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/query")
    public String query(@RequestParam String value, Model model){
        List<Garbage> garbages=new ArrayList<Garbage>();
        jdbcTemplate.query(String.format("select name,category from Garbage where name like '%%%s%%'",value),
                new RowMapper<Object>() {
                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        try {
                            garbages.add(new Garbage(rs.getString("name"), rs.getInt("category")));
                        } catch (UnkownGarbageCategoryException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
        if (!value.equals("")){
            jdbcTemplate.execute(String.format("insert ignore into Search values('%s',0)",value));
            jdbcTemplate.execute(String.format("update Search set search_count=search_count+1 where keyword='%s'",value));
        }
        model.addAttribute("value",value);
        model.addAttribute("garbages",garbages);
        return "searchbox_with_result";
    }
}
