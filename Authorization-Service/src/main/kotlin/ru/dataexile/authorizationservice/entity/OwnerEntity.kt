package ru.dataexile.authorizationservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.sql.Timestamp
import java.util.UUID

@Table("owner")
class OwnerEntity(
    @Id
    private var id: UUID,
    private var username: String,
    private var password: String,
    private var email: String,
    private var registrationDate: Timestamp,
    private var role: Role,
) : UserDetails {
    fun getEmail() = email
    fun getId() = id
    fun getRegistrationDate() = registrationDate
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority(role.name))

    override fun getPassword() = password

    override fun getUsername() = username

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}