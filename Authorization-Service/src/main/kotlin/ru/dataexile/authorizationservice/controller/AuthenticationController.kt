package ru.dataexile.authorizationservice.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.dataexile.authorizationservice.dto.request.AuthenticationRequest
import ru.dataexile.authorizationservice.dto.request.RegistrationRequest
import ru.dataexile.authorizationservice.service.AuthorizationService

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val authenticationService: AuthorizationService
) {
    @PostMapping("/authentication")
    fun authentication(@RequestBody request: AuthenticationRequest) =
        authenticationService.authentication(request)

    @PostMapping("/registration")
    fun registration(@RequestBody request: RegistrationRequest) =
        authenticationService.registration(request)
}