package com.example.igorapp1

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class Mensaje : AppCompatActivity() {

    private var email: String? = null
    private var name: String? = null
    private var recep: String? = null

    private lateinit var mensajesInterno: LinearLayout
    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mensaje)

        email = intent.getStringExtra("EMAIL_EXTRA")
        name = intent.getStringExtra("NAME_EXTRA")
        recep = intent.getStringExtra("NAME_RECEP")

        val textViewSaludo = findViewById<TextView>(R.id.receptor)
        textViewSaludo.text = "$recep!"

        val igorButton: ImageView = findViewById(R.id.igor)
        igorButton.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            intent.putExtra("EMAIL_EXTRA", email)
            intent.putExtra("NAME_EXTRA", name)
            intent.putExtra("NAME_RECEP", recep)
            startActivity(intent)
        }

        mensajesInterno = findViewById(R.id.mensajes)
        editText = findViewById(R.id.editTextText)

        editText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                resizeLayout(800)
            } else {
                resizeLayout(1500)
            }
        }

        val imageButton: ImageButton = findViewById(R.id.imageButton)
        imageButton.setOnClickListener {
            val messageText = editText.text.toString().trim()
            if (messageText.isNotEmpty()) {
                addMessageBubble(messageText)
                editText.text.clear()
            }
            editText.clearFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
        }
    }

    private fun addMessageBubble(text: String) {
        val textView = TextView(this)
        textView.text = text
        textView.background = ContextCompat.getDrawable(this, R.drawable.bubble_background)
        textView.setPadding(16, 8, 16, 8)
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.black))
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 8, 0, 8)
        layoutParams.gravity = android.view.Gravity.END
        textView.layoutParams = layoutParams

        mensajesInterno.addView(textView)
    }

    private fun resizeLayout(height: Int) {
        val params = mensajesInterno.layoutParams
        params.height = height
        mensajesInterno.layoutParams = params
    }
}

