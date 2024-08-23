package com.example.leadcode

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ScaffoldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.RadioButton
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.BeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.leadcode.model.InterviewQuestions
import com.example.leadcode.model.bottomNavItems
import com.example.leadcode.navigation.NavigationScreen
import com.example.leadcode.presentation.screens.ArticleScreen
import com.example.leadcode.presentation.screens.MainPagerScreen
import com.example.leadcode.presentation.screens.QuizScreen
import com.example.leadcode.presentation.screens.QuizSubTopicsScreen
import com.example.leadcode.presentation.screens.UsualButton
import com.example.leadcode.presentation.viewmodels.ArticleScreenViewModel
import com.example.leadcode.presentation.viewmodels.InterviewQuestionsViewModel
import com.example.leadcode.presentation.viewmodels.MyQuestionsViewModel
import com.example.leadcode.presentation.viewmodels.SearchScreenViewModel
import com.example.leadcode.presentation.viewmodels.TutorialScreenViewModel
import com.example.leadcode.ui.theme.LeadCodeTheme
import com.example.leadcode.utils.composables.HeadingText
import com.example.leadcode.utils.composables.NormalText
import com.example.leadcode.utils.composables.OneMinuteTimer
import com.example.leadcode.utils.composables.StripedProgressIndicator
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val questionsViewModel by  viewModels<MyQuestionsViewModel>()
    private val articlesViewModel by  viewModels<ArticleScreenViewModel>()
    private val tutorialsViewModel by  viewModels<TutorialScreenViewModel>()
    private val interviewPrepViewModel by  viewModels<InterviewQuestionsViewModel>()
    private val searchScreenViewModel by  viewModels<SearchScreenViewModel>()


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeadCodeTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val navsList = listOf("quiz", "articles", "search","tutorials", "interviewPrep")
                if (navsList.contains(currentDestination?.route)) {


                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.Black
                    ) {
                        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
                            rememberTopAppBarState()
                        )


                        Scaffold(
                            modifier =if(currentDestination?.route != "search")  Modifier.nestedScroll(scrollBehavior.nestedScrollConnection) else Modifier,
                            contentWindowInsets = ScaffoldDefaults.contentWindowInsets,

                                topBar =
                                    {
                                        if(currentDestination?.route != "search") {

                                        bottomNavItems.forEach { screen ->


                                            MediumTopAppBar(
                                                colors = TopAppBarDefaults.topAppBarColors(
                                                    containerColor = Color.Black,
                                                    titleContentColor = Color(0xFFFF3D00),
                                                    scrolledContainerColor = Color.Black

                                                ),
                                                title = {
                                                    HeadingText(
                                                        data = when (currentDestination?.route) {
                                                            "quiz" -> "Home"
                                                            "articles" -> "Articles"
                                                            "tutorials" -> "Tutorials"
                                                            else -> "InterviewPrep" // Default title
                                                        }
                                                    )


                                                },
                                                navigationIcon = {

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
                                        }

                                    }
                                },
                            bottomBar = {
                                BottomNavigation(backgroundColor = Color.Black) {

                                    bottomNavItems.forEach { screen ->
                                        BottomNavigationItem(
                                            icon = {
                                                Icon(
                                                    painter  = painterResource(id = screen.icon),
                                                    contentDescription = "${screen.resourceId}",
                                                    tint = Color.White
                                                )
                                            },
                                            label = { NormalText(data = stringResource(screen.resourceId)) },
                                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                            onClick = {
                                                navController.navigate(screen.route) {

                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }

                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            },
                                            selectedContentColor = Color(0xFFFF3D00),
                                            alwaysShowLabel = false
                                        )
                                    }
                                }
                            },
                            containerColor = Color.Black,

                            ) { innerPadding ->

                            NavigationScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                questionsViewModel = questionsViewModel,
                                articleScreenViewModel = articlesViewModel,
                                tutorialScreenViewModel = tutorialsViewModel,
                                interviewPrepViewModel = interviewPrepViewModel,
                                searchScreenViewModel = searchScreenViewModel
                            )

                        }

                    }
                }
                else{
                    NavigationScreen(
                        modifier = Modifier.padding(),
                        navController = navController,
                        questionsViewModel = questionsViewModel,
                        articleScreenViewModel = articlesViewModel,
                        tutorialScreenViewModel = tutorialsViewModel,
                        interviewPrepViewModel = interviewPrepViewModel,
                        searchScreenViewModel = searchScreenViewModel
                    )
                }
                if(!isInternetAvailable(LocalContext.current.applicationContext)){
                    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
                        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                            Column {


                                HeadingText(data = "No internet connection")
                                UsualButton(data = "Retry") {

                                }
                            }

                        }


                    }
                }
            }

        }
    }
}
fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities =
        connectivityManager.getNetworkCapabilities(network) ?: return false

    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}



@Composable
fun WebViewScreen() {

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()

                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
            }
        },
        update = { webView ->
            webView.loadUrl("https://404ilejltl.execute-api.ap-southeast-2.amazonaws.com/test/hello")
        }
    )
}
@Composable
fun FirstScreen() {
    Column(Modifier.fillMaxSize()) {


        var select1 by remember { mutableStateOf(false) }

        Text(text = "What is the native language for coding in android app development")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = select1, onClick = { select1 = !select1 })
            Text("Kotlin")
        }
        Row(verticalAlignment = Alignment.CenterVertically){


            RadioButton(selected = select1, onClick = { select1 = !select1 })
            Text("C++")
        }
        Row(verticalAlignment = Alignment.CenterVertically){


            RadioButton(selected = select1, onClick = { select1 = !select1 })
            Text("Python")
        }
        Row(verticalAlignment = Alignment.CenterVertically){


            RadioButton(selected = select1, onClick = { select1 = !select1 })
            Text("C#")
        }
        OneMinuteTimer()

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LeadCodeTheme {
    App()
    }
}

@Composable
fun App() {
    Surface(modifier = Modifier.fillMaxSize()) {
        RadioButtonSample()
    }
}

@Composable
fun RadioButtonSample() {
    // List of options
    val radioOptions = listOf("Option 1", "Option 2", "Option 3")

    // State to store the currently selected option
    val selectedOption = remember { mutableStateOf("") }

    Column(Modifier.padding(PaddingValues(16.dp))) {
        radioOptions.forEach { text ->
            RadioButtonWithLabel(
                label = text,
                isSelected = text == selectedOption.value,
                onSelect = { selectedOption.value = text }
            )
        }
    }
}

@Composable
fun RadioButtonWithLabel(
    label: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(modifier = Modifier
        .padding(vertical = 8.dp)
        .clickable { onSelect() }, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = isSelected,
            onClick = { onSelect() }
        )
        Text(
            text = label,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}


