package com.example.leadcode.model

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class ApiError(val message: String, val code: Int) :Result<Nothing>()
    data class Error(val exception: Exception) : Result<Nothing>()
}