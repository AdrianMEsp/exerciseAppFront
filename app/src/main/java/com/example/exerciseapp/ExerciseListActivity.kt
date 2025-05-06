package com.example.exerciseapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ExerciseListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExerciseAdapter
    private lateinit var originalExercises: List<Exercise>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_list)

        recyclerView = findViewById(R.id.recyclerView)
        val searchEditText = findViewById<EditText>(R.id.searchEditText)
//        val btnSearchName = findViewById<Button>(R.id.btnSearchName)
        val btnBack = findViewById<Button>(R.id.btnBack)

        recyclerView.layoutManager = LinearLayoutManager(this)

        originalExercises = intent.getParcelableArrayListExtra("exercises") ?: emptyList()
        adapter = ExerciseAdapter(originalExercises)
        recyclerView.adapter = adapter

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                val filtered = if (query.isEmpty()) {
                    originalExercises
                } else {
                    originalExercises.filter {
                        it.name.contains(query, ignoreCase = true)
                    }
                }
                adapter.updateData(filtered)
            }
        })

        btnBack.setOnClickListener {
            finish()
        }
    }
}


