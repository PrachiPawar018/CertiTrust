package com.certitrust.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/verify")
    public String verificationForm(
            @RequestParam(required = false) String certificateId,
            Model model) {
        model.addAttribute("certificateId", certificateId == null ? "" : certificateId);
        return "verify";
    }
}