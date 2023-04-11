package org.excercise.pizzeria.springlamiapizzeriacrud.service;

import org.excercise.pizzeria.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.excercise.pizzeria.springlamiapizzeriacrud.model.Ingredient;
import org.excercise.pizzeria.springlamiapizzeriacrud.model.Pizza;
import org.excercise.pizzeria.springlamiapizzeriacrud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredient> getAll(){
        return ingredientRepository.findAll(Sort.by("name"));
    }

    public Ingredient create(Ingredient formIngredient) {
        Ingredient ingredientToCreate = new Ingredient();
        ingredientToCreate.setName(formIngredient.getName());
        ingredientToCreate.setDescription(formIngredient.getDescription());
        return ingredientRepository.save(ingredientToCreate);
    }

    public Ingredient update(Ingredient formIngredient) {
/*        Ingredient ingredientToUpdate = new Ingredient();
        ingredientToUpdate.setName(formIngredient.getName());
        ingredientToUpdate.setDescription(formIngredient.getDescription());*/
        return ingredientRepository.save(formIngredient);
    }

    public Ingredient getById(Integer id) throws PizzaNotFoundException {
        Optional<Ingredient> result = ingredientRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PizzaNotFoundException(Integer.toString(id));
        }
    }

    public boolean deleteById(Integer id) throws PizzaNotFoundException {
        ingredientRepository.findById(id).orElseThrow(() -> new PizzaNotFoundException(Integer.toString(id)));
        try {
            ingredientRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



}
