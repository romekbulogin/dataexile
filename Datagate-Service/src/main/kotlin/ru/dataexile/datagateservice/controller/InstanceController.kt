package ru.dataexile.datagateservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.dataexile.datagateservice.service.InstanceService
import java.util.UUID

@RestController
@RequestMapping("/api/instance")
class InstanceController(
    private val instanceService: InstanceService
) {

    @GetMapping("/find/by_id")
    fun findById(@RequestParam id: String) =
        instanceService.findById(UUID.fromString(id))

    @GetMapping("/find/all")
    fun findAll() = instanceService.findAll()

    @GetMapping("/find/by_name")
    fun findByName(@RequestParam name: String) =
        instanceService.findByName(name)

    @GetMapping("/find/by_title")
    fun findByTitle(@RequestParam title: String) =
        instanceService.findByTitle(title)

    @GetMapping("/find/all/by_dbms")
    fun findAllByDbms(@RequestParam dbms: String) =
        instanceService.findAllByDbms(dbms)
}