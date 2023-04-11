package org.excercise.pizzeria.springlamiapizzeriacrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.math.BigDecimal;
import java.security.PublicKey;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pizzas")
public class Pizza {

    //ATTRIBUTES / COLUMNS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Text can not be empty :(")
    private String name;
    @NotEmpty(message = "Text can not be empty :(")
    @Size(min = 10 ,max = 250, message = "The text must be smaller than 250 characters :(" )
    @Lob
    private String description;
    @NotNull(message = "Text can not be empty :(")
    @Positive (message = "Price must be greater than zero :(")
    @Column(nullable = false)
    private BigDecimal price;

    //RELATION
    @OneToMany(mappedBy = "pizza")
    private List<SpecialOffer> specialOffers;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name = "pizza_ingredient",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients;


    //CONTRUCTOR
    public Pizza(){
        super();
    }

    //GETTER & SETTER


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<SpecialOffer> getSpecialOffers() {
        return specialOffers;
    }

    public void setSpecialOffers(List<SpecialOffer> specialOffers) {
        this.specialOffers = specialOffers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


}
