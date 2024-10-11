package mx.cazv.todasbrillamos.model.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.cazv.todasbrillamos.model.API
import mx.cazv.todasbrillamos.model.ApiConfig
import mx.cazv.todasbrillamos.model.apiCall
import mx.cazv.todasbrillamos.model.models.GroupedNotification
import mx.cazv.todasbrillamos.model.models.NotificationGet
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotificationService {
    // Inicializa una instancia de Retrofit con la URL base de la API y un convertidor
    private val retrofitApi by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Crea una instancia del servicio de la API utilizando Retrofit
    private val apiService by lazy {
        retrofitApi.create(API::class.java)
    }

    suspend fun getNotifications(token: String): GroupedNotification {
        return withContext(Dispatchers.IO) {
            try {
                apiCall { apiService.getNotificationsByClientId("Bearer $token") }.getOrNull()!!
            } catch (e: Exception) {
                e.printStackTrace()
                GroupedNotification("", listOf(NotificationGet("", "", "", 10)))
            }
        }
    }
}