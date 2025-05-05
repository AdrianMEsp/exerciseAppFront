package com.example.exerciseapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(
    val name: String,
    val series: String,
    val repetitionsOrMinutes: Int,
    val weight: Double,
    val description: String
) : Parcelable