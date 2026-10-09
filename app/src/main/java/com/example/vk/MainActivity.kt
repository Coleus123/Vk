package com.example.vk

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etInput: EditText
    private lateinit var btnOpenSecond: Button
    private lateinit var btnCall: Button
    private lateinit var btnShare: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        etInput = findViewById(R.id.etInput)
        btnOpenSecond = findViewById(R.id.btnOpenSecond)
        btnCall = findViewById(R.id.btnCall)
        btnShare = findViewById(R.id.btnShare)
        btnOpenSecond.setOnClickListener {
            val text = etInput.text.toString().trim()
            if (text.isEmpty()) {
                Toast.makeText(this, "Введите текст", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra(SecondActivity.EXTRA_TEXT, text)
            }
            startActivity(intent)
        }
        btnCall.setOnClickListener {
            val phone = etInput.text.toString().trim()
            if (!isValidPhone(phone)) {
                Toast.makeText(this, "Введите корректный номер телефона", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            if (dialIntent.resolveActivity(packageManager) != null) {
                startActivity(dialIntent)
            } else {
                Toast.makeText(this, "Нет приложения для звонков", Toast.LENGTH_SHORT).show()
            }
        }
        btnShare.setOnClickListener {
            val text = etInput.text.toString().trim()
            if (text.isEmpty()) {
                Toast.makeText(this, "Введите текст для отправки", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            startActivity(Intent.createChooser(shareIntent, "Поделиться через…"))
        }
    }

    private fun isValidPhone(phone: String): Boolean {
        if (phone.length < 5) return false
        val regex = Regex("^[+]?[0-9\\s\\-()]{5,20}$")
        return regex.matches(phone)
    }
}