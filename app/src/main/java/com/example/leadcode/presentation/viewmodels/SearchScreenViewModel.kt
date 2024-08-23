package com.example.leadcode.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leadcode.model.Article
import com.example.leadcode.model.CollsAndDocs
import com.example.leadcode.model.CombinedItem
import com.example.leadcode.model.DBAndColl
import com.example.leadcode.model.ListOfDBCollsDocs
import com.example.leadcode.model.QuerySearch
import com.example.leadcode.model.Result
import com.example.leadcode.model.combineAndShuffle
import com.example.leadcode.repository.QuestionsRepository
import com.example.leadcode.utils.Constants.BASE_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(private val repository: QuestionsRepository) : ViewModel() {
    private val _searchState = MutableStateFlow<ListOfDBCollsDocs?>(ListOfDBCollsDocs())
    val searchState = _searchState.asStateFlow()

    private val _inProgress = MutableStateFlow<Boolean>(false)
    val inProgress = _inProgress.asStateFlow()

    private val _urlString = MutableStateFlow<String>("")
    val urlString = _urlString.asStateFlow()

    private val _querySearchState = MutableStateFlow<QuerySearch?>(QuerySearch())
    val querySearchState = _querySearchState.asStateFlow()

    private val _listArticlesState = MutableStateFlow<List<CombinedItem?>>(listOf())
    val listArticlesState = _listArticlesState.asStateFlow()

    private val _filteredListArticlesState = MutableStateFlow<List<CombinedItem?>>(listOf(CombinedItem()))
    val filteredListArticlesState = _filteredListArticlesState.asStateFlow()

    private val _selectedTopics = MutableStateFlow<Set<String>>(emptySet())
    val selectedTopics: StateFlow<Set<String>> = _selectedTopics.asStateFlow()

    private val _originalTopics = MutableStateFlow<Set<String>>(emptySet())
    val originalTopics: StateFlow<Set<String>> = _selectedTopics.asStateFlow()


    init {
        fetchSearch(urlString = BASE_URL + "searchFeed")
    }
    fun fetchSearch(urlString: String){
        _inProgress.value = true


        viewModelScope.launch(Dispatchers.IO) {

            try {
                when (val result = repository.fetchSearch(urlString = urlString)) {
                    is Result.Success -> {
                        // Update UI with the fetched data
                        _searchState.value = result.data
                        Log.d("viewmodelsee", "fetchSearch: ${searchState.value}")
                        _listArticlesState.value = searchState.value?.articleDocs?.let {
                            combineAndShuffle(
                                dbColls = listOf(DBAndColl()),
                                articleDocs = it,
                                tutorialDocs = listOf(
                                    CollsAndDocs()
                                )
                            )
                        }?: listOf()
                        Log.d("all articles", "fetchSearch: ${listArticlesState.value}")

                        // Convert to a Set to eliminate duplicates

                        Log.d("orginal topics", "fetchSearch: ${originalTopics.value}")


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
    fun querySearch(urlString: String){
        _inProgress.value = true


        viewModelScope.launch(Dispatchers.IO) {

            try {
                when (val result = repository.fetchQuerySearch(urlString = urlString)) {
                    is Result.Success -> {
                        // Update UI with the fetched data
                        _querySearchState.value = result.data
                        Log.d("viewmodelsee", "fetchSearch: ${querySearchState.value}")
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
     fun onQuerySearch(query: String){
        viewModelScope.launch {
            _urlString.value = BASE_URL + "querySearch?query=${query}"

            querySearch(urlString.value)
        }
    }

    fun onTopicButtonClick(topic: String){
        viewModelScope.launch {
            val currentTopics = _selectedTopics.value.toMutableSet()
            if (topic in currentTopics) {
                currentTopics.remove(topic)
            } else {
                currentTopics.add(topic)
            }
            _selectedTopics.value = currentTopics

            Log.d("selected topics vm", "ArticleScreen: ${selectedTopics.value}")

        }
    }
    fun onApplyFilterClick(){
        _inProgress.value = true
        viewModelScope.launch {
            _filteredListArticlesState.value = if (_selectedTopics.value.isEmpty()) {
                listArticlesState.value
            } else {
                listArticlesState.value.filter { article ->
                    article?.collection in _selectedTopics.value
                }
            }
            Log.d("filtered list articles vm", "ArticleScreen: ${filteredListArticlesState.value}")

        }
        ProgressIndicator()
    }
    fun onClearAllClick(){
        _inProgress.value = true
        viewModelScope.launch {
            _filteredListArticlesState.value = emptyList()
            _selectedTopics.value = emptySet()
        }
        ProgressIndicator()
    }


}
