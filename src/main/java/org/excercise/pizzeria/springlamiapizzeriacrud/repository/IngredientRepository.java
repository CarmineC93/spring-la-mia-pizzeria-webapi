package org.excercise.pizzeria.springlamiapizzeriacrud.repository;


import org.excercise.pizzeria.springlamiapizzeriacrud.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {


}
