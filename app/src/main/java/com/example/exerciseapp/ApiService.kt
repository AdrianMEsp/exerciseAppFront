package com.example.exerciseapp

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("/api/v1/exercise/add")
    fun addExercise(@Body exercise: Exercise): Call<ResponseBody>

    @GET("api/v1/exercise")
    suspend fun getExercises(): List<Exercise>
}