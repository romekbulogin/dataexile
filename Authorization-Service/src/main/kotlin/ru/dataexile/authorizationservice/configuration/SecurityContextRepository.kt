package ru.dataexile.authorizationservice.configuration

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class SecurityContextRepository(
    private val authenticationManager: AuthenticationManager
) : ServerSecurityContextRepository {
    override fun save(exchange: ServerWebExchange?, context: SecurityContext?): Mono<Void> {
        throw IllegalStateException("Save method is not supported")
    }

    override fun load(exchange: ServerWebExchange?): Mono<SecurityContext> {
        val authenticationHeader = exchange
            ?.request?.headers
            ?.getFirst(HttpHeaders.AUTHORIZATION)

        if (
            authenticationHeader?.isNotEmpty() == true
            || authenticationHeader?.startsWith("Bearer ") == true
        ) {
            val token = authenticationHeader.substring(7)
            val authentication = UsernamePasswordAuthenticationToken(token, token)
            return Mono.just(
                authenticationManager
                    .authenticate(authentication) as SecurityContextImpl
            )
        }
        return Mono.empty()
    }
}