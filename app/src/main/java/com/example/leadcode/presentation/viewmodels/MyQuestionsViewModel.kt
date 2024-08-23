package com.example.leadcode.presentation.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.leadcode.model.MCQResponse
import com.example.leadcode.model.listOfTopics
import com.example.leadcode.repository.QuestionsRepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.IOException
import javax.inject.Inject
import com.example.leadcode.model.Result
import com.example.leadcode.utils.Constants.BASE_URL


@HiltViewModel
class MyQuestionsViewModel @Inject constructor(private val repository: QuestionsRepository) : ViewModel() {


    private val _paletteCacheState = MutableStateFlow<SnapshotStateMap<String, Pair<Color,Color>>>(SnapshotStateMap())
    val paletteCacheState = _paletteCacheState.asStateFlow()

    private val _questionsLimit = MutableStateFlow<Int>(0)
    val questionsLimit = _questionsLimit.asStateFlow()

    private val _inProgress = MutableStateFlow<Boolean>(false)
    val inProgress = _inProgress.asStateFlow()

    private lateinit var context: Context

    fun setContext(context: Context) {
        this.context = context
    }

    // ...
    // In a ViewModel

    private val _questionsResponseState = MutableStateFlow<MCQResponse>(MCQResponse())
    val questionsResponseState = _questionsResponseState.asStateFlow()


    private val _urlString = MutableStateFlow<String>("")
    val urlString = _urlString.asStateFlow()


    init {
//        fetchQuestions()
    }

    fun fetchQuestions(urlString: String) {

        _inProgress.value = true


        viewModelScope.launch() {





            withContext(Dispatchers.IO) {
                try {
                    when (val result = repository.fetchQuestions(urlString = urlString)) {
                        is Result.Success -> {
                            // Update UI with the fetched data
                            _questionsResponseState.value = result.data

                            }
                        is Result.Error -> {
                            // Handle the error, e.g., show an error message
                            Log.d("Error!!", "fetchQuestions: ${result.exception.message}")
                        }
                        is Result.ApiError -> {
                            Log.d("API Error", "fetchQuestions: ${result.message} with code: ${result.code} ")
                        }
                    }





                    progressIndicator()
                } catch (e: IOException) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }



    fun onQuizTopicClick(openScreen: (String) -> Unit, topic: String) {
        viewModelScope.launch {
            _questionsResponseState.value = MCQResponse()
            openScreen("QuizSubTopicsScreen")
            _urlString.value = BASE_URL + "questions?topic=${topic}"
            fetchQuestions(urlString.value)


        }
    }

    fun onQuizSubTopicClick(openScreen: (String) -> Unit, topic: String, subTopic: String, limit: Int) {
        viewModelScope.launch {
            _questionsResponseState.value = MCQResponse()
            openScreen("QuizLayoutScreen")
            _urlString.value = BASE_URL +"questions?topic=${topic}&subtopic=${subTopic}&limit=${limit}"
            _questionsLimit.value = limit
            fetchQuestions(urlString.value)

        }
    }

    fun onBackClick(onBack: () -> Unit) {
        viewModelScope.launch {
            onBack.invoke()
        }
    }

    private fun progressIndicator() {
        Log.d("viewmodel", "progressIndicator: ${inProgress.value}")

        _inProgress.value = false

    }

    fun generatePalette() {

        _inProgress.value = true
        viewModelScope.launch(Dispatchers.IO) {


            listOfTopics.forEachIndexed { index, topicsAndIcons ->

                try {
                    if (::context.isInitialized) {
                        // Fetch the image using Coil
                        val loader = ImageLoader(context)
                        val request = ImageRequest.Builder(context)
                            .data(topicsAndIcons.Icon)
                            .allowHardware(false) // Important for Palette generation
                            .build()
                        val result = loader.execute(request).drawable
                        val bitmap = result?.toBitmap()// Convert Drawable to Bitmap
                        if (bitmap != null) {
                            Palette.from(bitmap).generate { palette ->
                                if (palette != null) {
                                    paletteCacheState.value[topicsAndIcons.Icon] =
                                        Pair(Color(palette.getVibrantColor(Color(0xFFFF3D00).toArgb())),
                                            Color(palette.getMutedColor(Color.White.toArgb())))



                                }

                            }
                        }

                    }
                } catch (e: Exception) {
                    // Handle exceptions (e.g., network errors)
                    null
                    Log.d("colors game", "generatePalette: $e")

                }


            }
            progressIndicator()

        }
    }


}




