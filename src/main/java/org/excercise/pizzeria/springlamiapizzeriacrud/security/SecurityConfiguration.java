package org.excercise.pizzeria.springlamiapizzeriacrud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {


    @Bean
    UserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        // creo un authentication provider basato su database Data Access Object
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // associo il mio DatabaseUserDetailsService
        provider.setUserDetailsService(userDetailsService());
        // associo il mio PasswordEncoder
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/ingredients", "/ingredients/**").hasAuthority("ADMIN")
                .requestMatchers("/specialOffers/**").hasAuthority("ADMIN")
                .requestMatchers("/pizzas/create", "/pizzas/edit/**", "/pizzas/delete/**").hasAuthority("ADMIN")
                .requestMatchers("/", "/pizzas", "/pizzas/**").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/pizzas/**").hasAuthority("ADMIN")
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .and().logout()
                .and().exceptionHandling();

        http.csrf().disable(); //USE ONLY IN DEVELOPMENT
        return http.build();
    }

}
