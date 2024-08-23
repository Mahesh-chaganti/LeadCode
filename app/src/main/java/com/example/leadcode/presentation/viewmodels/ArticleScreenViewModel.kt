package com.example.leadcode.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leadcode.model.Article
import com.example.leadcode.model.Articles
import com.example.leadcode.model.CombinedItem
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
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ArticleScreenViewModel @Inject constructor(
    private val repository: QuestionsRepository
) : ViewModel() {

    private val _inProgress = MutableStateFlow<Boolean>(false)
    val inProgress = _inProgress.asStateFlow()

    private val _articlesState = MutableStateFlow<Articles?>(Articles())
    val articlesState = _articlesState.asStateFlow()




    val filteringList = mutableListOf<String>()


    init {
    }

    fun fetchArticles(urlString: String) {

        _inProgress.value = true


        viewModelScope.launch(Dispatchers.IO) {


            withContext(Dispatchers.IO) {
                try {
                    when (val result = repository.fetchArticles(urlString = urlString)) {
                        is Result.Success -> {
                            // Update UI with the fetched data
                            _articlesState.value = result.data
                            Log.d("artiviewmodelsee", "fetchSearch: ${articlesState.value}")

                        }
                        is Result.Error -> {
                            // Handle the error, e.g., show an error message
                            Log.d("Error!!", "fetchArticles: ${result.exception.message}")
                        }
                        is Result.ApiError -> {
                            Log.d("API Error", "fetchArticles: ${result.message} with code: ${result.code} ")
                        }
                    }





                    ProgressIndicator()
                } catch (e: IOException) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }


    private fun ProgressIndicator() {
        viewModelScope.launch {

            _inProgress.value = false


        }
    }

    fun onArticleClick(openScreen: (String) -> Unit, topic: String, title: String){
        viewModelScope.launch {
            _articlesState.value = Articles()
            openScreen("ArticleLayoutScreen")
            val url = BASE_URL + "articles/?topic=${topic}&title=${title}"
            fetchArticles(urlString = url)
        }
    }

    fun onBackClick(onBack: () -> Unit){
        viewModelScope.launch {
            onBack.invoke()
        }
    }




}