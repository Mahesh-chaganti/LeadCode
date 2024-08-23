package com.example.leadcode

import android.icu.util.Output
//import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
//import com.example.clientsdk.MyQuestionsAPIClient
//import com.example.clientsdk.model.Empty
import com.google.gson.JsonNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject


class MyQuestionsRepository() {
    // ...
//
//    val client = OkHttpClient()
//    suspend fun makeNetworkRequest(url: String): String? {
//        val request = Request.Builder()
//            .url(url)
//            .build()
//        return withContext(Dispatchers.IO) { // Use IO dispatcher for network operations
//            try {
//                client.newCall(request).executeAsync().await().use { response ->
//                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
//                    response.body?.string() // Read response body as string
//                }
//            } catch (e: IOException) {
//                // Handle network errors
//                e.printStackTrace()
//                null
//            }
//        }
//    }
//    suspend fun getQuestions(): Empty { // Assuming Output is the return type of questionsGet()
//        val apiClient = ApiClientFactory()
//        return apiClient.build(MyQuestionsAPIClient::class.java).questionsGet()
//    }
}