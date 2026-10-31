package com.example.vk.data.mapper

import com.example.vk.data.dto.UserDto
import com.example.vk.domain.model.User

fun UserDto.toDomain() = User(id = id, name = name)
