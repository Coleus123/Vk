package com.example.vk.data.repository

import com.example.vk.data.mapper.toDomain
import com.example.vk.data.source.UserDataSource
import com.example.vk.domain.model.User
import com.example.vk.domain.repository.UserRepository

class UserRepositoryImpl(
    private val dataSource: UserDataSource
) : UserRepository {
    override fun getUsers(): List<User> =
        dataSource.getUsers().map { it.toDomain() }
}
