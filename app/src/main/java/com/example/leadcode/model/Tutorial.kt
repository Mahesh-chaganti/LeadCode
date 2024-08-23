package com.example.leadcode.model

data class Tutorials(
    val tutorials: Tutorial = Tutorial()
)

data class Tutorial(
    val _id: String? = null,
    val title: String? = null,
    val introduction: Section? = Section(),
    val sections: List<Section> = listOf(),
    val conclusion: Section = Section()
)

data class Section(
    val heading: String? = null,
    val content: String? = null,
    val subsections: List<Subsection>? = listOf(),
    val code: CodeSnippet? = CodeSnippet()
)

data class Subsection(
    val subheading: String? = null,
    val content: String? = null,
    val code: CodeSnippet? = CodeSnippet()
)

data class CodeSnippet(
    val language: String? = null,
    val snippet: String? = null
)
