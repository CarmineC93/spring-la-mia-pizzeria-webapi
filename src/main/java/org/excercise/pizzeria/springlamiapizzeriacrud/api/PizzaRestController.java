package org.excercise.pizzeria.springlamiapizzeriacrud.api;

import jakarta.validation.Valid;
import org.excercise.pizzeria.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.excercise.pizzeria.springlamiapizzeriacrud.model.Pizza;
import org.excercise.pizzeria.springlamiapizzeriacrud.model.SpecialOffer;
import org.excercise.pizzeria.springlamiapizzeriacrud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/pizzas")
public class PizzaRestController {

    @Autowired
    private PizzaService pizzaService;

    // lista di tutte le pizze
    @GetMapping
/*    public List<Pizza> index() {
        return pizzaService.getAllPizzas();
    }*/
   public List<Pizza> index(@RequestParam(name = "q") Optional<String> search) {
        if (search.isPresent()) {
            return pizzaService.getFilteredPizzas(search.get());
        }
        return pizzaService.getAllPizzas();
    }

    // singola pizza
    @GetMapping("/{id}")
    public Pizza getById(@PathVariable Integer id) {
        try {
            return pizzaService.getById(id);
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    // creare pizza
    @PostMapping
    public Pizza create(@Valid @RequestBody Pizza pizza) {
        return pizzaService.createPizza(pizza);
    }

    // update pizza
    @PutMapping("/{id}")
    public Pizza update(@PathVariable Integer id, @Valid @RequestBody Pizza pizza) {
        try {
            return pizzaService.updatePizza(pizza, id);
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // eliminare pizza
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        try {
            boolean success = pizzaService.deleteById(id);
            if (!success) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Unable to delete pizza because it has special offers");
            }
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    // lista di tutte le special offers
    @GetMapping("/{id}/specialOffers")
    public List<SpecialOffer> getPizzaSpecialOffers(@PathVariable("id") Integer pizzaId) {
        Pizza pizza = null;
        try {
            pizza = pizzaService.getById(pizzaId);
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return pizza.getSpecialOffers();
    }
}