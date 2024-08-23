package com.example.leadcode.presentation.screens

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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import com.example.leadcode.model.listOfTopics
import com.example.leadcode.presentation.viewmodels.InterviewQuestionsViewModel
import com.example.leadcode.presentation.viewmodels.SearchScreenViewModel
import com.example.leadcode.utils.composables.HeadingText
import com.example.leadcode.utils.composables.NormalText
import com.example.leadcode.utils.composables.StripedProgressIndicator
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewPrepScreen(
    modifier: Modifier,
    searchScreenViewModel: SearchScreenViewModel,
    openScreen: (String) -> Unit,
    interviewPrepViewModel: InterviewQuestionsViewModel
) {

    val inProgress = searchScreenViewModel.inProgress.collectAsState().value
    val search = searchScreenViewModel.searchState.collectAsStateWithLifecycle().value
    val interviewDb = search?.listOfDbColls?.find { it.database == "InterviewQuestionsDb" }

    if(search?.listOfDbColls?.isNotEmpty() == true){
    Surface( modifier = modifier.fillMaxSize(), color = Color.Black) {


        if (interviewDb?.collections != null) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .padding(),
                horizontalArrangement = Arrangement.Center
            ) {
                itemsIndexed(interviewDb.collections) { index, topic ->

                    val topicAndIcon = listOfTopics.find { it.topic == topic }

                    Column(
                        Modifier
                            .height(240.dp)
                            .wrapContentWidth(), Arrangement.Center, Alignment.CenterHorizontally
                    ) {


                        Card(
                            Modifier
                                .clickable {
                                    topicAndIcon?.topic?.let {
                                        interviewPrepViewModel.onInterviewTopicClick(openScreen,
                                            it
                                        )
                                    }
                                    Log.d("WhichTopic", "QuizScreen: ${topicAndIcon?.topic}")
                                }
                                .padding(12.dp)
                                .clip(CircleShape)
                                .size(120.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {


                                AsyncImage(
                                    model = topicAndIcon?.Icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(100.dp)
                                )

                            }

                        }
                        topicAndIcon?.topic?.let {
                            NormalText(
                                data = it,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 12.sp
                            )
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
suspend fun delayy(){
    delay(100L)

}

@Composable
fun DelayTest(modifier: Modifier = Modifier) {
    var progress by remember{mutableStateOf(0f)}

    LaunchedEffect(key1 = true) {
        for(i in 1..10){
            delayy()
            progress = progress + 0.1f
        }

    }
    Surface(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxSize(), color = Color.Black) {
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            StripedProgressIndicator()

        }

    }
}
@Preview
@Composable
private fun InterviewQuestionsPreview() {
//    InterviewPrepScreen(modifier = Modifier)
}