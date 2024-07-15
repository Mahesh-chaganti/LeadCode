package com.example.leadcode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import com.amazonaws.mobileconnectors.apigateway.ApiRequest
import com.amazonaws.mobileconnectors.apigateway.ApiResponse
import com.example.clientsdk.MyQuestionsAPIClient
import com.example.clientsdk.model.Empty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyQuestionsViewModel @Inject constructor() : ViewModel() {
    // ...
    // In a ViewModel
    fun fetchQuestions(): String {
        var questions: Empty = Empty()
        var responseBody: String? = "empty"
        viewModelScope.launch {
            withContext(Dispatchers.IO) { // Switch to IO dispatcher for network call
                val apiClient = ApiClientFactory().build(MyQuestionsAPIClient::class.java)
                val request = ApiRequest()
                request.withPath("/questions")
                val apiResponse: ApiResponse = apiClient.execute(request)

                val responseCode: Int = apiResponse.statusCode
                Log.d("Quest", "fetchQuestions:$responseCode ")


                try {
//                    responseBody = apiResponse.getRawContent().toString()
                    questions = apiClient.questionsGet()

                } catch (e: Exception) {
                    Log.d("ERROR ", " failed reading response ")
                }
                Log.d("Quest", "fetchQuestions:${questions.toString()} ")


                // ...
            }
        }
        return questions.toString()
    }
}