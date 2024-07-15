package com.example.leadcode

import android.icu.util.Output
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import com.example.clientsdk.MyQuestionsAPIClient
import com.example.clientsdk.model.Empty
import com.google.gson.JsonNull
import javax.inject.Inject


class MyQuestionsRepository() {
    // ...
    suspend fun getQuestions(): Empty { // Assuming Output is the return type of questionsGet()
        val apiClient = ApiClientFactory()
        return apiClient.build(MyQuestionsAPIClient::class.java).questionsGet()
    }
}