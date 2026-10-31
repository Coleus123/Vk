package com.example.vk.domain.repository

import com.example.vk.domain.model.User

interface UserRepository {
    fun getUsers(): List<User>
}
