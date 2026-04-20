package io.github.alblm28.reserva_cabanas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeguridadConfig {

    @Bean
    public BCryptPasswordEncoder codificador() {
        return new BCryptPasswordEncoder();
    }
}