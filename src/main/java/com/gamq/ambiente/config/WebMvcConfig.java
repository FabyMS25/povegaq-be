package com.gamq.ambiente.config;

import com.gamq.ambiente.audit.UsernameAuditorAware;
import com.gamq.ambiente.interceptors.HeaderInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    @Qualifier("headerInterceptor")
    private HeaderInterceptor headerInterceptor;

    @Bean
    AuditorAware<String> auditorProvider() {
        return new UsernameAuditorAware();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(headerInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/reporte/**", "api/*", "/api/actividad/uuid/**",
                        "/api/notificacion/uuid/**",
                        "/api/certificado/codigo/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(
                        "http://localhost:4200",
                        "http://localhost:4201",
                        "http://200.58.81.34:4210", // frontend
                        "http://quillacollopage.test",
                        "http://quillacollo.gob.bo",
                        "https://quillacollo.gob.bo"

                )
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
        WebMvcConfigurer.super.addCorsMappings(registry);
    }
}

