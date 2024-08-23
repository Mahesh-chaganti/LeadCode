package com.example.leadcode.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leadcode.model.BottomNavScreen
import com.example.leadcode.model.SwipeableScreens
import com.example.leadcode.presentation.screens.ArticleLayoutScreen
import com.example.leadcode.presentation.screens.ArticleScreen
import com.example.leadcode.presentation.screens.DummyScreen
import com.example.leadcode.presentation.screens.InterviewLayoutScreen
import com.example.leadcode.presentation.screens.InterviewPrep
import com.example.leadcode.presentation.screens.InterviewPrepScreen
import com.example.leadcode.presentation.screens.MainPagerScreen
import com.example.leadcode.presentation.screens.QuizLayoutScreen
import com.example.leadcode.presentation.screens.QuizScreen
import com.example.leadcode.presentation.screens.QuizSubTopicsScreen
import com.example.leadcode.presentation.screens.SearchScreen
import com.example.leadcode.presentation.screens.TutorialLayoutScreen
import com.example.leadcode.presentation.screens.TutorialScreen
import com.example.leadcode.presentation.screens.Tutorials
import com.example.leadcode.presentation.viewmodels.ArticleScreenViewModel
import com.example.leadcode.presentation.viewmodels.InterviewQuestionsViewModel
import com.example.leadcode.presentation.viewmodels.MyQuestionsViewModel
import com.example.leadcode.presentation.viewmodels.SearchScreenViewModel
import com.example.leadcode.presentation.viewmodels.TutorialScreenViewModel

@Composable
fun NavigationScreen(
    modifier : Modifier,
    navController: NavHostController,
    questionsViewModel: MyQuestionsViewModel,
    articleScreenViewModel: ArticleScreenViewModel,
    tutorialScreenViewModel: TutorialScreenViewModel,
    interviewPrepViewModel: InterviewQuestionsViewModel,
    searchScreenViewModel: SearchScreenViewModel
) {


    fun navigateTo(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }

    fun onBack() {
        navController.popBackStack()
    }

//
    NavHost(navController = navController, startDestination = BottomNavScreen.Search.route){
        composable(BottomNavScreen.Quiz.route) {
            QuizScreen(modifier=modifier, searchScreenViewModel = searchScreenViewModel,viewModel = questionsViewModel) { route -> navigateTo(route = route) }
        }
        composable(BottomNavScreen.Articles.route) {
            ArticleScreen(modifier = modifier,viewModel = articleScreenViewModel, searchScreenViewModel = searchScreenViewModel){ route -> navigateTo(route = route) }

        }
        composable(BottomNavScreen.Search.route) {
            SearchScreen(modifier = modifier,
                searchScreenViewModel = searchScreenViewModel,
                myQuestionsViewModel = questionsViewModel,
                interviewQuestionsViewModel = interviewPrepViewModel,
                tutorialScreenViewModel = tutorialScreenViewModel,
                articleScreenViewModel = articleScreenViewModel,
                openScreen = {route -> navigateTo(route)})
        }

        composable(BottomNavScreen.Tutorials.route) {
            TutorialScreen(modifier = modifier,tutorialScreenViewModel, searchScreenViewModel = searchScreenViewModel,openScreen = {route -> navigateTo(route = route)})
        }

        composable(BottomNavScreen.InterviewPrep.route) {
            InterviewPrepScreen(modifier = modifier, searchScreenViewModel = searchScreenViewModel,
                openScreen = {route -> navigateTo(route)},interviewPrepViewModel = interviewPrepViewModel)
        }
        composable("QuizSubTopicsScreen"){
            QuizSubTopicsScreen(
                viewModel = questionsViewModel,
                openScreen = {route -> navigateTo(route)}, goback = {onBack()}
            )
        }
        composable("QuizLayoutScreen"){
            QuizLayoutScreen(viewModel = questionsViewModel, goback = {onBack()})
        }

        composable("ArticleLayoutScreen"){
            ArticleLayoutScreen(viewModel = articleScreenViewModel,goback = {onBack()})
        }
        composable("TutorialLayoutScreen"){
            TutorialLayoutScreen(viewModel = tutorialScreenViewModel, goback = {onBack()})
        }
        composable("InterviewLayoutScreen"){
            InterviewLayoutScreen(viewModel = interviewPrepViewModel, goback = {onBack()})
        }

    }
}



//val listOfSwipeableScreens = listOf(
//        SwipeableScreens(
//            "Quiz", Icons.Outlined.CheckCircle
//        ) {
//            QuizScreen(viewModel = questionsViewModel) { route -> navigateTo(route = route) }
//        },
//        SwipeableScreens(
//            "Articles", Icons.Outlined.ShoppingCart, content = {
//                ArticleScreen(viewModel = articleScreenViewModel){ route -> navigateTo(route = route) }
//
//            }
//        ),
//        SwipeableScreens(
//            "Search", Icons.Outlined.Search, content = {
//                SearchScreen()
//            }
//        ),
//        SwipeableScreens(
//            "Tutorial", Icons.Outlined.Build, content = {
//                TutorialScreen(tutorialScreenViewModel, openScreen = {route -> navigateTo(route = route)})
//            }
//        ),
//        SwipeableScreens(
//            "Interview", Icons.Outlined.AccountCircle, content = {
//                InterviewPrepScreen()
//            }
//        ))
