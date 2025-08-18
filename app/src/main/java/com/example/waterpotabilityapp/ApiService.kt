package com.yapayzeka.waterpotability.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class PredictionRequest(val features: List<Float>)
data class PredictionResponse(val prediction: Int)

interface ApiService {
    @POST("/predict")
    fun getPrediction(@Body request: PredictionRequest): Call<PredictionResponse>
}