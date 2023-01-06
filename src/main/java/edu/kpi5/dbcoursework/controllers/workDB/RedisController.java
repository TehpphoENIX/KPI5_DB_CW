package edu.kpi5.dbcoursework.controllers.workDB;

import edu.kpi5.dbcoursework.dbaccess.workDB.ContributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class RedisController {
    @Autowired
    ContributionRepository contributionRepository;
    @GetMapping("/redis")
    public String home(){

        return "Welcome to redis";
    }
}