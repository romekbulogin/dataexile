package ru.dataexile.authorizationservice.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import ru.dataexile.authorizationservice.repository.OwnerRepository

@Configuration
class ApplicationConfiguration(
    private val ownerRepository: OwnerRepository
) {
    @Bean
    fun ownerReactiveUserDetailsService(): ReactiveUserDetailsService? {
        return ReactiveUserDetailsService { username: String? ->
            username?.let { ownerRepository.findByUsername(it).cast(UserDetails::class.java) }
        }
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}