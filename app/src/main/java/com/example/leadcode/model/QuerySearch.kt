package com.example.leadcode.model

data class QuerySearch(
    val question: Boolean = false,
    val article: Boolean = false,
    val tutorial: Boolean = false,
    val interview: Boolean = false
)