package com.example.waterpotabilityapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapayzeka.waterpotability.network.PredictionRequest
import com.yapayzeka.waterpotability.network.PredictionResponse
import com.yapayzeka.waterpotability.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PredictionViewModel : ViewModel() {

    var predictionResult: String = ""

    fun fetchPrediction(features: List<Float>, onResult: (String) -> Unit): String {
        val request = PredictionRequest(features = features)
        viewModelScope.launch(Dispatchers.IO) {
            RetrofitClient.apiService.getPrediction(request).enqueue(object : Callback<PredictionResponse> {
                override fun onResponse(
                    call: Call<PredictionResponse>,
                    response: Response<PredictionResponse>
                ) {
                    if (response.isSuccessful) {
                        predictionResult = if (response.body()?.prediction == 1) {
                            "Su içilebilir."
                        } else {
                            "Su içilemez."
                        }
                        onResult(predictionResult)
                    } else {
                        onResult("Hata: API isteği başarısız.")
                    }
                }

                override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                    onResult("Hata: Sunucu bağlantısı başarısız.")
                }
            })
        }
        return predictionResult
    }
}