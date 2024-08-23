package com.example.leadcode.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leadcode.model.Article
import com.example.leadcode.model.CodeSnippet
import com.example.leadcode.model.Section
import com.example.leadcode.model.Subsection
import com.example.leadcode.utils.composables.HeadingText
import com.example.leadcode.utils.composables.NormalText


@Composable
fun OneArticleLayout(modifier: Modifier, article: Article) {
    Column(modifier = modifier.padding(24.dp).verticalScroll(rememberScrollState())){

        article.introduction?.let { ArticleIntro(it) }
        ArticleSections(article.sections)
        article.conclusion?.let { ArticleConclusion(it) }
    }





}


@Composable
fun ArticleConclusion(conclusion: Section) {
    ArticleSection(section = conclusion)
}

@Composable
fun ArticleIntro(intro: Section) {
    ArticleSection(section = intro)
}

@Composable
fun ArticleSections(sections: List<Section>) {
    sections.forEachIndexed { index, section ->
        ArticleSection(section= section)
    }
}

@Composable
fun ArticleSection(section: Section) {
    Column {
        section.heading?.let {

            HeadingText(data = it,
                modifier = Modifier.padding(bottom = 24.dp),
                fontSize = 14.sp
            )
        }
        section.content?.let {
            NormalText(data = it,
                modifier = Modifier.padding(bottom = 24.dp),

            )
        }
        section.subsections?.let { ArticleSubSections(subsections = it) }
        section.code?.let { ArticleCodeSnippet(codesnippet = it) }
    }
}

@Composable
fun ArticleCodeSnippet(codesnippet: CodeSnippet) {
    Card(modifier = Modifier
        .wrapContentHeight()
        .fillMaxWidth()
        .padding(bottom = 24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column(Modifier) {

            codesnippet.language?.let { CodeLanguageText(data = it) }

            codesnippet.snippet?.let { CodeText(data = it) }

        }
    }

}

@Composable
fun CodeLanguageText(modifier: Modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, top = 10.dp), data: String) {
    NormalText(
        data = data,
        fontSize = 6.sp,
        modifier = modifier,
        fontWeight = FontWeight.ExtraLight,
        textAlign = TextAlign.Center,
        color = Color.LightGray

    )
}

@Composable
fun CodeText(modifier: Modifier = Modifier.padding(start = 10.dp, bottom = 10.dp), data: String) {
    NormalText(
        data = data ,
        fontSize = 6.sp,
        modifier = modifier,
        fontWeight = FontWeight.ExtraLight,
        lineHeight = 8.sp


    )
}

@Composable
fun ArticleSubSections(subsections: List<Subsection>) {
    subsections.forEachIndexed { index, subsection ->
        ArticleSubSection(subsection = subsection)
    }
}

@Composable
fun ArticleSubSection(subsection: Subsection) {
    subsection.subheading?.let {
        HeadingText(data = it,
            modifier = Modifier.padding(bottom = 24.dp),
            fontSize = 14.sp
        )
    }
    subsection.content?.let {
        NormalText(data = it,
            modifier = Modifier.padding(bottom = 24.dp),
            fontSize = 14.sp
        )
    }
    subsection.code?.let { ArticleCodeSnippet(codesnippet = it) }


}


@Composable
fun ArticleTitle(title: String) {
    HeadingText(data = title,
        modifier = Modifier.padding(bottom = 24.dp),
    )
}