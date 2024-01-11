package ru.dataexile.authorizationservice.service

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.dataexile.authorizationservice.dto.request.AuthenticationRequest
import ru.dataexile.authorizationservice.dto.request.RegistrationRequest
import ru.dataexile.authorizationservice.dto.response.AuthenticationResponse
import ru.dataexile.authorizationservice.dto.response.User
import ru.dataexile.authorizationservice.entity.OwnerEntity
import ru.dataexile.authorizationservice.entity.Role
import ru.dataexile.authorizationservice.repository.OwnerRepository
import java.sql.Timestamp
import java.time.Instant
import java.util.*

@Service
class AuthorizationService(
    private val ownerReactiveUserDetailsService: ReactiveUserDetailsService,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val ownerRepository: OwnerRepository,
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

    fun registration(request: RegistrationRequest): Mono<ResponseEntity<Any>> {
        logger.info("[REGISTRATION]: ${request.username}")

        require(request.password.isNotEmpty()) {
            IllegalArgumentException("Password is empty")
        }
        require(request.password == request.confirmPassword) {
            IllegalArgumentException("Password mismatch")
        }

        val ownerEntity = OwnerEntity(
            UUID.randomUUID(),
            request.username,
            request.password,
            request.email,
            Timestamp.from(Instant.now()),
            Role.USER
        )
        return ownerRepository.save(ownerEntity).map { owner ->
            try {
                ResponseEntity.ok(
                    AuthenticationResponse(
                        jwtService.generateToken(owner),
                        User(owner.username, owner.getEmail(), owner.authorities.first().authority)
                    )
                )
            } catch (ex: Exception) {
                logger.error(ex.message)
                ResponseEntity.badRequest().build()
            }
        }
    }
}