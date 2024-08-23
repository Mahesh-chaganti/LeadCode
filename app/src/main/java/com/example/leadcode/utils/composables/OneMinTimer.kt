package com.example.leadcode.utils.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun OneMinuteTimer() {
    val initialValue = 1f
    val totalTime = 60L * 1000L
    var value by remember {
        mutableStateOf(initialValue)
    }
    var currentTime by remember {
        mutableStateOf(totalTime)
    }
    var isTimer by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = currentTime, key2 = isTimer) {
        if(currentTime > 0 && isTimer) {
            delay(100L)
            currentTime -= 100L
            value = currentTime / totalTime.toFloat()
        }
    }
    Canvas(modifier = Modifier.size(150.dp)) {
        drawCircle(color = Color.White, radius = 100f, center = Offset(200f, 200f))
        drawCircle(color = Color.Black, radius = 50f, center = Offset(200f, 200f))

        drawArc(
            color = Color.Red,
            startAngle = 270f,
            sweepAngle = -360f * value,
            useCenter = false,
            size = Size(size.width.toFloat(), size.height.toFloat()),
            style = Stroke(5.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Preview
@Composable
fun OneMinPreview() {
    OneMinuteTimer()
}