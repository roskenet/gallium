package de.roskenet.gallium

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@Configuration
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests {
                it.requestMatchers("/api/public").permitAll()
                    .requestMatchers("/api/private").authenticated()
            }
            .oauth2ResourceServer { it.jwt { jwtConfigurer -> } }
//            .sessionManagement { it.sessionCreationPolicy(STATELESS) }

        return http.build()
    }
}

@RestController
@RequestMapping("/api")
class ApiController {
    @GetMapping("/public")
    fun publicEndpoint(): String {
        return "Öffentlicher Bereich - keine Authentifizierung erforderlich"
    }

    @GetMapping("/private")
    fun privateEndpoint(): String {
        return "Geschützter Bereich - Authentifizierung erforderlich"
    }
}