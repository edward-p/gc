package xyz.edwardp.gc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/")
    public String result(Model model) {
        List<String> keywords = new ArrayList<String>();
        jdbcTemplate.query("select top 10 keyword from (select * from search order by search_count desc)",
                new RowMapper<Object>() {
                    @Override
                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                        keywords.add(rs.getString("keyword"));
                        return null;
                    }
                });
        model.addAttribute("keywords", keywords);
        return "searchbox";
    }
}
