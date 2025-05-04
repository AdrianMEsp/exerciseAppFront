package com.example.exerciseapp

data class Exercise(
    val name: String,
    val series: String,
    val repetitionsOrMinutes: Int,
    val weight: Double,
    val description: String
)