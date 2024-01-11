package ru.dataexile.authorizationservice.service

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import reactor.core.publisher.Mono
import ru.dataexile.authorizationservice.configuration.AuthenticationManager
import ru.dataexile.authorizationservice.dto.requst.AuthenticationRequest
import ru.dataexile.authorizationservice.dto.response.AuthenticationResponse
import ru.dataexile.authorizationservice.dto.response.User
import ru.dataexile.authorizationservice.entity.OwnerEntity
import ru.dataexile.authorizationservice.repository.OwnerRepository

@Service
class AuthorizationService(
    private val ownerReactiveUserDetailsService: ReactiveUserDetailsService,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtService: JwtService
) {
    private val logger = KotlinLogging.logger { }
    fun authentication(request: AuthenticationRequest): Mono<ResponseEntity<Any>> {
        logger.info("[AUTHENTICATION]: ${request.username}")
        return ownerReactiveUserDetailsService
            .findByUsername(request.username)
            .cast(OwnerEntity::class.java)
            .map { owner ->
                if (passwordEncoder.matches(request.password, owner.password))
                    ResponseEntity.ok(
                        AuthenticationResponse(
                            jwtService.generateToken(owner),
                            User(owner.username, owner.getEmail(), owner.authorities.first().authority)
                        )
                    )
                else
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
            }
    }
}