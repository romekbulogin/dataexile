package ru.dataexile.authorizationservice.dto.requst

data class AuthenticationRequest(
    var username: String,
    var password: String
)