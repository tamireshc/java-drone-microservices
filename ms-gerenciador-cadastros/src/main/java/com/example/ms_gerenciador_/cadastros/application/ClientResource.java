package com.example.ms_gerenciador_.cadastros.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("teste")
public class ClientResource {
    @GetMapping
    public String status() {
        return "ok";
    }
}
