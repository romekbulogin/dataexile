package ru.dataexile.datagateservice.service

import mu.KotlinLogging
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.dataexile.datagateservice.entity.InstanceEntity
import ru.dataexile.datagateservice.repository.InstanceRepository
import java.util.UUID

@Service
class InstanceService(
    private val instanceRepository: InstanceRepository
) {
    private val logger = KotlinLogging.logger { }

    fun findById(id: UUID): Mono<ResponseEntity<Any>> {
        logger.info("[FIND BY ID] id=$id")
        return instanceRepository.findById(id).map { instance ->
            require(instance != null) {
                throw NotFoundException()
            }
            ResponseEntity.ok(instance)
        }
    }

    fun findAll(): Flux<InstanceEntity> {
        logger.info("[FIND ALL]")
        return instanceRepository.findAll()
    }

    fun findByName(name: String): Mono<ResponseEntity<Any>> {
        logger.info("[FIND BY NAME] name=$name")
        return instanceRepository.findByName(name).map { instance ->
            require(instance != null) {
                throw NotFoundException()
            }
            ResponseEntity.ok(instance)
        }
    }

    fun findByTitle(title: String): Mono<ResponseEntity<Any>> {
        logger.info("[FIND BY TITLE] title=$title")
        return instanceRepository.findByTitle(title).map { instance ->
            require(instance != null) {
                throw NotFoundException()
            }
            ResponseEntity.ok(instance)
        }
    }

    fun findAllByDbms(dbms: String): Flux<ResponseEntity<Any>> {
        logger.info("[FIND ALL BY DBMS] dbms=$dbms")
        return instanceRepository.findAllByDbms(dbms).map { instance ->
            require(instance != null) {
                throw NotFoundException()
            }
            ResponseEntity.ok(instance)
        }
    }
}