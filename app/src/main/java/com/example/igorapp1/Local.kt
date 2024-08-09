package com.example.igorapp1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class Local : AppCompatActivity() {

    // Declarar variables para email, name y apode
    private var email: String? = null
    private var name: String? = null
    private var apode: String? = null
    private var recep: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local)

        email = intent.getStringExtra("EMAIL_EXTRA")
        name = intent.getStringExtra("NAME_EXTRA") ?: "Usuario"
        apode = intent.getStringExtra("APODE_EXTRA")
        recep = intent.getStringExtra("NAME_RECEP")

        val textViewSaludo = findViewById<TextView>(R.id.salEmotion)
        textViewSaludo.text = "¡Hola $name!"

        val igorButton: ImageView = findViewById(R.id.igor)
        igorButton.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            intent.putExtra("EMAIL_EXTRA", email)
            intent.putExtra("NAME_EXTRA", name)
            intent.putExtra("APODE_EXTRA", apode)
            intent.putExtra("NAME_RECEP", recep)
            startActivity(intent)
        }
    }
}