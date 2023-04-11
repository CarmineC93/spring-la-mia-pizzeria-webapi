package org.excercise.pizzeria.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.excercise.pizzeria.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.excercise.pizzeria.springlamiapizzeriacrud.model.Pizza;
import org.excercise.pizzeria.springlamiapizzeriacrud.model.SpecialOffer;
import org.excercise.pizzeria.springlamiapizzeriacrud.repository.SpecialOfferRepository;
import org.excercise.pizzeria.springlamiapizzeriacrud.service.PizzaService;
import org.excercise.pizzeria.springlamiapizzeriacrud.service.SpecialOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/special-offers")
public class SpecialOfferController {
    @Autowired
    private SpecialOfferRepository specialOfferRepository;
    @Autowired
    private PizzaService pizzaService;
    @Autowired
    private SpecialOfferService specialOfferService;

    @GetMapping("/create")
    public String create(@RequestParam(name = "pizzaId") int id, Model model){
        SpecialOffer specialOffer = new SpecialOffer();
        specialOffer.setStartDate(LocalDate.now());
        specialOffer.setEndDate(LocalDate.now().plusDays(7));
            try {
                Pizza pizza = pizzaService.getById(id);
                specialOffer.setPizza(pizza);
            } catch (PizzaNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        model.addAttribute("specialOffer", specialOffer);
        return "/specialOffers/create";
    }


    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute SpecialOffer formOffer,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/specialOffers/create";
        }

        SpecialOffer newOffer = specialOfferService.create(formOffer);
        return "redirect:/pizzas/" + Integer.toString(newOffer.getPizza().getId());
    }

}
