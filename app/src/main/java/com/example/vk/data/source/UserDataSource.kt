package com.example.vk.data.source

import com.example.vk.data.dto.UserDto

class UserDataSource {
    fun getUsers() = listOf(
        UserDto(1, "Алексей"),
        UserDto(2, "Мария"),
        UserDto(3, "Иван")
    )
}
