package ru.dataexile.authorizationservice.dto.request

data class AuthenticationRequest(
    var username: String,
    var password: String
)