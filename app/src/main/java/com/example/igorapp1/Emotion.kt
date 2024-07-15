package com.example.igorapp1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class Emotion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotion)
    }

    fun mostrarMensaje(view: View) {
        val tag = view.tag as String // Obtener el tag como String

        // Convertir el tag a Integer si es necesario
        val emocionIndex = tag.toIntOrNull() ?: -1 // Convertir a Integer o asignar -1 si falla

        // Verificar el índice obtenido
        when (emocionIndex) {
            0 -> mostrarConfirmacion("Feliz")
            1 -> mostrarConfirmacion("Normal")
            2 -> mostrarConfirmacion("Frustrado")
            3 -> mostrarConfirmacion("Triste")
            4 -> mostrarConfirmacion("Cansado")
            else -> {
                // Acción por defecto en caso de valor no reconocido
                Toast.makeText(this, "Emoción no reconocida", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarConfirmacion(emocion: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("¿Estás seguro de anotar que estás $emocion?")
            .setPositiveButton("Sí") { dialog, id ->
                // Acción a realizar si el usuario dice "Sí"
                Toast.makeText(this, "Confirmado: Estás $emocion", Toast.LENGTH_SHORT).show()
                // Aquí puedes guardar la emoción en un arreglo o hacer cualquier otra acción
            }
            .setNegativeButton("Cancelar") { dialog, id ->
                // Acción a realizar si el usuario dice "Cancelar"
                dialog.dismiss()  // Cerrar el diálogo sin hacer nada
            }
        builder.create().show()
    }
}