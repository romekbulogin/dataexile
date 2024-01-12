package ru.dataexile.datagateservice.repository

import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.dataexile.datagateservice.entity.InstanceEntity
import java.util.UUID

@Repository
interface InstanceRepository : R2dbcRepository<InstanceEntity, UUID> {
    fun findByTitle(title: String): Mono<InstanceEntity>
    fun findByName(name: String): Mono<InstanceEntity>
    fun findAllByDbms(dbms: String): Flux<InstanceEntity>
}