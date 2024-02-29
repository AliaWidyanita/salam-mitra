package salam.mitra.frontend.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;

@Controller
public class BaseController {

    @GetMapping("/")
    public String home(Model model) {
        
        return "landing-page";
    }
}
