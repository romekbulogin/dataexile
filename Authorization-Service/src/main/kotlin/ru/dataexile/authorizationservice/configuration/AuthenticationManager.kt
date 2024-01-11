package ru.dataexile.authorizationservice.configuration

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import ru.dataexile.authorizationservice.service.JwtService
import java.util.stream.Collectors

@Component
class AuthenticationManager(
    private val jwtService: JwtService
) : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        val token = authentication?.credentials.toString()

        val username = jwtService.extractUsername(token)

        require(username.isNotEmpty()) {
            UsernameNotFoundException("User $username not found")
        }

        val claims = jwtService.extractAllClaims(token)
        val role = listOf(claims["role"] as String)
        val authorities = role.stream().map { role ->
            SimpleGrantedAuthority(role)
        }.collect(Collectors.toList())

        val authentication = UsernamePasswordAuthenticationToken(
            username, null, authorities
        )
        return Mono.just(authentication)
    }
}