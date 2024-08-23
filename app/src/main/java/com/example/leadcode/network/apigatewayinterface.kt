package com.example.leadcode.network

import com.example.leadcode.model.Article
import com.example.leadcode.model.Articles
import com.example.leadcode.model.InterviewQuestions
import com.example.leadcode.model.ListOfDBCollsDocs
import com.example.leadcode.model.MCQResponse
import com.example.leadcode.model.QuerySearch
import com.example.leadcode.model.Tutorials
import com.example.leadcode.model.Result
import com.example.leadcode.model.Tutorial

interface ApiGatewayInterface {

        suspend fun fetchQuestions(urlString: String):Result<MCQResponse> // Example function to fetch data
        suspend fun fetchArticles(urlString: String): Result<Articles>
        suspend fun fetchTutorials(urlString: String): Result<Tutorials>
        suspend fun fetchInterviewQuestions(urlString: String): Result<InterviewQuestions>
        suspend fun fetchSearch(urlString: String): Result<ListOfDBCollsDocs>
        suspend fun fetchQuerySearch(urlString: String): Result<QuerySearch>


}