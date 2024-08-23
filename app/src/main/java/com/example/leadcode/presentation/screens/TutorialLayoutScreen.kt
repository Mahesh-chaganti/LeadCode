package com.example.leadcode.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.leadcode.presentation.viewmodels.TutorialScreenViewModel
import com.example.leadcode.utils.composables.HeadingText
import com.example.leadcode.utils.composables.StripedProgressIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialLayoutScreen(
    viewModel: TutorialScreenViewModel,
    goback: ()-> Unit

) {
    val tutorials = viewModel.tutorialsState.collectAsStateWithLifecycle().value?.tutorials
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val inProgress = viewModel.inProgress.collectAsState().value

    Surface( modifier = Modifier.fillMaxSize(), color = Color.Black) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                LargeTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black,
                        titleContentColor = Color(0xFFFF3D00),
                        scrolledContainerColor = Color.Black

                    ),
                    title = {
                        if (tutorials != null) {
                            tutorials.title?.let {
                                HeadingText(modifier = Modifier,data = it ,fontSize = 18.sp)
                            }
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
            if (tutorials != null) {
                OneTutorialLayout(
                    modifier= Modifier.padding(innerPadding),
                    tutorial = tutorials
                )
            }
            if (inProgress) {


                Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
                    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                        StripedProgressIndicator()

                    }


                }
            }
        }
    }


}


