package com.example.exerciseapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var api: ApiService
    private lateinit var textResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los campos del layout
        val editName = findViewById<EditText>(R.id.editNombre)
        val editSeries = findViewById<EditText>(R.id.editSeries)
        val editRepetitionsOrMinutes = findViewById<EditText>(R.id.editTiempoRepets)
        val editWeight = findViewById<EditText>(R.id.editPeso)
        val editDescription = findViewById<EditText>(R.id.editDescripcion)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080") // Apunta a localhost desde el emulador
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiService::class.java)

        val btnGetAll = findViewById<Button>(R.id.btnGetAll)
        val editSearchName = findViewById<EditText>(R.id.editSearchName)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val editDeleteName = findViewById<EditText>(R.id.editDeleteName)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val editUpdateName = findViewById<EditText>(R.id.editUpdateName)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        textResult = findViewById(R.id.textResult)

        // Acción del botón
        btnGuardar.setOnClickListener {
            val exercise = Exercise(
                name = editName.text.toString(),
                series = editSeries.text.toString(),
                repetitionsOrMinutes = editRepetitionsOrMinutes.text.toString().toIntOrNull() ?: 0,
                weight = editWeight.text.toString().toDoubleOrNull() ?: 0.0,
                description = editDescription.text.toString()
            )

            api.addExercise(exercise).enqueue(object : Callback<ResponseBody> {

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@MainActivity, "Ejercicio guardado", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Error en el servidor: ${response.code()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Fallo: ${t.message}", Toast.LENGTH_LONG)
                        .show()
                }
            })


        }

        btnGetAll.setOnClickListener {
            api.getAllExercises().enqueue(object : Callback<List<Exercise>> {

                override fun onResponse(
                    call: Call<List<Exercise>>,
                    response: Response<List<Exercise>>
                ) {
                    if (response.isSuccessful) {
                        val exercises = response.body() ?: emptyList()
                        val intent = Intent(this@MainActivity, ExerciseListActivity::class.java)
                        intent.putParcelableArrayListExtra("exercises", ArrayList(exercises))
                        startActivity(intent)
                    } else {
                        Toast.makeText( this@MainActivity,
                            "Error obtaining exercises: ${response.code()}",
                            Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<List<Exercise>?>, t: Throwable) {
                    Toast.makeText(this@MainActivity,
                        "Failed at obtained exercises : ${t.message}",
                        Toast.LENGTH_LONG)
                        .show()
                }
            })
        }

        btnSearch.setOnClickListener {
            val nameToSearch = editSearchName.text.toString()
            api.getExerciseByName(nameToSearch).enqueue(object : Callback<Exercise> { // Cambiado a Callback<Exercise>
                override fun onResponse(call: Call<Exercise>, response: Response<Exercise>) {
                    if (response.isSuccessful) {
                        val exercise = response.body() // Obtenemos un solo Exercise, no una lista
                        if (exercise != null) {
                            Toast.makeText(this@MainActivity,
                                "Exercise found:\n$exercise",
                                Toast.LENGTH_LONG)
                                .show()
                        } else {
                            Toast.makeText(this@MainActivity,
                                "Exercise: $nameToSearch not found",
                                Toast.LENGTH_LONG)
                                .show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity,
                            "Error at searching: ${response.code()}",
                            Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Exercise>, t: Throwable) {
                    Toast.makeText(this@MainActivity,
                        "Failed at find: ${t.message}",
                        Toast.LENGTH_LONG)
                        .show()
                }
            })
        }

        btnDelete.setOnClickListener {
            val nameToDelete = editDeleteName.text.toString()
            api.deleteExerciseByName(nameToDelete).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MainActivity,
                            "exercise deleted successfully",
                            Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(this@MainActivity,
                            "Error at delete: ${response.code()}",
                            Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MainActivity,
                        "Failed at deleted: ${t.message}",
                        Toast.LENGTH_LONG)
                        .show()
                }
            })
        }

        btnUpdate.setOnClickListener {
            val nameToUpdate = editUpdateName.text.toString()
            val updatedExercise = Exercise(
                name = editName.text.toString(),
                series = editSeries.text.toString(),
                repetitionsOrMinutes = editRepetitionsOrMinutes.text.toString().toIntOrNull() ?: 0,
                weight = editWeight.text.toString().toDoubleOrNull() ?: 0.0,
                description = editDescription.text.toString()
            )

            api.updateExerciseByName(nameToUpdate, updatedExercise)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@MainActivity,
                                "Exercise updated successfully",
                                Toast.LENGTH_LONG)
                                .show()
                        } else {
                            Toast.makeText(this@MainActivity,
                                "Error at update: ${response.code()}",
                                Toast.LENGTH_LONG)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(this@MainActivity,
                            "Failed at update: ${t.message}",
                            Toast.LENGTH_LONG)
                            .show()
                    }
                })
        }
    }
}