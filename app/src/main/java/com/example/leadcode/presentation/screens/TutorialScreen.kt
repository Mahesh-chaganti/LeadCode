package com.example.leadcode.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.leadcode.model.CollsAndDocs
import com.example.leadcode.model.DBAndColl
import com.example.leadcode.model.combineAndShuffle
import com.example.leadcode.model.listOfTopics
import com.example.leadcode.presentation.viewmodels.ArticleScreenViewModel
import com.example.leadcode.presentation.viewmodels.SearchScreenViewModel
import com.example.leadcode.presentation.viewmodels.TutorialScreenViewModel
import com.example.leadcode.utils.composables.HeadingText
import com.example.leadcode.utils.composables.StripedProgressIndicator
import okhttp3.internal.wait

@Composable
fun TutorialScreen(
    modifier: Modifier,
    viewModel: TutorialScreenViewModel,
    searchScreenViewModel: SearchScreenViewModel,
    openScreen: (String) -> Unit
) {

    val search = searchScreenViewModel.searchState.collectAsStateWithLifecycle().value

    val inProgress = searchScreenViewModel.inProgress.collectAsState().value

    var expanded by remember {
        mutableStateOf(false)
    }
    if (search?.listOfDbColls?.isNotEmpty() == true) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(), color = Color.Black
        ) {

            Column {




                Row(
                    modifier = Modifier.padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(),
                        userScrollEnabled = expanded
                    ) {
                        itemsIndexed(search.tutorialDocs) { index, item ->
                            val topicAndIcon = listOfTopics.find { it.topic == item.collection }

                            var expanded by remember {
                                mutableStateOf(false)
                            }
                            Column {


                                val tutorialsTopic = buildAnnotatedString {
                                    append(item.collection)
                                    appendInlineContent(
                                        "icon1",
                                        "[icon]"
                                    ) // Placeholder for the first icon
                                    appendInlineContent(
                                        "icon2",
                                        "[icon]"
                                    ) // Placeholder for the second icon
                                }

                                Text(
                                    text = tutorialsTopic, inlineContent = mapOf(
                                        "icon1" to InlineTextContent(
                                            Placeholder(
                                                width = 24.sp,
                                                height = 24.sp,
                                                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                                            )
                                        ) { topicAndIcon?.Icon?.let { it1 -> MyIcon1(it1) } },
                                        "icon2" to InlineTextContent(
                                            Placeholder(
                                                width = 16.sp,
                                                height = 16.sp,
                                                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                                            )
                                        ) {
                                            IconButton(
                                                onClick = { expanded = !expanded },
                                            ) {
                                                Icon(
                                                    imageVector = if (expanded) Icons.Rounded.KeyboardArrowRight
                                                    else Icons.Rounded.KeyboardArrowDown,
                                                    contentDescription = "",
                                                    tint = Color(0xFFFF3D00)
                                                )
                                            }
                                        }

                                    ), color = Color.White,
                                    fontSize = 24.sp,
                                    fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
                                    fontWeight = FontWeight.ExtraBold,
                                    style = TextStyle(lineHeight = 18.sp),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(bottom = 24.dp)
                                )
                                HorizontalDivider(modifier = Modifier.padding(bottom = 24.dp))


                                AnimatedVisibility(visible = expanded) {
                                    LazyColumn(
                                        modifier = Modifier
                                            .height(500.dp)
                                            .fillMaxWidth()

                                    )
                                    {


                                        itemsIndexed(item.docs) { index, doc ->

                                            val topicAndIcon = listOfTopics.find { it.topic == doc }
                                            Button(
                                                onClick = {
                                                    item.collection?.let {
                                                        viewModel.onTutorialClick(
                                                            openScreen = openScreen,
                                                            topic = it,
                                                            title = doc
                                                        )
                                                    }
                                                },
                                                Modifier
                                                    .fillMaxWidth(),
                                                colors = ButtonDefaults.elevatedButtonColors(Color.DarkGray),
                                                shape = RoundedCornerShape(8.dp),
//                        elevation = ButtonDefaults.elevatedButtonColors(),

                                            ) {
                                                val myText = buildAnnotatedString {
                                                    append(doc)
                                                    appendInlineContent(
                                                        "icon1",
                                                        "[icon]"
                                                    ) // Placeholder for the first icon
                                                    // Placeholder for the second icon

                                                }
                                                doc.let { it1 ->
                                                    Text(
                                                        text = myText, inlineContent = mapOf(
                                                            "icon1" to InlineTextContent(
                                                                Placeholder(
                                                                    width = 16.sp,
                                                                    height = 16.sp,
                                                                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                                                                )
                                                            ) {
                                                                topicAndIcon?.Icon?.let { it2 ->
                                                                    MyIcon1(
                                                                        it2
                                                                    )
                                                                }
                                                            },

                                                            ), color = Color.White,
                                                        fontSize = 14.sp,
                                                        fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
                                                        style = TextStyle(lineHeight = 18.sp),
                                                        textAlign = TextAlign.Left,
                                                        modifier = Modifier.fillMaxWidth()
                                                    )
                                                }


                                            }


                                            Spacer(modifier = Modifier.height(24.dp))

                                        }
                                        item{
                                            Row(modifier = Modifier.fillMaxWidth()
                                                ,horizontalArrangement = Arrangement.Center) {
                                                UsualButton(
                                                    data = "Load more",
                                                    color = Color(0xFFFF3D00),
                                                    modifier = Modifier
                                                ) {

                                                }
                                            }
                                            HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp))


                                        }
                                    }
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
fun TextWithIcons() {
    val myText = buildAnnotatedString {
        append("This is some text with ")
        appendInlineContent("icon1", "[icon]")
        append(" and ")
        appendInlineContent("icon2", "[icon]")
        append(".")
    }

    Column {
        Text(text = myText, inlineContent = mapOf(
            "icon1" to InlineTextContent(
                Placeholder(
                    width = 16.sp,
                    height = 16.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                MyIcon1("")
            },
            "icon2" to InlineTextContent(
                Placeholder(
                    width = 16.sp,
                    height = 16.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) { MyIcon2("") }
        ), color = Color.White)
    }
}

@Composable
fun MyIcon1(url: String) {
    AsyncImage(
        model = url,
        contentDescription = "Icon 1",
        modifier = Modifier.size(36.dp)

    )
}

@Composable
fun MyIcon2(url: String) {
    AsyncImage(
        model = url,
        contentDescription = "Icon 1",
        modifier = Modifier.size(36.dp)

    )
}

@Composable
fun PrevScreen(modifier: Modifier = Modifier) {

    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {


        val tutorialsTopic = buildAnnotatedString {
            append("Android")
            appendInlineContent(
                "icon1",
                "[icon]"
            ) // Placeholder for the first icon
        }

        Text(
            text = tutorialsTopic,
            inlineContent = mapOf(
                "icon1" to InlineTextContent(
                    Placeholder(
                        width = 16.sp,
                        height = 16.sp,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                    )
                ) { MyIcon1(listOfTopics[0].Icon) },

                ),
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle(lineHeight = 18.sp),

            )
        IconButton(
            onClick = { expanded = !expanded },
        ) {
            Icon(
                imageVector = if (expanded) Icons.Rounded.KeyboardArrowRight
                else Icons.Rounded.KeyboardArrowDown,
                contentDescription = "",
                tint = Color(0xFFFF3D00)
            )
        }

    }
}

@Preview
@Composable
private fun tutorialPreview() {
    PrevScreen()
}


