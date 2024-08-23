package com.example.leadcode.presentation.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.leadcode.model.MCQuestion
import com.example.leadcode.presentation.viewmodels.MyQuestionsViewModel
import com.example.leadcode.utils.composables.HeadingText
import com.example.leadcode.utils.composables.NormalText
import com.example.leadcode.utils.composables.RadioButtonGroup
import com.example.leadcode.utils.composables.StripedProgressIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.log


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizLayoutScreen(viewModel: MyQuestionsViewModel, goback: () -> Unit) {
    val scope = rememberCoroutineScope()
    var showAnswers by remember {
        mutableStateOf(false)
    }
    var score by remember {
        mutableStateOf(0)
    }
    val questionsLimit = viewModel.questionsLimit.collectAsState().value
    val totalTime: Long =
        (if (questionsLimit != 5) (questionsLimit - 5) else questionsLimit) * 60L * 1000L
    var timeRemaining by remember { mutableStateOf(totalTime) }
    var isRunning by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = isRunning) {
        if (isRunning) {
            while (timeRemaining > 0) {
                delay(1000L)
                timeRemaining -= 1000L
            }
            isRunning = false
        }
    }

    val formattedTime = formatTime(timeRemaining)
    val questions = viewModel.questionsResponseState.collectAsStateWithLifecycle().value
    val inProgress = viewModel.inProgress.collectAsStateWithLifecycle().value
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    if (!showAnswers)
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
                        Card(
                            modifier = Modifier
                                .padding(start = 10.dp, top = 10.dp)
                                .size(width = 180.dp, height = 50.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(Color.Black)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                UsualButton(data = formattedTime) {
                                    isRunning = !isRunning
                                }


                            }
                        }
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

            if (questions?.questions?.isNotEmpty() == true) {
                var currentQ by remember {
                    mutableStateOf(0)
                }
                var nextQuestionEnabled by remember { mutableStateOf(false) }

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp), color = Color.Black
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.fillMaxSize()) {


                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                questions.topic?.let {
                                    UsualButton(data = it) {

                                    }
                                }
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = "Right",
                                    tint = Color.White
                                )
                                questions.subtopic?.let {
                                    UsualButton(data = it) {

                                    }
                                }
                            }
                            var selectedOption = remember { mutableStateOf("") }

                            if (currentQ < questions.questions.size) {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(500.dp)
                                    .background(Color.Black)
                                    .padding(8.dp)
                            ) {
                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    verticalArrangement = Arrangement.Center
                                ) {


                                        Row(modifier = Modifier.padding(bottom = 40.dp)) {


                                            questions.questions[currentQ].let { question ->
                                                question.question?.let {
                                                    Text(
                                                        text = it,
                                                        color = Color.White,
                                                        fontSize = 18.sp,
                                                        fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                                                    )
                                                }
                                            }
                                        }


                                        Column(modifier = Modifier.padding(start = 24.dp)) {


                                            Text(
                                                modifier = Modifier.padding(start = 24.dp),
                                                text = "select one Answer",
                                                color = Color.Gray,
                                                fontSize = 16.sp,
                                                fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                                            )
                                            val optionsLocal = listOf(
                                                questions.questions[currentQ].options.a,
                                                questions.questions[currentQ].options.b,
                                                questions.questions[currentQ].options.c,
                                                questions.questions[currentQ].options.d,

                                                )
                                            RadioButtonGroup(
                                                options = optionsLocal,
                                                selectedOption = selectedOption.value,
                                                onOptionSelected = {
                                                    selectedOption.value = it
                                                    nextQuestionEnabled = true

                                                }
                                            )

                                        }


                                }
                            }
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                horizontalArrangement = Arrangement.Absolute.Right
                            )

                            {

                                var buttonText by remember {
                                    mutableStateOf("Next")
                                }
                                Button(
                                    onClick = {
                                        scope.launch {
                                            if (currentQ < questions.questions.size) {


                                                if (currentQ == questions.questions.size - 1)
                                                    buttonText = "Submit"
                                                if (selectedOption.value ==
                                                    when (questions.questions[currentQ].answer) {
                                                        "a" -> questions.questions[currentQ].options.a
                                                        "b" -> questions.questions[currentQ].options.b
                                                        "c" -> questions.questions[currentQ].options.c
                                                        "d" -> questions.questions[currentQ].options.d
                                                        else -> "Nothing" // Handle cases where the answer key is not a, b, c, or d
                                                    }
                                                ) {
                                                    score++
                                                }
                                                currentQ++
                                            }
                                            if (buttonText == "Submit" && currentQ == questions.questions.size) {
                                                showAnswers = true
                                            }

                                        }

                                    },
                                    shape = RoundedCornerShape(48.dp),
                                    modifier = Modifier
                                        .padding(24.dp)
                                        .width(100.dp)
                                        .height(40.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFFFF3D00
                                        )
                                    ),
                                    enabled = nextQuestionEnabled
                                ) {


                                    NormalText(data = buttonText)


                                }
                            }
                            }
                        }
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


    AnimatedVisibility(visible = showAnswers) {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
            Column(Modifier.fillMaxSize()) {


                LazyColumn( contentPadding = PaddingValues(24.dp)) {
                    itemsIndexed(questions.questions) { index, question ->
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = AbsoluteAlignment.Left
                        ) {


                            question.question?.let {
                                HeadingText(
                                    data = it,
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Left
                                )
                            }
                            question.answer?.let { it ->
                                when (it) {
                                    "a" -> question.options.a
                                    "b" -> question.options.b
                                    "c" -> question.options.c
                                    "d" -> question.options.d
                                    else -> "Nothing" // Handle cases where the answer key is not a, b, c, or d
                                }?.let { it1 ->
                                    UsualButton(data = it1, color = Color.Green) {

                                    }
                                }
                            }
                            HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp))
                        }

                    }
                    item {
                        HeadingText(
                            data = "Score : ${score}/${questions.questions.size}",
                            color = Color(0xFFFF3D00),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                    }

                }
                UsualButton(data = "Lets goooo!!!", color = Color(0xFFFF3D00), modifier = Modifier.fillMaxWidth()) {
                    goback.invoke()
                }
            }

        }
    }

}


private fun formatTime(timeInMillis: Long): String {
    val totalSeconds = timeInMillis / 1000
    val minutes = totalSeconds / 60
    val remainingSeconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

@Composable
fun MCQLayout(
    modifier: Modifier = Modifier,
    topic: String,
    subTopic: String,
    question: MCQuestion,
    openScreen: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {


    }
}

@Composable
fun DummyScreen(modifier: Modifier = Modifier) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        HeadingText(data = "Android")

    }
}

@Preview
@Composable
private fun PreviewScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
        Column(Modifier.fillMaxSize()) {


            LazyColumn( contentPadding = PaddingValues(24.dp)) {
                items(10) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = AbsoluteAlignment.Left
                    ) {



                            HeadingText(
                                data = "Question is this?",
                                modifier = Modifier.padding(vertical = 8.dp),
                                fontSize = 18.sp,
                                textAlign = TextAlign.Left
                            )



                                UsualButton(data = "Answer", color = Color.Green) {

                                }


                        HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp))
                    }

                }
                item {
                    HeadingText(
                        data = "Score : 4/5",
                        color = Color(0xFFFF3D00),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
                        textAlign = TextAlign.Center
                    )

                }
                item {
                    UsualButton(data = "Lets goooo!!!", color = Color(0xFFFF3D00), modifier = Modifier.fillMaxWidth()) {
//                goback.invoke()
                    }
                }

            }


        }

    }
}
