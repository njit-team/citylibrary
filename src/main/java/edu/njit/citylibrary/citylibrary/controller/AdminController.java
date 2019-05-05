package edu.njit.citylibrary.citylibrary.controller;

import edu.njit.citylibrary.citylibrary.domain.Admin;
import edu.njit.citylibrary.citylibrary.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class AdminController {

    private static final String ADMIN_USERNAME = "adminUsername";
    @Autowired
    AdminRepo adminRepo;

    @GetMapping(value = "/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping(value = "/adminlogin")
    public String loginAdmin(@RequestParam String username, @RequestParam String password, Model model, HttpServletRequest request) {
        Admin admin = adminRepo.adminLogin(username, password);
        if (Objects.nonNull(admin)) {
            HttpSession session = request.getSession(false);
            if (!Objects.isNull(session)) {
                session.invalidate();
            }
            session = request.getSession(true);
            session.setAttribute(ADMIN_USERNAME, admin.getUsername());
            return "adminmenu";
        } else {
            return "admin";
        }
    }
}
