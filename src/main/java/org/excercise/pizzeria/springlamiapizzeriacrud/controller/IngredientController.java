package org.excercise.pizzeria.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.excercise.pizzeria.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.excercise.pizzeria.springlamiapizzeriacrud.model.Ingredient;
import org.excercise.pizzeria.springlamiapizzeriacrud.model.Pizza;
import org.excercise.pizzeria.springlamiapizzeriacrud.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("allIngredients", ingredientService.getAll());
        model.addAttribute("ingredient", new Ingredient());
        return "ingredients/index";
    }

    @PostMapping("/save")
    public String doSave(@Valid @ModelAttribute(name = "ingredient") Ingredient ingredient,
                         BindingResult bs,
                         Model model) {
        if (bs.hasErrors()) {
            // ricreo la view ripassando l'ingrediente
            model.addAttribute("allIngredients", ingredientService.getAll());
            return "ingredients/index";
        }
        // salvo i dati
        ingredientService.create(ingredient);
        return "redirect:/ingredients";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
            Ingredient ingredient = ingredientService.getById(id);
            model.addAttribute("ingredient", ingredient);
            model.addAttribute("ingredients", ingredientService.getAll());
            return "ingredients/index";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute Ingredient ingredient, BindingResult bs, Model model,
                         @PathVariable Integer id) {
        if (bs.hasErrors()) {
            model.addAttribute("ingredients", ingredientService.getAll());
            return "ingredients/index";
        }
        ingredientService.update(ingredient);
        model.addAttribute("ingredients", ingredientService.getAll());
        return "redirect:/ingredients";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        ingredientService.deleteById(id);
        return "redirect:/ingredients";
    }

}
