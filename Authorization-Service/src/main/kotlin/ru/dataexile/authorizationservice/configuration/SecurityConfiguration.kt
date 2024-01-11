package ru.dataexile.authorizationservice.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfiguration(
    private val authenticationManager: AuthenticationManager,
    private val securityContextRepository: SecurityContextRepository
) {

    @Bean
    fun securityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        return http
            .csrf { csrf -> csrf.disable() }
            .cors { cors -> cors.disable() }
            .authorizeExchange { authorize ->
                authorize
                    .pathMatchers("/api/auth/**")
                    .permitAll()
                    .anyExchange()
                    .authenticated()
            }
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .httpBasic { http -> http.disable() }
            .formLogin { form -> form.disable() }
            .build()
    }
}