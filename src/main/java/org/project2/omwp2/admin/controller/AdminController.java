package org.project2.omwp2.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping({"","/index"})
    public String Go(){

       return "adminPage";
    }

    @GetMapping("/board")
    public String boardPage(){
        return "boardPage";
    }
}
