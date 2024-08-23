package com.example.leadcode.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.leadcode.presentation.viewmodels.InterviewQuestionsViewModel
import com.example.leadcode.utils.composables.HeadingText
import com.example.leadcode.utils.composables.NormalText
import com.example.leadcode.utils.composables.StripedProgressIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewLayoutScreen(viewModel: InterviewQuestionsViewModel,goback:()-> Unit) {
    val interviewQuestions = viewModel.interviewPrepsState.collectAsState().value
    val inProgress = viewModel.inProgress.collectAsState().value
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val title = viewModel.topicClicked.collectAsState().value
    Surface( modifier = Modifier.fillMaxSize(), color = Color.Black) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                MediumTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black,
                        titleContentColor = Color(0xFFFF3D00),
                        scrolledContainerColor = Color.Black

                    ),
                    title = {

                            HeadingText(modifier = Modifier,data = title ,fontSize = 18.sp, textAlign = TextAlign.Start)

                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            viewModel.onBackClick(goback)
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "Localized description",
                                tint = Color(0xFFFF3D00)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* do something */ }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "Localized description",
                                tint = Color(0xFFFF3D00)
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,

                    )
            },
            containerColor = Color.Black,

            ) { innerPadding ->
            Surface(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), color = Color.Black) {

                if (interviewQuestions != null)
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp)
                    ) {
                        itemsIndexed(interviewQuestions.interviewQuestions) { index, question ->
                            question.question.let {
                                if (it != null) {
                                    HeadingText(
                                        data = it,
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Start
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                            question.answer.let {
                                if (it != null) {
                                    NormalText(
                                        data = it,
                                        color = Color.White,
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))

                            HorizontalDivider()
                            Spacer(modifier = Modifier.height(24.dp))



                        }
                    }

            }
            if (inProgress) {


                Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
                    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                        StripedProgressIndicator()

                    }


                }
            }
        }
    }

}
