package ru.dataexile.datagateservice.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.dataexile.datagateservice.service.ExileService

@RestController
@RequestMapping("/api/exile")
class ExileController(
    private val exileService: ExileService
) {
    @PostMapping("/start")
    fun exile() = exileService.exile()
}