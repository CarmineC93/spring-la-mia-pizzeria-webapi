package org.excercise.pizzeria.springlamiapizzeriacrud.service;


import org.excercise.pizzeria.springlamiapizzeriacrud.model.SpecialOffer;
import org.excercise.pizzeria.springlamiapizzeriacrud.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialOfferService {

    @Autowired
    private SpecialOfferRepository specialOfferRepository;

    public SpecialOffer create(SpecialOffer formOffer) {
        return specialOfferRepository.save(formOffer);
    }
}