package com.catalogs.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/login")
    public String loging(){
        return "login";
    }

    @GetMapping("/req/singup")
    public String singup(){
        return "singup";
    }
}
