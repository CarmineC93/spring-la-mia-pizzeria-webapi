package org.excercise.pizzeria.springlamiapizzeriacrud.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MenuController {

    //gestisco la rotta "/" reindirizzandola su "/pizzas"
    @GetMapping
    public String index(){
        return "redirect:/pizzas";
    }

}
