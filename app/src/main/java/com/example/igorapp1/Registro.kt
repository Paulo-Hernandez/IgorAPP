package com.example.igorapp1

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Registro : AppCompatActivity() {

    // Declarar las variables como variables de clase
    private lateinit var registroButton: Button
    private lateinit var usernameEditText: EditText
    private lateinit var fnacimientoEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var password2EditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Inicializar las variables
        registroButton = findViewById(R.id.button_accepted)
        usernameEditText = findViewById(R.id.edit_text_username)
        fnacimientoEditText = findViewById(R.id.edit_text_fnacimiento)
        emailEditText = findViewById(R.id.edit_text_mail)
        passwordEditText = findViewById(R.id.edit_text_password)
        password2EditText = findViewById(R.id.edit_text_password2)

        val volverLogin: TextView = findViewById(R.id.volverLogin)
        volverLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        setup()
    }

    private fun setup() {
        registroButton.setOnClickListener {
            if (areFieldsEmpty()) {
                Toast.makeText(this, "Faltan campos por llenar", Toast.LENGTH_SHORT).show()
            } else {
                // Aquí puedes añadir la lógica para cuando todos los campos están llenos
                // Por ejemplo, iniciar otra actividad o registrar al usuario
            }
        }
    }

    private fun areFieldsEmpty(): Boolean {
        return usernameEditText.text.isEmpty() ||
                fnacimientoEditText.text.isEmpty() ||
                emailEditText.text.isEmpty() ||
                passwordEditText.text.isEmpty() ||
                password2EditText.text.isEmpty()
    }
}
