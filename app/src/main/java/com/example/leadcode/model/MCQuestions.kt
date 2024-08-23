package com.example.leadcode.model

import kotlinx.serialization.Serializable

data class MCQResponse(
    val topic: String? = null,
    val subtopic: String? = null,
    val collections: List<String> = listOf(),
    val questions: List<MCQuestion> = listOf()
)

data class MCQuestions (
    val questions: List<MCQuestion> = listOf()
)
data class MCQuestion(
    val _id : String? = null,
    val question: String? = null,
    val options: Options = Options(),
    val answer: String? = null
)

data class Options(
    val a: String? = null,
    val b: String? = null,
    val c: String? = null,
    val d: String? = null
)
