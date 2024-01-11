package ru.dataexile.authorizationservice.repository

import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import ru.dataexile.authorizationservice.entity.OwnerEntity
import java.util.UUID

@Repository
interface OwnerRepository : R2dbcRepository<OwnerEntity, UUID> {
    fun findByEmail(email: String): Mono<OwnerEntity>
    fun findByUsername(username: String): Mono<OwnerEntity>
}