package com.example.leadcode.presentation.screens

import android.graphics.Shader
import android.os.Build
import android.provider.MediaStore.Video
import android.util.DisplayMetrics
import android.util.Log
import android.view.Surface
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.leadcode.R
import com.example.leadcode.R.*
import com.example.leadcode.model.CombinedItem
import com.example.leadcode.model.combineAndShuffle
import com.example.leadcode.model.listOfTopics
import com.example.leadcode.presentation.viewmodels.ArticleScreenViewModel
import com.example.leadcode.presentation.viewmodels.InterviewQuestionsViewModel
import com.example.leadcode.presentation.viewmodels.MyQuestionsViewModel
import com.example.leadcode.presentation.viewmodels.SearchScreenViewModel
import com.example.leadcode.presentation.viewmodels.TutorialScreenViewModel
import com.example.leadcode.utils.composables.HeadingText
import com.example.leadcode.utils.composables.NormalText
import com.example.leadcode.utils.composables.StripedProgressIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontStyle
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier,
    searchScreenViewModel: SearchScreenViewModel,
    myQuestionsViewModel: MyQuestionsViewModel,
    articleScreenViewModel: ArticleScreenViewModel,
    tutorialScreenViewModel: TutorialScreenViewModel,
    interviewQuestionsViewModel: InterviewQuestionsViewModel,
    openScreen: (String) -> Unit
) {
    val search = searchScreenViewModel.searchState.collectAsStateWithLifecycle().value
    val inProgress = searchScreenViewModel.inProgress.collectAsState().value
    val querySearch = searchScreenViewModel.querySearchState.collectAsStateWithLifecycle().value

    var state by remember { mutableStateOf(0) }
    val titles = listOf("Articles", "Quiz", "Tutorials", "InterviewPrep")
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())




    if (search != null) {
        val excludedDatabases = listOf(
            "ArticlesDb",
            "TutorialsDb",
            "__realm_sync_65c08ccee78937a95d6d844c",
            "admin",
            "local",

            )
        val filteredDbColls = search.listOfDbColls.filter { dbColl ->
            !excludedDatabases.contains(dbColl.database)
        }
        val combinedList =
            combineAndShuffle(filteredDbColls, search.articleDocs, search.tutorialDocs)
        val configuration = LocalConfiguration.current

        val screenWidthDp = configuration.screenWidthDp.dp
        val screenHeightDp = configuration.screenHeightDp.dp


        Box(modifier = Modifier.fillMaxSize()) {

            var text by remember { mutableStateOf("") }
            var showTabs by remember { mutableStateOf(false) }
            var showBack by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxSize()){
                if(!showBack)
                AnimatedThreeDGradientBall(modifier = Modifier.fillMaxSize())

            }
            Column(
                modifier = Modifier.fillMaxSize(),  // Ensure TextField is placed properly
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Black), verticalAlignment = Alignment.CenterVertically
            ) {

                    if(!showBack)
                    OutlinedTextField(
                        modifier = Modifier
                            .background(Color.Black)
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                            .clickable {
                                // Handle click event here
                                showBack = true

                            },
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Search the topic you want") },
                        placeholder = {},

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            focusedLabelColor = Color(0xFFFF3D00),
                            focusedBorderColor = Color(0xFFFF3D00)
                        ),
                        enabled = showBack,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {
                            searchScreenViewModel.onQuerySearch(text)
                            showTabs = true
                        }),
                        trailingIcon = {
                            IconButton(onClick = {
                                searchScreenViewModel.onQuerySearch(query = text)
                                showTabs = true
                            }) {
                                Icon(Icons.Filled.Search, contentDescription = "Search")
                            }
                        }

                    )
                }

                if(showBack)
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        TopAppBar(modifier = Modifier.height(height = 64.dp),
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Black,
                                titleContentColor = Color(0xFFFF3D00),
                                scrolledContainerColor = Color.Black

                            ),
                            title = {
                                // Place your search TextField here
                                OutlinedTextField(
                                    modifier = Modifier
                                        .background(Color.Black)
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp)
                                        ,
                                    value = text,
                                    onValueChange = { text = it },
                                    label = { Text("Search the topic you want") },
                                    placeholder = {},
                                    textStyle = TextStyle(fontSize= 18.sp),

                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = Color.White,
                                        focusedLabelColor = Color(0xFFFF3D00),
                                        focusedBorderColor = Color(0xFFFF3D00)
                                    ),
                                    enabled = showBack,
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                    keyboardActions = KeyboardActions(onSearch = {
                                        searchScreenViewModel.onQuerySearch(text)
                                        showTabs = true
                                    }),
                                    trailingIcon = {
                                        IconButton(onClick = {
                                            searchScreenViewModel.onQuerySearch(query = text)
                                            showTabs = true
                                        }) {
                                            Icon(Icons.Filled.Search, contentDescription = "Search")
                                        }
                                    }

                                )
                            },
                            navigationIcon = {
                                IconButton(modifier = Modifier.height(64.dp),onClick = {
                                    showBack = !showBack
                                    showTabs = false
                                    text = ""
                                }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                        contentDescription = "Localized description",
                                        tint = Color(0xFFFF3D00)
                                    )
                                }
                            },
                            actions = {

                            },
                            scrollBehavior = scrollBehavior,

                            )
                    },
                    containerColor = Color.Black,

                    ) { innerPadding ->


                    AnimatedVisibility(
                        modifier = Modifier.padding(innerPadding),
                        visible = showTabs
                    ) {

                        Column {
                            TabRow(selectedTabIndex = state,
                                containerColor = Color.Transparent,
                                contentColor = Color.White,
                                indicator = { tabPositions ->
                                    // Draw a white indicator for the selected tab
                                    TabRowDefaults.PrimaryIndicator(
                                        Modifier.tabIndicatorOffset(tabPositions[state]),
                                        color = Color(0xFFFF3D00)
                                    )
                                },
                                divider = {}
                            ) {
                                titles.forEachIndexed { index, title ->
                                    Tab(
                                        text = {
                                            NormalText(
                                                data = title,
                                                fontSize = 13.sp,
                                                textAlign = TextAlign.Center
                                            )
                                        },
                                        selected = state == index,
                                        onClick = { state = index },
                                        unselectedContentColor = Color.DarkGray

                                    )
                                }
                            }
                            if (inProgress) {
                                Surface(modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding), color = Color.Black) {
                                    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                                        StripedProgressIndicator()

                                    }


                                }
                            }

                            when (state) {
                                0 ->
                                    if (querySearch?.article == true)
                                        Tab1Content(query = text)

                                1 ->
                                    if (querySearch?.question == true)
                                        Tab2Content(query = text)

                                2 ->
                                    if (querySearch?.tutorial == true)
                                            Tab3Content(query = text)

                                3 ->
                                    if (querySearch?.interview == true)
                                            Tab4Content(query = text)
                                // Add content for other tabs}
                            }

                        }
                    }
                }
            if (!showBack) {


                LazyVerticalGrid(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    columns = GridCells.Fixed(3)
                ) {


                    itemsIndexed(combinedList) { index, comb ->

                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .size(screenWidthDp / 3, screenHeightDp / 3)
                                .background(
                                    color = Color.Transparent
                                )
                                .border(0.5.dp, Color.White, RoundedCornerShape(1.dp))
                                .clickable {
                                    when (comb.database) {

                                        "ArticlesDb" -> comb.collection?.let {
                                            comb.doc?.let { it1 ->
                                                articleScreenViewModel.onArticleClick(
                                                    openScreen = openScreen,
                                                    topic = it,
                                                    title = it1
                                                )
                                                Log.d(
                                                    "artiravali",
                                                    "SearchScreen: ${comb.database}"
                                                )

                                            }
                                        }

                                        "TutorialsDb" -> comb.collection?.let {
                                            comb.doc?.let { it1 ->
                                                tutorialScreenViewModel.onTutorialClick(
                                                    openScreen = openScreen,
                                                    topic = it,
                                                    title = it1
                                                )
                                                Log.d(
                                                    "tutoravali",
                                                    "SearchScreen: ${comb.database}"
                                                )
                                            }
                                        }

                                        "InterviewQuestionsDb" -> comb.collection?.let {
                                            interviewQuestionsViewModel.onInterviewTopicClick(
                                                openScreen = openScreen,
                                                topic = it
                                            )
                                            Log.d(
                                                "interravali",
                                                "SearchScreen: ${comb.database}"
                                            )

                                        }

                                        else -> comb.collection?.let {
                                            comb.database?.let { it1 ->
                                                myQuestionsViewModel.onQuizSubTopicClick(
                                                    openScreen = openScreen,
                                                    topic = it1,
                                                    subTopic = it,
                                                    limit = 25
                                                )
                                                Log.d(
                                                    "mcqravali",
                                                    "SearchScreen: ${comb.database}"
                                                )

                                            }
                                        }
                                    }
                                },

                            ) {
                            if (comb.doc == null) {
                                if (comb.database == "InterviewQuestionsDb")
                                    NormalText(
                                        data = "Interview Questions on ${comb.collection}",
                                        modifier = Modifier.align(
                                            Alignment.Center
                                        ),
                                        textAlign = TextAlign.Center
                                    )
                                else
                                    NormalText(
                                        data = "Solve 25 MCQ's on ${comb.collection} of ${comb.database}",
                                        modifier = Modifier.align(
                                            Alignment.Center
                                        ),
                                        textAlign = TextAlign.Center
                                    )
                            } else
                                NormalText(
                                    data = comb.doc ?: comb.collection ?: comb.database
                                    ?: "Nothing",
                                    modifier = Modifier.align(
                                        Alignment.Center
                                    ),
                                    textAlign = TextAlign.Center
                                )

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .align(Alignment.BottomCenter),
                                shape = RoundedCornerShape(2.dp),
                                colors = CardDefaults.cardColors(Color.Black.copy(0.5f))
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {


                                    var text by remember {
                                        mutableStateOf("")
                                    }
                                    if (comb.database == "ArticlesDb" || comb.database == "TutorialsDb" || comb.database == "InterviewQuestionsDb") {
                                        text = comb.collection.toString()
                                    } else {
                                        text = comb.database.toString()
                                    }
                                    val myText = buildAnnotatedString {
                                        append(
                                            text
                                        )
                                        appendInlineContent(
                                            "icon1",
                                            "[icon]"
                                        ) // Placeholder for the first icon
                                        // Placeholder for the second icon

                                    }
                                    val topicAndIcon =
                                        listOfTopics.find { it.topic == text }
                                    topicAndIcon.let { it ->
                                        Text(text = myText, inlineContent = mapOf(
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
                                            }
                                        ), color = Color.White,
                                            fontSize = 12.sp,
                                            fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
                                            style = TextStyle(lineHeight = 18.sp),
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth()
                                        )
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
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(), color = Color.Black) {
            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                StripedProgressIndicator()

            }


        }
    }



}

