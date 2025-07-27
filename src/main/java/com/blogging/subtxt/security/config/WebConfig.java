package com.blogging.subtxt.security.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // CORS config to allow frontend like Flutter or web apps to call APIs
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }


}
    // frontend api call for web

    //.allowedOrigins(
    //       "http://localhost:3000",          "Web app (React, Flutter web) with locally"
    //       "http://127.0.0.1:3000",          "Web app (React, Flutter web) with system IP"
    //       "http://your-prod-domain.com")    "Web app for deployment with  domain"
