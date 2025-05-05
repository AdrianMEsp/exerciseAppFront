package com.example.exerciseapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ExerciseListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var api: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_list)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val btnBack = findViewById<Button>(R.id.btnBack)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val exercises = intent.getParcelableArrayListExtra<Exercise>("exercises") ?: emptyList()

        recyclerView.adapter = ExerciseAdapter(exercises)

        btnBack.setOnClickListener {
            finish() // Regresa a MainActivity
        }
    }

}
