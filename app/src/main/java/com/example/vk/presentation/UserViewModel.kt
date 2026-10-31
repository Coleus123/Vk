package com.example.vk.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vk.data.repository.UserRepositoryImpl
import com.example.vk.data.source.UserDataSource
import com.example.vk.domain.model.User
import com.example.vk.domain.repository.UserRepository

class UserViewModel(
    private val repository: UserRepository = UserRepositoryImpl(UserDataSource())
) : ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    fun loadUsers() {
        _users.value = repository.getUsers()
    }
}
