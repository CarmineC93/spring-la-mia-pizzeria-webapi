package org.excercise.pizzeria.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.excercise.pizzeria.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.excercise.pizzeria.springlamiapizzeriacrud.model.CrudMessage;
import org.excercise.pizzeria.springlamiapizzeriacrud.model.Pizza;
import org.excercise.pizzeria.springlamiapizzeriacrud.repository.PizzaRepository;
import org.excercise.pizzeria.springlamiapizzeriacrud.service.IngredientService;
import org.excercise.pizzeria.springlamiapizzeriacrud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

/*        @Autowired
        private PizzaRepository pizzaRepository;*/

        @Autowired
        private PizzaService pizzaService;

        @Autowired
        private IngredientService ingredientService;


        /* //uso Option perchè il parametro potrà esserci come non esserci
        public String index(Model model, @RequestParam(name = "q") Optional<String> keyword) {
            List<Pizza> pizzas;
            //se il parametro non arriva visualizerà tutto l'elenco
            if(keyword.isEmpty()){
               pizzas = pizzaRepository.findAll(Sort.by("name"));
                model.addAttribute("list", pizzas);
            }
            //se invece viene passato un parametro, verrà estratto e fatta partire una query con il metodo repository
            else{
                pizzas = pizzaRepository.findByNameContainingIgnoreCase(keyword.get());
                //se ci saranno parametri di ricerca collego il parametro passato nell'input al model
                model.addAttribute("keyword", keyword.get());
            }
            model.addAttribute("list", pizzas);
            return "/pizzas/index";
        }*/

        @GetMapping
        public String index(Model model, @RequestParam(name = "q") Optional<String> keyword) {
            List<Pizza> pizzas;
            if (keyword.isEmpty()) {
                pizzas = pizzaService.getAllPizzas();
            } else {
                pizzas = pizzaService.getFilteredPizzas(keyword.get());
                model.addAttribute("keyword", keyword.get());
            }
            model.addAttribute("list", pizzas);

            return "/pizzas/index";
        }


        /*
        @GetMapping("/{pizzaId}")
        public String show(@PathVariable("pizzaId") Integer id, Model model){
            Optional<Pizza> result = pizzaRepository.findById(id);
            if(result.isPresent()){
                model.addAttribute("pizza", result.get());
                return "/pizzas/show";
            }else{
                //se l'id chiamato non esiste lancerà un'eccezione
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }*/

        @GetMapping("/{pizzaId}")
        public String show(@PathVariable("pizzaId") Integer id, Model model) {
            try {
                Pizza pizza = pizzaService.getById(id);
                model.addAttribute("pizza", pizza);
                return "/pizzas/show";
            } catch (PizzaNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
            }
        }

        @GetMapping("/create")
        public String create(Model model){
            model.addAttribute("pizza", new Pizza());
            //associo al model di pizza un attributo ListaIngredienti con tutti gli ingredienti presi dal metodo nell service
            model.addAttribute("ingredientList", ingredientService.getAll());
            return "/pizzas/create";
        }


        @PostMapping("/create")
        public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult br, RedirectAttributes redirectAttributes, Model model){

            if(br.hasErrors()){
                //per far lasciare i campi selezionati in caso di altri campi con errore e ricaricamento pagina
                model.addAttribute("ingredientList", ingredientService.getAll());

                return "/pizzas/create";
            }

            /*
            //per avere più controllo sul nuovo elemento, specifico i campi che dovranno comporlo
            Pizza pizzaToSave = new Pizza();
            pizzaToSave.setName(formPizza.getName());
            pizzaToSave.setDescription(formPizza.getDescription());
            pizzaToSave.setPrice(formPizza.getPrice());

            //per far persistere il nuovo elemento
              pizzaRepository.save(pizzaToSave);
            */

            redirectAttributes.addFlashAttribute("message", new CrudMessage(CrudMessage.CrudMessageType.SUCCESS, "New Pizza successfully created"));

            pizzaService.createPizza(formPizza);
            return "redirect:/pizzas";
        }

        @GetMapping("/edit/{id}")
        public String edit(@PathVariable Integer id, Model model) {
            try {
                Pizza pizza = pizzaService.getById(id);
                model.addAttribute("pizza", pizza);
                model.addAttribute("ingredientList", ingredientService.getAll());

                return "/pizzas/edit";
            } catch (PizzaNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza with id " + id + " not found");
            }
        }

        @PostMapping("/edit/{id}")
        public String doEdit(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bs, RedirectAttributes redirectAttributes, Model model){
            if(bs.hasErrors()){
                System.out.println(bs);
                model.addAttribute("ingredientList", ingredientService.getAll());
                return "/pizzas/edit";
            }
            try{
                Pizza updatedPizza = pizzaService.updatePizza(formPizza, id);
                redirectAttributes.addFlashAttribute("message", new CrudMessage(CrudMessage.CrudMessageType.SUCCESS, "Pizza " + id + " successfully updated"));

                model.addAttribute("modifiedBy", ingredientService.getAll());


                return "redirect:/pizzas/" + Integer.toString(updatedPizza.getId());
            }catch(PizzaNotFoundException e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza with id " + id + " not found");
            }
        }

        @GetMapping("/delete/{id}")
        public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try{
            boolean success = pizzaService.deleteById(id);
            if (success){
                redirectAttributes.addFlashAttribute("message", new CrudMessage(CrudMessage.CrudMessageType.SUCCESS, "Pizza " + id + " successfully deleted"));
            }
            else{
                redirectAttributes.addFlashAttribute("message", new CrudMessage(CrudMessage.CrudMessageType.ERROR, "Pizza " + id + " can NOT be deleted"));

                }
            }catch(PizzaNotFoundException e){
/*
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);

                anzichè lanciare eccezione mandiamo un messaggio userfriendly
*/
                redirectAttributes.addFlashAttribute("message", new CrudMessage(CrudMessage.CrudMessageType.ERROR, "Pizza " + id + " can NOT be deleted, because doesn't exist"));

        }
            return "redirect:/pizzas";

        }

}

