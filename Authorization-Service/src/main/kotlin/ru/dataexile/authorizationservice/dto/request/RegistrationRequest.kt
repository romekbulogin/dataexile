package ru.dataexile.authorizationservice.dto.request

data class RegistrationRequest(
    var username: String,
    var password: String,
    var confirmPassword: String,
    var email: String
)
