package ru.dataexile.authorizationservice.dto.response

data class User(
    var username: String,
    var email: String,
    var role: String
)