package org.excercise.pizzeria.springlamiapizzeriacrud.service;

import org.excercise.pizzeria.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.excercise.pizzeria.springlamiapizzeriacrud.model.Pizza;
import org.excercise.pizzeria.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PizzaService {

    @Autowired
    PizzaRepository pizzaRepository;

    public Pizza createPizza(Pizza formPizza) {
        Pizza pizzaToPersist = new Pizza();
        pizzaToPersist.setName(formPizza.getName());
        pizzaToPersist.setDescription(formPizza.getDescription());
        pizzaToPersist.setPrice(formPizza.getPrice());
        pizzaToPersist.setIngredients(formPizza.getIngredients());
        pizzaToPersist.setUser(formPizza.getUser());
        return pizzaRepository.save(pizzaToPersist);
    }

    public Pizza updatePizza(Pizza formPizza, Integer id) throws PizzaNotFoundException {
        Pizza pizzaToUpdate = getById(id);
        pizzaToUpdate.setName(formPizza.getName());
        pizzaToUpdate.setDescription(formPizza.getDescription());
        pizzaToUpdate.setPrice(formPizza.getPrice());
        pizzaToUpdate.setIngredients(formPizza.getIngredients());
        pizzaToUpdate.setUser(formPizza.getUser());
        return pizzaRepository.save(pizzaToUpdate);
    }

    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll(Sort.by("name"));
    }

    public List<Pizza> getFilteredPizzas(String keyword) {
        return pizzaRepository.findByNameContainingIgnoreCase(keyword);
    }

    public Pizza getById(Integer id) throws PizzaNotFoundException {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PizzaNotFoundException(Integer.toString(id));
        }
    }

    public boolean deleteById(Integer id) throws PizzaNotFoundException {
        pizzaRepository.findById(id).orElseThrow(() -> new PizzaNotFoundException(Integer.toString(id)));
        try {
            pizzaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
