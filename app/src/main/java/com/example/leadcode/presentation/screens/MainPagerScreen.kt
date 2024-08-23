package com.example.leadcode.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.leadcode.model.BottomNavScreen
import com.example.leadcode.model.SwipeableScreens
import com.example.leadcode.model.bottomNavItems
import com.example.leadcode.navigation.NavigationScreen
import com.example.leadcode.utils.composables.HeadingText
import com.example.leadcode.utils.composables.NormalText
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainPagerScreen(modifier: Modifier = Modifier,
                    listOfSwipeableScreens: List<SwipeableScreens>) {
    val scope = rememberCoroutineScope()


    val pageState = rememberPagerState(0, pageCount = { listOfSwipeableScreens.size})

    Scaffold(

        bottomBar = {
            BottomAppBar(
                contentColor = Color.Black,
                content = {
                    listOfSwipeableScreens.forEachIndexed { i, page ->
                        NavigationBarItem(
                            icon = {
                                Icon(imageVector = page.icon, "Page $i")
                            },
                            // here's the trick. the selected tab is based
                            // on HorizontalPager state.
                            selected = i == pageState.currentPage,
                            onClick = {
                                // When a tab is selected,
                                // the page is updated

                                scope.launch {
                                    pageState.animateScrollToPage(i)
                                }
                            },
                            colors = NavigationBarItemColors(Color.White,Color.Gray,Color.Gray,Color.White,Color.Gray,Color.Gray,Color.Gray),

                            label = { Text(text = page.label) }
                        )
                    }
                }
            )
        },
    ) {

        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            state = pageState,
            userScrollEnabled = true
        ) { page ->
            listOfSwipeableScreens[page].content()
            val scope = rememberCoroutineScope()
            LaunchedEffect(key1 = null) {
                scope.launch {


//                    viewModel?.currentUser?.email?.let { firestoreViewModel?.getMyUserProfile(it) }
//                    firestoreViewModel?.getAllOtherUserProfiles()
//                    spotifyApiViewModel?.getAccessToken(authHeader)

                }
            }
        }
    }
}


@Composable
fun BottomNav(modifier: Modifier = Modifier) {

}

@Composable
fun Quiz(modifier: Modifier = Modifier) {
    HeadingText(data = "Quiz", color = Color.White)
}

@Composable
fun Articles(modifier: Modifier = Modifier,) {
    HeadingText(data = "Articles",color = Color.Red)
}

@Composable
fun Search(modifier: Modifier = Modifier,) {
    HeadingText(data = "Search",color = Color.Cyan)
}

@Composable
fun Tutorials(modifier: Modifier = Modifier,) {
    HeadingText(data = "Tutorials",color = Color.Green)
}

@Composable
fun InterviewPrep(modifier: Modifier = Modifier,) {
    HeadingText(data = "InterviewPrep",color = Color.Green)
}








@Preview
@Composable
private fun PagerReview() {
//    MainPagerScreen()
    BottomNav()
}