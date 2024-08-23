package com.example.leadcode.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.leadcode.model.bottomNavItems
import com.example.leadcode.utils.composables.NormalText

@Composable
fun SearchLayoutScreen(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    var showTabs by remember { mutableStateOf(false) }

    Column {


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            value = text,
            onValueChange = { text = it },
            label = { Text("Search the topic you want") },
            placeholder = {},
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                focusedLabelColor = Color(0xFFFF3D00),
                focusedBorderColor = Color(0xFFFF3D00)
            ),
            trailingIcon = {
                IconButton(onClick = {
                    showTabs = text.isNotBlank()
                }) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            }
        )
        // Tabs
        var state by remember { mutableStateOf(0) }
        val titles = listOf("Articles", "Quiz", "Tutorials", "InterviewPrep")
        AnimatedVisibility(visible = showTabs) {

            Column {
                TabRow(
                    selectedTabIndex = state,
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
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Fancy tab ${state + 1} selected",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }


    }
}

@Preview
@Composable
private fun SearchLayoutPrev() {
    SearchLayoutScreen()
}


// Tabs


// Content area for the selected tab

