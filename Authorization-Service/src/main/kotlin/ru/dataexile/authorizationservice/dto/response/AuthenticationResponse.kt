package ru.dataexile.authorizationservice.dto.response

data class AuthenticationResponse(
    var token: String,
    var user: User
)