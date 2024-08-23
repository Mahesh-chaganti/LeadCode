package com.example.leadcode.model

data class InterviewQuestions(

    val interviewQuestions: List<Question> = listOf()
)

data class Question(
    val _id: String? = null,
    val question: String? = null,
    val answer: String? = null
)
