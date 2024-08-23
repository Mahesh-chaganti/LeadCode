package com.example.leadcode.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class SwipeableScreens(val label: String, val icon: ImageVector, val content: @Composable () -> Unit)
