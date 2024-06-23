package me.kiryakov.animal_chips.configuration;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String[] WHITELIST = {"/accounts/**", "/animals/**", "/locations/**"};

    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(auth -> {
            auth.dispatcherTypeMatchers(DispatcherType.ERROR, DispatcherType.FORWARD).permitAll()
                    .requestMatchers("/registration").anonymous()
                    .requestMatchers(HttpMethod.GET, WHITELIST ).permitAll()
                    .anyRequest().authenticated();
        });
        http.httpBasic(Customizer.withDefaults());
        http.csrf(customizer -> customizer.disable());
        http.cors(customizer -> customizer.disable());
        return http.build();
    }
}
