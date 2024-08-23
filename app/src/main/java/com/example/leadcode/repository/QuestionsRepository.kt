package com.example.leadcode.repository

import android.util.Log
import com.example.leadcode.model.Articles
import com.example.leadcode.model.InterviewQuestions
import com.example.leadcode.model.ListOfDBCollsDocs
import com.example.leadcode.model.MCQResponse
import com.example.leadcode.model.QuerySearch
import com.example.leadcode.model.Tutorials
import com.example.leadcode.network.ApiGatewayInterface
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import com.example.leadcode.model.Result
import com.example.leadcode.utils.Constants.API_KEY_NAME
import com.example.leadcode.utils.Constants.API_KEY_VALUE
import com.example.leadcode.utils.Constants.AUTH_TOKEN_NAME
import com.example.leadcode.utils.Constants.AUTH_TOKEN_VALUE
import okhttp3.Response

class QuestionsRepository @Inject constructor(private val client: OkHttpClient): ApiGatewayInterface {
    override suspend fun fetchQuestions(urlString: String):Result<MCQResponse> {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

            val jsonAdapter: JsonAdapter<MCQResponse> = moshi.adapter(MCQResponse::class.java)


            val request = Request.Builder()
                .url(urlString)
                .addHeader(API_KEY_NAME, API_KEY_VALUE)
                .addHeader(AUTH_TOKEN_NAME, AUTH_TOKEN_VALUE)
                .build()
        return try {
            val response = client.newCall(request).execute()

            checkResponse(response)

            val json = response.body?.string()

            if (response.isSuccessful) {
                val data = json?.let { jsonAdapter.fromJson(it) }!!
                Result.Success(data)
                // Parse response body and return Result.Success
            } else {
                Result.ApiError(message = json.toString(),code = response.code)

                // Handle error and return Result.Error
            }
        } catch (e: Exception) {
            Result.Error(exception = e)
            // Handle exception and return Result.Error
        }


    }

    override suspend fun fetchArticles(urlString: String): Result<Articles> {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter: JsonAdapter<Articles> = moshi.adapter(Articles::class.java)


        val request = Request.Builder()
            .url(urlString)
            .addHeader(API_KEY_NAME, API_KEY_VALUE)
            .addHeader(AUTH_TOKEN_NAME, AUTH_TOKEN_VALUE)
            .build()
        return try {
            val response = client.newCall(request).execute()

            checkResponse(response)

            val json = response.body?.string()

            if (response.isSuccessful) {
                val data = json?.let { jsonAdapter.fromJson(it) }!!
                Result.Success(data)
                // Parse response body and return Result.Success
            } else {
                Result.ApiError(message = json.toString(),code = response.code)

                // Handle error and return Result.Error
            }
        } catch (e: Exception) {
            Result.Error(exception = e)
            // Handle exception and return Result.Error
        }
    }

    private fun checkResponse(response: Response) {
        if(response.networkResponse == null && response.cacheResponse != null){
            Log.d("N/W or Cache", "checkResponse: Came from Cache")
        }
        else if(response.cacheResponse == null && response.networkResponse != null){
            Log.d("N/W or Cache", "checkResponse: Came from Network")
        }
        else if( response.cacheResponse != null && response.networkResponse != null){
            Log.d("N/W or Cache", "checkResponse: Conditional GET ")
        }
    }



    override suspend fun fetchTutorials(urlString: String): Result<Tutorials> {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter: JsonAdapter<Tutorials> = moshi.adapter(Tutorials::class.java)


        val request = Request.Builder()
            .url(urlString)
            .addHeader(API_KEY_NAME, API_KEY_VALUE)
            .addHeader(AUTH_TOKEN_NAME, AUTH_TOKEN_VALUE)
            .build()
        return try {
            val response = client.newCall(request).execute()

            checkResponse(response)

            val json = response.body?.string()

            if (response.isSuccessful) {
                val data = json?.let { jsonAdapter.fromJson(it) }!!
                Result.Success(data)
                // Parse response body and return Result.Success
            } else {
                Result.ApiError(message = json.toString(),code = response.code)

                // Handle error and return Result.Error
            }
        } catch (e: Exception) {
            Result.Error(exception = e)
            // Handle exception and return Result.Error
        }
    }

    override suspend fun fetchInterviewQuestions(urlString: String): Result<InterviewQuestions> {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter: JsonAdapter<InterviewQuestions> = moshi.adapter(InterviewQuestions::class.java)


        val request = Request.Builder()
            .url(urlString)
            .addHeader(API_KEY_NAME, API_KEY_VALUE)
            .addHeader(AUTH_TOKEN_NAME, AUTH_TOKEN_VALUE)
            .build()
        return try {
            val response = client.newCall(request).execute()

            checkResponse(response)

            val json = response.body?.string()

            if (response.isSuccessful) {
                val data = json?.let { jsonAdapter.fromJson(it) }!!
                Result.Success(data)
                // Parse response body and return Result.Success
            } else {
                Result.ApiError(message = json.toString(),code = response.code)

                // Handle error and return Result.Error
            }
        } catch (e: Exception) {
            Result.Error(exception = e)
            // Handle exception and return Result.Error
        }
    }

    override suspend fun fetchSearch(urlString: String): Result<ListOfDBCollsDocs> {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter: JsonAdapter<ListOfDBCollsDocs> = moshi.adapter(ListOfDBCollsDocs::class.java)


        val request = Request.Builder()
            .url(urlString)
            .addHeader(API_KEY_NAME, API_KEY_VALUE)
            .addHeader(AUTH_TOKEN_NAME, AUTH_TOKEN_VALUE)
            .build()
        return try {
            val response = client.newCall(request).execute()

            checkResponse(response)

            val json = response.body?.string()

            if (response.isSuccessful) {
                val data = json?.let { jsonAdapter.fromJson(it) }!!
                Result.Success(data)
                // Parse response body and return Result.Success
            } else {
                Result.ApiError(message = json.toString(),code = response.code)

                // Handle error and return Result.Error
            }
        } catch (e: Exception) {
            Result.Error(exception = e)
            // Handle exception and return Result.Error
        }
    }

    override suspend fun fetchQuerySearch(urlString: String): Result<QuerySearch> {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter: JsonAdapter<QuerySearch> = moshi.adapter(QuerySearch::class.java)


        val request = Request.Builder()
            .url(urlString)
            .addHeader(API_KEY_NAME, API_KEY_VALUE)
            .addHeader(AUTH_TOKEN_NAME, AUTH_TOKEN_VALUE)
            .build()
        return try {
            val response = client.newCall(request).execute()

            checkResponse(response)

            val json = response.body?.string()

            if (response.isSuccessful) {
                val data = json?.let { jsonAdapter.fromJson(it) }!!
                Result.Success(data)
                // Parse response body and return Result.Success
            } else {
                Result.ApiError(message = json.toString(),code = response.code)

                // Handle error and return Result.Error
            }
        } catch (e: Exception) {
            Result.Error(exception = e)
            // Handle exception and return Result.Error
        }
    }


}