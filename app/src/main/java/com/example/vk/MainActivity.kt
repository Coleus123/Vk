package com.example.vk

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.vk.presentation.UserViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usersText = findViewById<TextView>(R.id.usersText)
        viewModel.users.observe(this) { users ->
            usersText.text = users.joinToString("\n") { it.name }
        }
        viewModel.loadUsers()
    }
}
