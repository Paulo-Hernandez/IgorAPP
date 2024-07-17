package com.example.igorapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val email = intent.getStringExtra("EMAIL_EXTRA")
        val name = intent.getStringExtra("NAME_EXTRA") ?: "Usuario"
        val apode = intent.getStringExtra("APODE_EXTRA")

        val textViewSal = findViewById<TextView>(R.id.saludo)
        textViewSal.text = "¡Hola $name!"

        val emotionButton: ImageButton = findViewById(R.id.emotion)
        emotionButton.setOnClickListener {
            val intent = Intent(this, Emotion::class.java)
            intent.putExtra("EMAIL_EXTRA", email)
            intent.putExtra("NAME_EXTRA", name)
            intent.putExtra("APODE_EXTRA", apode)
            startActivity(intent)
        }

        val perfilButton: ImageButton = findViewById(R.id.setting)
        perfilButton.setOnClickListener {
            val intent = Intent(this, iconos::class.java)
            intent.putExtra("EMAIL_EXTRA", email)
            startActivity(intent)
        }
    }
}