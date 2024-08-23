package com.example.leadcode.presentation.screens

import android.media.Image
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.leadcode.R
import com.example.leadcode.model.CombinedItem
import com.example.leadcode.presentation.viewmodels.ArticleScreenViewModel
import com.example.leadcode.presentation.viewmodels.SearchScreenViewModel
import com.example.leadcode.utils.composables.NormalText
import com.example.leadcode.utils.composables.StripedProgressIndicator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ArticleScreen(
    modifier: Modifier,
    searchScreenViewModel: SearchScreenViewModel,
    viewModel: ArticleScreenViewModel,
    openScreen: (String) -> Unit
) {
    val search = searchScreenViewModel.searchState.collectAsStateWithLifecycle().value
    val listArticles = searchScreenViewModel.listArticlesState.collectAsStateWithLifecycle().value
    val filteredArticles = searchScreenViewModel.filteredListArticlesState.collectAsStateWithLifecycle().value
    val inProgress = searchScreenViewModel.inProgress.collectAsState().value

    var originalTopics by remember {
        mutableStateOf<Set<String>>(emptySet())
    }

    var applyFilterPlease by remember {
        mutableStateOf(false)
    }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()




    if (search?.listOfDbColls?.isNotEmpty() == true) {
        Surface(modifier = modifier.fillMaxSize(), color = Color.Black) {


            var expanded by remember { mutableStateOf(false) }

            Column {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.Right) {


                    ElevatedButton(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        onClick = {
                            expanded = !expanded

                                  },
                        shape = RoundedCornerShape(24.dp),
                        border = BorderStroke(
                            1.dp,
                            Color.Black
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(2.dp)

                    ) {

                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                modifier = Modifier.padding(end = 2.dp),
                                text = "Filter",
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp
                            )

                            Icon(
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(start = 4.dp),
                                painter = painterResource(id = R.drawable.filtericon),
                                contentDescription = "Add",
                                tint = Color(0xFFFF3D00)
                            )
                        }
                    }
                }
                val selectedIndices = remember { mutableStateListOf<Int>() }

                if (expanded) {
                    ModalBottomSheet(modifier = Modifier.padding(),
                        onDismissRequest = {
                            expanded = false
                        },
                        sheetState = sheetState
                    ) {
                        // Sheet content
                        FlowRow(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
                            originalTopics.toList().forEachIndexed {index,topic ->
                                val isSelected = index in selectedIndices

                                topic?.let {
                                    TopicButton(
                                        modifier = Modifier
                                            .wrapContentHeight(Alignment.CenterVertically)
                                            .wrapContentWidth()
                                            .padding(2.dp),
                                        data = topic,
                                        onClick = {
                                            searchScreenViewModel.onTopicButtonClick(
                                                topic = topic,

                                                )
                                            Log.d("filtered list articles", "ArticleScreen: $filteredArticles")
                                            if (isSelected){
                                                selectedIndices.remove(index)
                                            } else {
                                                selectedIndices.add(index)
                                            }
                                        },
                                        color = if (isSelected) Color(0xFFFF3D00) else Color.White,
                                        imageVector = if(isSelected) Icons.Filled.Clear else Icons.Filled.Add


                                    )

                                }
                            }




                        }
                        Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                            UsualButton(data = "Apply") {
                                searchScreenViewModel.onApplyFilterClick()
                                expanded = !expanded
                                applyFilterPlease = true
                            }
                            UsualButton(data = "Clear all") {
                                searchScreenViewModel.onClearAllClick()
                                expanded = !expanded
                                applyFilterPlease = false
                                selectedIndices.removeAll(selectedIndices)
                            }
                        }
                    }
                }

                LaunchedEffect(listArticles) {

                        if(listArticles.isNotEmpty()) {
                            val newTopics = listArticles.mapNotNull { it?.collection }.toSet()
                            originalTopics = newTopics
                            Log.d("topics", "ArticleScreen: ${newTopics}")
                        }


                    Log.d("articles", "ArticleScreen: $listArticles")

                    Log.d("topics", "ArticleScreen: ${originalTopics}")

                }
                Log.d("filtered artis", "ArticleScreen: $filteredArticles")


                LazyColumn(
                    Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(24.dp)
                ) {
                    itemsIndexed(if(applyFilterPlease)filteredArticles else listArticles) { index, combined ->
                        combined.let {
                            Column(
                                Modifier
                                    .clickable {


                                        combined?.doc?.let { it1 ->
                                            combined.collection?.let { it2 ->
                                                viewModel.onArticleClick(

                                                    openScreen = openScreen,
                                                    topic = it2,
                                                    title = it1
                                                )
                                            }
                                        }


                                    }
                                    .fillMaxWidth()
                                    .padding()
                            ) {

                                combined?.doc?.let { it1 ->
                                    NormalText(
                                        data = it1,
                                        fontSize = 14.sp
                                    )
                                }


                                Row(Modifier.fillMaxSize()) {
                                    combined?.collection?.let { it1 ->
                                        NormalText(
                                            data = it1,
                                            color = Color.Gray
                                        )
                                    }


                                }
                                Spacer(modifier = Modifier.height(24.dp))
                                HorizontalDivider()
                            }
                            Spacer(modifier = Modifier.height(24.dp))

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

fun WhatTopics(listArticles: List<CombinedItem?>): List<String> {
    val topics: List<String> = listOf()
    listArticles.forEach {topic ->
        topic?.collection.takeIf { it !in topics }
    }
    return topics
    Log.d("chupi list", "WhatTopics: $topics")
}


@Composable
private fun PrevFunction() {
    val topics = listOf(
        "Android",
        "AWS",
        "Azure",
        "GCP",
        "Web Development",
        "DSA",
        "Machine Learning",
        "Deep Learning",
        "Backend",
        "Frontend",
        "Blockchain",
        "Web3",
        "Angular",
        "Data Engineering",
        "Data Analysis",
        "Firebase",
        "Javascript",
        "Node.js",
        "Mongodb",
        "React"
    )
    var expanded by remember { mutableStateOf(false) }
    Column {
        ElevatedButton(
            modifier = Modifier,
            onClick = { expanded = !expanded },
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(
                1.dp,
                Color.Black
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(2.dp)

        ) {

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.padding(end = 2.dp),
                    text = "Filter",
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp
                )

                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(start = 4.dp),
                    painter = painterResource(id = R.drawable.filtericon),
                    contentDescription = "Add",
                    tint = Color(0xFFFF3D00)
                )
            }

        }
        AnimatedVisibility(visible = expanded) {


            LazyHorizontalStaggeredGrid(
                rows = StaggeredGridCells.Fixed(4), modifier = Modifier
                    .fillMaxWidth()
                    .padding()
                    .height(200.dp)
            ) {
                itemsIndexed(topics) { index, item ->
                    TopicButton(
                        modifier = Modifier
                            .wrapContentHeight(Alignment.CenterVertically)
                            .wrapContentWidth()
                            .padding(2.dp),
                        data = item
                    )
                }
            }
        }
    }
}

@Composable
fun TopicButton(modifier: Modifier, data: String, onClick : () -> Unit = {}, color: Color = Color.White,
                imageVector: ImageVector = Icons.Default.Add) {


    ElevatedButton(
        modifier = modifier,
        onClick = { onClick.invoke()},
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            1.dp,
            Color.Black
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.Black
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(2.dp)

    ) {

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(end = 2.dp),
                text = data,
                fontWeight = FontWeight.Black,
                fontSize = 16.sp
            )

            Icon(
                modifier = Modifier
                    .height(16.dp)
                    .width(14.dp)
                    .padding(start = 4.dp),
                imageVector = imageVector,
                contentDescription = "Add"
            )
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalMaterialApi::class
)
@Preview
@Composable
private fun Examplepre() {

}



