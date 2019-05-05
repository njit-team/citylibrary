package edu.njit.citylibrary.citylibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @GetMapping(value = "/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping(value = "/adminlogin")
    public String loginadmin(@RequestParam String username, String password, Model model){
        return "adminmenu";
    }
}
