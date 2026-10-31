package com.example.vk

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class User(val id: Int, val name: String)

interface UserRepository {
    suspend fun getUsers(): List<User>
}

class GetUsersUseCase(private val repo: UserRepository) {
    suspend operator fun invoke() = repo.getUsers()
}

data class UserDto(val id: Int, val first_name: String)

fun UserDto.toDomain() = User(id, first_name)

class UserRepositoryImpl : UserRepository {
    override suspend fun getUsers(): List<User> {
        val mockData = listOf(UserDto(1, "Ivan"), UserDto(2, "Petr"))
        return mockData.map { it.toDomain() }
    }
}

sealed interface UiState {
    data class Success(val users: List<User>) : UiState
}

class MainViewModel(useCase: GetUsersUseCase) : ViewModel() {
    private val _state = MutableStateFlow<UiState?>(null)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = UiState.Success(useCase())
        }
    }
}

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(GetUsersUseCase(UserRepositoryImpl())) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tv = findViewById<TextView>(R.id.textView)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    if (state is UiState.Success) {
                        tv.text = state.users.joinToString("\n") { it.name }
                    }
                }
            }
        }
    }
}