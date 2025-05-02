package com.example.exerciseapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var api: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los campos del layout
        val editNombre = findViewById<EditText>(R.id.editNombre)
        val editSeries = findViewById<EditText>(R.id.editSeries)
//        val editReps = findViewById<EditText>(R.id.editReps)
        val editTiempoRepets = findViewById<EditText>(R.id.editTiempoRepets)
        val editPeso = findViewById<EditText>(R.id.editPeso)
        val editDescripcion = findViewById<EditText>(R.id.editDescripcion)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080") // Apunta a localhost desde el emulador
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiService::class.java)

        // Acción del botón
        btnGuardar.setOnClickListener {
            val ejercicio = Exercise(
                nombre = editNombre.text.toString(),
                series = editSeries.text.toString().toIntOrNull() ?: 0,
                tiempoORepets = editTiempoRepets.text.toString().toIntOrNull() ?: 0,
                peso = editPeso.text.toString().toDoubleOrNull() ?: 0.0,
//                tiempo = editTiempo.text.toString().toIntOrNull() ?: 0,
                descripcion = editDescripcion.text.toString()
            )

            api.addExercise(ejercicio).enqueue(object : Callback<ResponseBody> {

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@MainActivity, "Ejercicio guardado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            this@MainActivity, "Error en el servidor: ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Fallo: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
