package com.crimsonlogic.hospitalmanagement.controller;

import javax.servlet.http.HttpSession;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.model.UserAccount;
import com.crimsonlogic.hospitalmanagement.services.*;

@Controller
public class LoginController {

    private final AuthenticationServiceImpl authenticationService =
            new AuthenticationServiceImpl();

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        try {

            UserAccount user =
                    authenticationService.login(
                            username,
                            password);

            session.setAttribute(
                    "userId",
                    user.getUserId());

            session.setAttribute(
                    "role",
                    user.getRole());

            session.setAttribute(
                    "username",
                    user.getUsername());

            return "redirect:/dashboard";

        } catch (ValidationException e) {

            model.addAttribute(
                    "error",
                    e.getMessage());

            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}