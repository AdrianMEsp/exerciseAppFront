package com.example.exerciseapp

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("/api/v1/exercise/add")
    fun addExercise(@Body exercise: Exercise): Call<ResponseBody>

    @GET("api/v1/exercise")
    fun getExercises(): Call<List<Exercise>>
    
    @GET("api/v1/exercise/{name}")
    fun getExerciseByName(@Path("name") name: String): Call<Exercise>

    @DELETE("/api/v1/exercise/{name}")
    fun deleteExerciseByName(@Path("name") name: String): Call<ResponseBody>

    @PUT("/api/v1/exercise/{name}")
    fun updateExerciseByName(
        @Path("name") name: String,
        @Body exercise: Exercise): Call<ResponseBody>
}