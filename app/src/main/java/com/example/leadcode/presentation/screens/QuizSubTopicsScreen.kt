package com.example.leadcode.presentation.screens

import android.app.Activity
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.leadcode.model.MCQResponse
import com.example.leadcode.model.listOfTopics
import com.example.leadcode.presentation.viewmodels.MyQuestionsViewModel
import com.example.leadcode.utils.composables.HeadingText
import com.example.leadcode.utils.composables.NormalText
import com.example.leadcode.utils.composables.StripedProgressIndicator
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizSubTopicsScreen(
    openScreen :(String) -> Unit,
    goback :() -> Unit,
    viewModel: MyQuestionsViewModel
) {
    val activity = LocalContext.current as Activity
    LaunchedEffect(activity) {
        activity.window.setBackgroundDrawable(BitmapDrawable())
    }

    val collectionsInDB = viewModel.questionsResponseState.collectAsStateWithLifecycle().value
    val topic = viewModel.questionsResponseState.collectAsStateWithLifecycle().value?.topic
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
// ...
    val inProgress = viewModel.inProgress.collectAsState().value



    Log.d("collections", "QuizSubTopicsScreen: $collectionsInDB")
    Log.d("collections", "QuizSubTopicsScreen: ${collectionsInDB?.collections}")

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
                        if (topic != null) {
                            HeadingText(modifier = Modifier,data = topic ,fontSize = 18.sp)
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
            if (inProgress) {


                Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
                    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                        StripedProgressIndicator()

                    }


                }
            }
            

        if (collectionsInDB.collections.isNotEmpty()) {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .padding(innerPadding)
            ) {


                    itemsIndexed(

                        collectionsInDB.collections
                    ) { index, item ->

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 8.dp)
                                , horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            NormalText(data = item, modifier = Modifier.weight(0.5f), fontSize = 14.sp)

                            Row(
                                modifier = Modifier.weight(0.5f),
                                horizontalArrangement = Arrangement.Absolute.Right
                            ) {
                                Card(shape = CircleShape,
                                    modifier = Modifier
                                        .clickable {
                                            if (topic != null) {
                                                viewModel.onQuizSubTopicClick(
                                                    openScreen,
                                                    topic = topic,
                                                    subTopic = item,
                                                    limit = 5
                                                )
                                            }
                                        }
                                        .padding(2.dp)
                                        .size(36.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.Cyan)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        NormalText(
                                            data = "5Q",
                                            modifier = Modifier.align(Alignment.CenterHorizontally),
                                            fontSize = 12.sp,
                                            color = Color.Black,
                                            textAlign = TextAlign.Center
                                        )
                                    }


                                }
                                Card(
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .clickable {
                                            if (topic != null) {
                                                viewModel.onQuizSubTopicClick(
                                                    openScreen,
                                                    topic = topic,
                                                    subTopic = item,
                                                    limit = 15
                                                )
                                            }
                                        }
                                        .padding(2.dp)
                                        .size(36.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.Red)

                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {

                                        NormalText(
                                            data = "15Q",
                                            modifier = Modifier,
                                            fontSize = 12.sp,
                                            color = Color.Black
                                        )
                                    }

                                }
                                Card(
                                    shape = CircleShape,
                                    modifier = Modifier
                                        .clickable {
                                            if (topic != null) {
                                                viewModel.onQuizSubTopicClick(
                                                    openScreen,
                                                    topic = topic,
                                                    subTopic = item,
                                                    limit = 25
                                                )
                                            }
                                        }
                                        .padding(2.dp)
                                        .size(36.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.Yellow)

                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {

                                        NormalText(
                                            data = "25Q",
                                            modifier = Modifier,
                                            fontSize = 12.sp,
                                            color = Color.Black
                                        )
                                    }
                                }


                            }
                        }
                        HorizontalDivider()
                    }
                }
            }
        }


        }







}

@Composable
private fun SomePreview() {
//    QuizSubTopicsScreen()
//    Surface(modifier = Modifier.fillMaxSize(),color = Color.Black){
//
//        Button(onClick = { viewModel.onQuizSubTopicClick(openScreen,topic = "Android", subTopic = "Jetpack Compose",limit = 5) }
//            , modifier = Modifier.size(100.dp)) {
//            NormalText(data = "Hello")
//        }
//    }
}

@Preview
@Composable
private fun SubTopicsPrev() {
    HorizontalDivider(color = Color.White,
        thickness = Dp.Hairline,modifier = Modifier.fillMaxSize())

}
