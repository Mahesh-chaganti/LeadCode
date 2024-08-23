package com.example.leadcode.model

data class Articles(
        val articles: Article = Article()
)

data class Article(
    val _id: String? = null,
    val title: String? = null,
    val introduction: Section? = Section(),
    val sections: List<Section> = listOf(),
    val conclusion: Section? = Section()
)



