package ru.dataexile.datagateservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DatagateServiceApplication

fun main(args: Array<String>) {
    runApplication<DatagateServiceApplication>(*args)
}
