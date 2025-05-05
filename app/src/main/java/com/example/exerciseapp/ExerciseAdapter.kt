package com.example.exerciseapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(private val exercises: List<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textInfo: TextView = itemView.findViewById(R.id.textExerciseInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.textInfo.text = "Nombre: ${exercise.name}\n" +
                "Series: ${exercise.series}\n" +
                "Tiempo/Reps: ${exercise.repetitionsOrMinutes}\n" +
                "Peso: ${exercise.weight}\n" +
                "Descripción: ${exercise.description}"
    }

    override fun getItemCount(): Int = exercises.size
}
