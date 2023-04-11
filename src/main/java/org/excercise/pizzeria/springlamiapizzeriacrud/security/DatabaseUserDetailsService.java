package org.excercise.pizzeria.springlamiapizzeriacrud.security;

import org.excercise.pizzeria.springlamiapizzeriacrud.model.User;
import org.excercise.pizzeria.springlamiapizzeriacrud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;



public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override                                    //email
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(username);
        if ( user.isPresent()){
            return new DatabaseUserDetails(user.get());
        }else{
         throw new UsernameNotFoundException("user with this username: " + username + " not found");
        }
    }
}
