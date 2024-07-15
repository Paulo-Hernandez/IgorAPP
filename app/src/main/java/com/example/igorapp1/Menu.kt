package com.example.igorapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val emotionButton: ImageButton = findViewById(R.id.emotion)
        emotionButton.setOnClickListener {
            val intent = Intent(this, Emotion::class.java)
            startActivity(intent)
        }
    }
}