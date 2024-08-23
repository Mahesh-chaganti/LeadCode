package com.example.leadcode.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.leadcode.R

sealed class BottomNavScreen(val route: String, @StringRes val resourceId: Int, val icon: Int) {
        object Quiz : BottomNavScreen("quiz", R.string.Home, R.drawable.quizicon)
        object Articles : BottomNavScreen("articles", R.string.Articles, R.drawable.articleicon,)
        object Search : BottomNavScreen("search", R.string.Search,R.drawable.searchicon,)
        object Tutorials : BottomNavScreen("tutorials", R.string.Tutorials,R.drawable.tutorialicon,)
        object InterviewPrep : BottomNavScreen("interviewPrep", R.string.InterviewPrep,R.drawable.interviewicon)


}

val bottomNavItems = listOf(
    BottomNavScreen.Quiz,
    BottomNavScreen.Articles,
    BottomNavScreen.Search,
    BottomNavScreen.Tutorials,
    BottomNavScreen.InterviewPrep
)
