package com.njuse.uctaserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RootRedictController {

    @GetMapping(value = "/")
    public String rootRedict(){
        return "redirect:/swagger-ui.html";
    }


}
