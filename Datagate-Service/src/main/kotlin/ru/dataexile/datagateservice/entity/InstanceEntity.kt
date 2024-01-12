package ru.dataexile.datagateservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("instance")
data class InstanceEntity(
    @Id
    var id: UUID? = null,
    var url: String? = null,
    var username: String? = null,
    var password: String? = null,
    var title: String? = null,
    var name: String? = null,
    var dbms: String? = null
)