package com.example.Web2.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                .permitAll()

// .requestMatchers(HttpMethod.POST, "users/register").permitAll()

                                .requestMatchers("/error?continue").permitAll()
                                .requestMatchers("/home").permitAll()

                                .requestMatchers("/login").permitAll()

                                .requestMatchers(HttpMethod.GET,"/user/registration").permitAll()
                                .requestMatchers(HttpMethod.POST,"/user/registration").permitAll()
                                .requestMatchers("/user/details/**").permitAll()

                                .requestMatchers("/offers/add").authenticated()


                                .requestMatchers("/offers/details/**").permitAll()
                                .requestMatchers("/offers/edit/**").authenticated()
                                .requestMatchers("/offers/delete/**").authenticated()


                                .requestMatchers("/offers/**").permitAll()
                                .requestMatchers("/user/**").authenticated()
                                .requestMatchers("/admin/**").hasRole("Admin")

                ).formLogin((formLogin) -> formLogin.defaultSuccessUrl("/home"))
                .logout((logout) -> logout.logoutSuccessUrl("/home"));

        httpSecurity.formLogin((formLogin) -> formLogin.defaultSuccessUrl("/home").permitAll());

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}