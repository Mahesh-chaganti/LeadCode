package com.example.leadcode.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leadcode.model.InterviewQuestions
import com.example.leadcode.model.Result
import com.example.leadcode.repository.QuestionsRepository
import com.example.leadcode.utils.Constants.BASE_URL
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class InterviewQuestionsViewModel @Inject constructor(private val repository: QuestionsRepository) : ViewModel() {
    private val _inProgress = MutableStateFlow<Boolean>(false)
    val inProgress = _inProgress.asStateFlow()

    private val _interviewPrepsState = MutableStateFlow<InterviewQuestions?>(InterviewQuestions())
    val interviewPrepsState = _interviewPrepsState.asStateFlow()

    private val _topicClicked = MutableStateFlow<String>("")
    val topicClicked = _topicClicked.asStateFlow()

    init {
//        fetchInterviewQuestions()
    }

    fun fetchInterviewQuestions(urlString: String) {
        _inProgress.value = true


        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val result = repository.fetchInterviewQuestions(urlString = urlString)) {
                    is Result.Success -> {
                        // Update UI with the fetched data
                        _interviewPrepsState.value = result.data
                        Log.d("viewmodelinterviewsee", "fetchInterviewQuestions: ${interviewPrepsState.value}")
                    }
                    is Result.Error -> {
                        // Handle the error, e.g., show an error message
                        Log.d("Error!!", "fetchQuestions: ${result.exception.message}")
                    }
                    is Result.ApiError -> {
                        Log.d("API Error", "fetchQuestions: ${result.message} with code: ${result.code} ")
                    }
                }





                ProgressIndicator()
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }

    private fun ProgressIndicator() {
        viewModelScope.launch {

            _inProgress.value = false


        }
    }

    fun onInterviewTopicClick(openScreen: (String) -> Unit,topic: String){
        viewModelScope.launch {
            openScreen("InterviewLayoutScreen")
            val url = BASE_URL + "interviewPrep?topic=${topic}"
            fetchInterviewQuestions(url)
            Log.d("viewmodel", "progressIndicator: ${interviewPrepsState.value?.interviewQuestions}")
            _topicClicked.value = topic

        }
    }


    fun onBackClick(onBack: () -> Unit){
        viewModelScope.launch {
            onBack.invoke()
        }
    }
}