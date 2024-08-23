package com.example.leadcode.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leadcode.model.Result
import com.example.leadcode.model.Tutorial
import com.example.leadcode.model.Tutorials
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

@HiltViewModel
class TutorialScreenViewModel @Inject constructor(
    private val repository: QuestionsRepository
) : ViewModel() {
    private val _inProgress = MutableStateFlow<Boolean>(false)
    val inProgress = _inProgress.asStateFlow()

    private val _tutorialClicked = MutableStateFlow<Int>(0)
    val tutorialClicked = _tutorialClicked.asStateFlow()

    private val _tutorialsState = MutableStateFlow<Tutorials?>(Tutorials())
    val tutorialsState = _tutorialsState.asStateFlow()


    init {
    }

    fun fetchTutorials(urlString: String) {
        _inProgress.value = true


        viewModelScope.launch(Dispatchers.IO) {

            try {
                when (val result = repository.fetchTutorials(urlString = urlString)) {
                    is Result.Success -> {
                        // Update UI with the fetched data
                        _tutorialsState.value = result.data
                        Log.d("tutoviewmodelsee", "fetchSearch: ${tutorialsState.value}")

                    }
                    is Result.Error -> {
                        // Handle the error, e.g., show an error message
                        Log.d("Error!!", "fetchTutorials: ${result.exception.message}")
                    }
                    is Result.ApiError -> {
                        Log.d("API Error", "fetchTutorials: ${result.message} with code: ${result.code} ")
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

    fun onBackClick(onBack: () -> Unit){
        viewModelScope.launch {
            onBack.invoke()
        }
    }

    fun onTutorialClick(openScreen: (String) -> Unit, topic: String, title: String){
        viewModelScope.launch {
            _tutorialsState.value = Tutorials()
            openScreen("TutorialLayoutScreen")
            val url = BASE_URL + "tutorials/?topic=${topic}&title=${title}"
            fetchTutorials(urlString = url)
        }
    }
}