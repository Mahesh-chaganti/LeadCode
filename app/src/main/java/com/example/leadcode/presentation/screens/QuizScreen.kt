package com.example.leadcode.presentation.screens

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
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
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.leadcode.R
import com.example.leadcode.model.MCQuestion
import com.example.leadcode.model.TopicsAndIcons
import com.example.leadcode.model.listOfTopics
import com.example.leadcode.presentation.viewmodels.MyQuestionsViewModel
import com.example.leadcode.presentation.viewmodels.SearchScreenViewModel
import com.example.leadcode.utils.composables.HeadingText
import com.example.leadcode.utils.composables.NormalText
import com.example.leadcode.utils.composables.RadioButtonGroup
import com.example.leadcode.utils.composables.StripedProgressIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    modifier: Modifier,
    viewModel: MyQuestionsViewModel,
    searchScreenViewModel: SearchScreenViewModel,
    openScreen: (String) -> Unit,
) {

    val palettesMap = viewModel.paletteCacheState.collectAsStateWithLifecycle().value
    val inProgress = searchScreenViewModel.inProgress.collectAsState().value

    if (palettesMap.isEmpty()) {

        viewModel.setContext(context = LocalContext.current.applicationContext)
        viewModel.generatePalette()


    }
    val search = searchScreenViewModel.searchState.collectAsStateWithLifecycle().value
    val excludedDatabases = listOf(
        "ArticlesDb",
        "TutorialsDb",
        "__realm_sync_65c08ccee78937a95d6d844c",
        "admin",
        "local",
        "InterviewQuestionsDb",
        "DSA",
        "Backend",
        "ChatQuestionsDb",
        "MachineLearning"
    )
    if (search?.listOfDbColls?.isNotEmpty() == true)
        Surface(modifier = modifier.fillMaxSize(), color = Color.Black) {
            val configuration = LocalConfiguration.current

            val screenWidthDp = configuration.screenWidthDp.dp
            val screenHeightDp = configuration.screenHeightDp.dp

            if (palettesMap.toMap().size > 0) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .padding(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    itemsIndexed(search?.listOfDbColls) { index, topic ->
                        if (!excludedDatabases.contains(topic.database)) {

                            val topicAndIcon = listOfTopics.find { it.topic == topic.database }


                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,

                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(
                                        shape = RoundedCornerShape(24.dp),
                                        color = Color.DarkGray
                                    )
                                    .size(screenWidthDp / 2, screenHeightDp / 4)









                            ) {

                                Card(
                                    onClick = {
                                        topicAndIcon?.topic?.let {
                                            viewModel.onQuizTopicClick(
                                                openScreen,
                                                it
                                            )
                                        }
                                    },
                                    Modifier
                                        .weight(5f)
                                        .background(
                                            brush = Brush.linearGradient(
                                                listOf(

                                                    palettesMap[topicAndIcon?.Icon]?.first
                                                        ?: Color.Red,
                                                    palettesMap[topicAndIcon?.Icon]?.second
                                                        ?: Color.White
                                                )
                                            ),
                                            RoundedCornerShape(24.dp)
                                        ),


                                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {


                                        AsyncImage(
                                            model = topicAndIcon?.Icon,
                                            contentDescription = null,
                                            modifier = Modifier.size(100.dp),
                                            contentScale = ContentScale.Fit,
                                            alignment = Alignment.Center,

                                            )

                                    }
                                }
                                Column( modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {


                                    topicAndIcon?.topic?.let {
                                        NormalText(
                                            data = it, 
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 14.sp,
                                            lineHeight = 2.sp
                                        )
                                    }
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

@Composable
fun EachCard(
    modifier: Modifier = Modifier,
    topicsAndIcons: TopicsAndIcons,
    viewModel: MyQuestionsViewModel
) {
    val palettesMap = viewModel.paletteCacheState.collectAsStateWithLifecycle().value

    val listOfColors: List<Color> =

        listOf(

            palettesMap[topicsAndIcons.Icon]?.first ?: Color.Red,
            palettesMap[topicsAndIcons.Icon]?.second ?: Color.White
            //    if((palettesMap[topic.Icon]?.first?.value?.toInt()
            //            ?: 0) == 0
            //    ) palettesMap[topic.Icon]!!.first else Color.Blue,
            //    if((palettesMap[topic.Icon]?.first?.value?.toInt()
            //            ?: 0) == 0
            //    )
            //        palettesMap[topic.Icon]!!.second
            //    else Color.White


        )


    Column(
        Modifier
            .height(240.dp)
            .wrapContentWidth(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {


        Card(
            Modifier
                .clickable {
//                    viewModel.onQuizTopicClick(openScreen, topicsAndIcons.topic)
                    Log.d("WhichTopic", "QuizScreen: ${topicsAndIcons.topic}")
                }
                .padding(12.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(
                    brush = Brush.linearGradient(
                        listOfColors
                    )
                )
                .size(180.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {


                AsyncImage(
                    model = topicsAndIcons.Icon,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )

            }


        }
        NormalText(
            data = topicsAndIcons.topic,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp
        )


    }

}


@Preview
@Composable
private fun TopicsPrev() {
//    TopicsLayout()
    PrevComp()
}

@Composable
fun PrevComp(modifier: Modifier = Modifier) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {

    }
    LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(24.dp)) {
        items(10) { index -> // Replace 10 with the actual number of items
            Column(
                verticalArrangement = Arrangement.Center,

                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                Color.Blue,
                                Color.White
                            )
                        )
                    )

            ) {


                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    ),
                    modifier = Modifier


                        .size(width = 240.dp, height = 100.dp)
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(Color.Transparent)

                ) {
                    Text(
                        text = "Elevated",
                        modifier = Modifier
                            .padding(16.dp),
                        color = Color.White

                    )
                }
                Text(
                    text = "Elevated",
                    modifier = Modifier,
                    color = Color.White


                )

            }
        }
    }
}