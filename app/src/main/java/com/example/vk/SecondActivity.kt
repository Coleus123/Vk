package com.example.vk

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val textView = findViewById<TextView>(R.id.tvReceivedText)
        val receivedText = intent.getStringExtra(EXTRA_TEXT)
        textView.text = receivedText ?: "Текст не получен"

        // Кнопка "Назад"
        findViewById<android.widget.Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }

    companion object {
        const val EXTRA_TEXT = "extra_text"
    }
}