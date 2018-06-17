package com.ia.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/alta-usuario")
    public String altaUsuario() {
        return"alta-usuario";
    }

    @GetMapping("/diagnostic")
    public String diagnostic() {
        return"diagnostic";
    }
}