@Composable
fun Tab4Content(query: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {

            },
            Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
            colors = ButtonDefaults.elevatedButtonColors(Color.DarkGray),
            shape = RoundedCornerShape(8.dp),

//                        elevation = ButtonDefaults.elevatedButtonColors(),

        ) {
            HeadingText(data = query, textAlign = TextAlign.Left)
        }
    }
}

@Composable
fun Tab3Content(query: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {

            },
            Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
            colors = ButtonDefaults.elevatedButtonColors(Color.DarkGray),
            shape = RoundedCornerShape(8.dp),
//                        elevation = ButtonDefaults.elevatedButtonColors(),

        ) {
            HeadingText(data = query, textAlign = TextAlign.Left)
        }    }
}

@Composable
fun Tab2Content(query: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {

            },
            Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
            colors = ButtonDefaults.elevatedButtonColors(Color.DarkGray),
            shape = RoundedCornerShape(8.dp),
//                        elevation = ButtonDefaults.elevatedButtonColors(),

        ) {
            HeadingText(data = query, textAlign = TextAlign.Left)
        }    }
}

@Composable
fun Tab1Content(query: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {

            },
            Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart),
            colors = ButtonDefaults.elevatedButtonColors(Color.DarkGray),
            shape = RoundedCornerShape(8.dp),
