package com.github.leondevlifelog.model

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Users : IntIdTable() {
    val username = varchar("username", length = 50)
    val phone = varchar("phone", length = 15).uniqueIndex().nullable()
    val password = varchar("password", length = 512)
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)

    var username by Users.username
    var phone by Users.phone
    var password by Users.password
}