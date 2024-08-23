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
import com.example.leadcode.model.CodeSnippet
import com.example.leadcode.model.Section
import com.example.leadcode.model.Subsection
import com.example.leadcode.model.Tutorial
import com.example.leadcode.utils.composables.HeadingText
import com.example.leadcode.utils.composables.NormalText


@Composable
fun OneTutorialLayout(modifier: Modifier, tutorial: Tutorial) {
    Column(modifier = modifier
        .padding(24.dp)
        .verticalScroll(rememberScrollState())) {
        tutorial.introduction?.let { TutorialIntro(it) }
        TutorialSections(tutorial.sections)
        tutorial.conclusion?.let { TutorialConclusion(it) }
    }





}


@Composable
fun TutorialConclusion(conclusion: Section) {
    TutorialSection(section = conclusion)
}

@Composable
fun TutorialIntro(intro: Section) {
    TutorialSection(section = intro)
}

@Composable
fun TutorialSections(sections: List<Section>) {
    sections.forEachIndexed { index, section ->
        TutorialSection(section= section)
    }
}

@Composable
fun TutorialSection(section: Section) {
    Column() {
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
        section.subsections?.let { TutorialSubSections(subsections = it) }
        section.code?.let { TutorialCodeSnippet(codesnippet = it) }
    }
}

@Composable
fun TutorialCodeSnippet(codesnippet: CodeSnippet) {
    Card(modifier = Modifier
        .wrapContentHeight()
        .fillMaxWidth()
        .padding(bottom = 24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
    ) {
        Column(Modifier.fillMaxSize()) {

            codesnippet.language?.let { CodeLanguageText(data = it) }

            codesnippet.snippet?.let { CodeText(data = it) }

        }
    }

}



@Composable
fun TutorialSubSections(subsections: List<Subsection>) {
    subsections.forEachIndexed { index, subsection ->
        TutorialSubSection(subsection = subsection)
    }
}

@Composable
fun TutorialSubSection(subsection: Subsection) {
    subsection.subheading?.let {
        HeadingText(data = it,
            modifier = Modifier.padding(bottom = 24.dp),
            fontSize = 14.sp
        )
    }
    subsection.content?.let {
        NormalText(data = it,
            modifier = Modifier.padding(bottom = 24.dp),
        )
    }
    subsection.code?.let {
        TutorialCodeSnippet(codesnippet = it)
    }



}


@Composable
fun TutorialTitle(title: String) {
    HeadingText(data = title,
        modifier = Modifier.padding(bottom = 24.dp),
    )
}