//                        elevation = ButtonDefaults.elevatedButtonColors(),

        ) {
            HeadingText(data = query, textAlign = TextAlign.Left)
        }    }
}


@Composable
fun ScrollWithLoadingIndicatorPage() {
    val buffer = 1
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Example items
    var items by remember { mutableStateOf(generateInitialItems()) }
    var isLoading by remember { mutableStateOf(false) }

    // Check if scrolled beyond the last item
    val isScrolledBeyondLastItem by remember {

        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - buffer




        }
    }
//    Log.d("scroll", "ScrollWithLoadingIndicatorPage: $layoutInfo")
//    Log.d("scroll", "ScrollWithLoadingIndicatorPage: $totalItems")
//    Log.d("scroll", "ScrollWithLoadingIndicatorPage: $lastVisibleItemIndex")
//    Log.d("scroll", "ScrollWithLoadingIndicatorPage: $endOfListReached")
//    Log.d("scroll", "ScrollWithLoadingIndicatorPage: $isScrolledBeyondLastItem")

    // Load more data when scrolled to the end
    LaunchedEffect(isScrolledBeyondLastItem) {
        if (isScrolledBeyondLastItem && !isLoading) {
            isLoading = true
            // Simulate loading data
            coroutineScope.launch {

                // Here you would perform your data fetching and update the items list
                // For demonstration, we just append more items
                val newItems = generateNewItems()
                items = items + newItems
                isLoading = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // LazyColumn displaying items
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(items) { index, item ->
                Text(text = item, modifier = Modifier.padding(8.dp))
            }

            // Add an empty item to show progress indicator when scrolling to end
            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

// Dummy function to generate initial items
fun generateInitialItems(): List<String> {
    return List(10) { "Item ${it + 1}" }
}

// Dummy function to generate new items
fun generateNewItems(): List<String> {
    return List(20) { "New Item ${it + 1}" }
}

@Preview(showBackground = true)
@Composable
fun PreviewScrollWithLoadingIndicatorPage() {
    ScrollWithLoadingIndicatorPage()
}


//@Preview
@Composable
fun Refresh(modifier: Modifier = Modifier) {

    val buffer = 1 // load more when scroll reaches last n item, where n >= 1

        val listState = rememberLazyListState()

        // observe list scrolling
        val reachedBottom by remember{derivedStateOf {
        // Calculate if the last visible item is at or beyond the end
        val layoutInfo = listState.layoutInfo
        val totalItems = layoutInfo.totalItemsCount
        val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
        val endOfListReached = lastVisibleItemIndex >= totalItems - 1
            endOfListReached && (layoutInfo.visibleItemsInfo.lastOrNull()?.offset
                ?: 0) >= layoutInfo.viewportEndOffset
    }}

        // load more if scrolled to bottom



        Box(modifier = Modifier.fillMaxSize()) {
            // display our list
            LazyColumn(state = listState, modifier = Modifier.align(Alignment.Center)) {
                items(5) { card ->
                    Text("Android $card", color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                }
                if (reachedBottom)
                item {

                        CircularProgressIndicator()
                }
            }
        }



}







@Composable
fun AnimatedThreeDGradientBall(modifier: Modifier = Modifier) {
//     InfiniteTransition for continuous animation

    val infiniteTransition = rememberInfiniteTransition()

    // Animated position (for movement)
    val animatedOffset = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "movement"
    )

    // Animated size (for shape changes)
    val animatedSize = infiniteTransition.animateFloat(
        initialValue = 24f,
        targetValue = 36f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "shape"
    )

    // Animated radius (for shape morphing)
    val animatedRadius = infiniteTransition.animateFloat(
        initialValue = 60f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "shape morph"
    )


    Box(modifier = Modifier // Apply padding from the top
        .fillMaxSize() ) {
        Canvas(modifier = modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val centerX = canvasWidth * animatedOffset.value
            val centerY =
                canvasHeight / 2 + (sin(animatedOffset.value * 2 * Math.PI) * 200).toFloat()

            val radius = animatedRadius.value * animatedSize.value

            // 1. Background Linear Gradient
            drawRect(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFF3D00),
                        Color(0xFF2196F3)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(canvasWidth, canvasHeight)
                ),
                size = size
            )

            // 2. Radial Gradient for 3D Effect
            drawRoundRect(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x80FFFFFF), Color.Transparent),
                    center = Offset(centerX, centerY),
                    radius = radius
                ),
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(2 * radius, 2 * radius),
                cornerRadius = CornerRadius(radius / 2, radius / 2)
            )

            // 3. Shading with Another Linear Gradient
            drawRoundRect(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Transparent, Color(0x80000000)),
                    start = Offset(centerX - radius, centerY + radius),
                    end = Offset(centerX + radius, centerY - radius)
                ),
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(2 * radius, 2 * radius),
                cornerRadius = CornerRadius(radius / 2, radius / 2)
            )
        }
}
}


