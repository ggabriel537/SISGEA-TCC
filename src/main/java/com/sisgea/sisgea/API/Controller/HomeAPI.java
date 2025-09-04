package com.sisgea.sisgea.API.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeAPI {
    @GetMapping("/")
    public String home() {
        return "redirect:/sisgea/login.html";
    }
    
}
