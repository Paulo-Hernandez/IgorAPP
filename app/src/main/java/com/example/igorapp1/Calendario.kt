package com.example.igorapp1

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Calendario : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private var email: String? = null
    private var name: String? = null
    private var apode: String? = null
    private var recep: String? = null

    private lateinit var EditTextDia: EditText
    private lateinit var resultsTextView: TextView
    private lateinit var imageViewToggle: ImageView

    private var currentImageIndex = 0
    private val images = listOf(
        R.drawable.image1, // Reemplaza con tus imágenes
        R.drawable.image2,
        R.drawable.image_3
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)

        email = intent.getStringExtra("EMAIL_EXTRA")
        name = intent.getStringExtra("NAME_EXTRA") ?: "Usuario"
        apode = intent.getStringExtra("APODE_EXTRA")
        recep = intent.getStringExtra("NAME_RECEP")

        EditTextDia = findViewById(R.id.editTextDia)
        resultsTextView = findViewById(R.id.resultsTextView)
        imageViewToggle = findViewById(R.id.imageView2)

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

        val calButton: Button = findViewById(R.id.calcu)
        calButton.setOnClickListener {
            // Cambiar la imagen a la siguiente en la secuencia
            if (currentImageIndex == images.size - 1) {
                currentImageIndex = 0
            } else {
                currentImageIndex++
            }
            imageViewToggle.setImageResource(images[currentImageIndex])

            if (calButton.text == "Limpiar") {
                // Cambiar a "Calcular" y color morado
                imageViewToggle.visibility = ImageView.INVISIBLE
                calButton.text = "Calcular"
                calButton.setBackgroundColor(Color.parseColor("#9C27B0")) // Morado
            } else {
                // Cambiar a "Limpiar" y color verde
                imageViewToggle.visibility = ImageView.VISIBLE
                calButton.text = "Limpiar"
                calButton.setBackgroundColor(Color.parseColor("#4CAF50")) // Verde
            }
        }

        // Mostrar la primera imagen al inicio
        imageViewToggle.setImageResource(images[currentImageIndex])
        setupDatePicker()
    }

    private fun setupDatePicker() {
        EditTextDia.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                EditTextDia.setText(dateFormat.format(selectedDate.time))
            }, year, month, day)

            datePickerDialog.show()
        }
    }

    private fun fetchEmotionsForDate(date: String) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val selectedDate = dateFormat.parse(date) ?: return

        val calendar = Calendar.getInstance()
        calendar.time = selectedDate
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.time

        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val endOfDay = calendar.time

        db.collection("usuarios").document(email ?: return)
            .collection("emociones")
            .whereGreaterThanOrEqualTo("timestamp", startOfDay)
            .whereLessThan("timestamp", endOfDay)
            .get()
            .addOnSuccessListener { result ->
                processEmotionsData(result)
            }
            .addOnFailureListener { e ->
                resultsTextView.text = "Error al recuperar emociones: ${e.message}"
            }
    }

    private fun processEmotionsData(result: QuerySnapshot) {
        // Inicializar un mapa con todas las emociones posibles
        val emotionCount = mutableMapOf(
            "Feliz" to 0,
            "Normal" to 0,
            "Frustrado" to 0,
            "Triste" to 0,
            "Cansado" to 0
        )

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        // Procesar los documentos obtenidos de Firestore
        result.documents.forEach { document ->
            val emotion = document.getString("emocion") ?: "Desconocida"
            val timestamp = document.getTimestamp("timestamp")?.toDate() ?: Date()

            // Restar 4 horas
            val calendar = Calendar.getInstance().apply {
                time = timestamp
                add(Calendar.HOUR_OF_DAY, -4)
            }
            val adjustedDate = calendar.time

            val dateString = dateFormat.format(adjustedDate)
            val timeString = timeFormat.format(adjustedDate)

            // Incrementar el conteo para la emoción correspondiente
            if (emotion in emotionCount) {
                emotionCount[emotion] = emotionCount[emotion]!! + 1
            }
        }

        // Construir el mensaje para mostrar en el TextView
        val sb = StringBuilder()
        sb.append("Emociones para el día seleccionado:\n")
        emotionCount.forEach { (emotion, count) ->
            sb.append("$emotion: $count\n")
        }

        // Mostrar el mensaje en el TextView
        resultsTextView.text = sb.toString()
    }
}



