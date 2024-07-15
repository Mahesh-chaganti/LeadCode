package com.example.leadcode

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.runtime.*
import kotlinx.coroutines.delay


@Composable
fun MCQLayout(modifier: Modifier = Modifier) {
    val totalTime: Long = 60L * 1000L
    var timeRemaining by remember { mutableStateOf(totalTime) }
    var isRunning by remember { mutableStateOf(false) }
    val radioOptions = listOf("Hyper text mark land","Hyper text markup language","Hyper text mark lan","Hyper text mark lambda")
    var selectedOption = remember{ mutableStateOf( "")}
    LaunchedEffect(key1 = isRunning) {
        if (isRunning) {
            while (timeRemaining > 0) {
                delay(1000L)
                timeRemaining -= 1000L
            }
            isRunning = false
        }
    }

    val formattedTime = formatTime(timeRemaining)
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {

    }
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier.fillMaxSize()) {


            Card(
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
                    .size(width = 180.dp, height = 50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(Color.Black)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                    UsualButton(data = formattedTime) {
                        isRunning = !isRunning
                    }




                }
            }
            Row(modifier = Modifier.padding(24.dp), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                UsualButton(data = "Web Development") {
                    //TODO//
                }
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Right",
                    tint = Color.White)
                UsualButton(data = "HTML") {
                    //TODO//
                }
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .background(Color.Black)
                    .padding(8.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .height(500.dp),
                    verticalArrangement = Arrangement.Center) {


                        Row(modifier = Modifier.padding(bottom = 40.dp)) {


                            Text(
                                text = "1.  ",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                            )
                            Text(
                                text = "What is the fullform of HTML?",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                            )
                        }


                        Column(modifier = Modifier.padding(start = 24.dp)) {


                            Text(
                                modifier = Modifier.padding(start = 24.dp),
                                text = "select one Answer",
                                color = Color.Gray,
                                fontSize = 16.sp,
                                fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                            )

                            RadioButtonGroup(
                                options = radioOptions,
                                selectedOption = selectedOption.value,
                                onOptionSelected = { selectedOption.value = it },

                            )

                        }

                }
            }
            Button(onClick = { /*TODO*/ },
                shape = RoundedCornerShape(48.dp),
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                , colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {


                Text(
                    text = "Submit",
                    fontSize = 18.sp,
                    color = Color.Black

                )
            }

        }
    }
}

//@Composable
//fun ChoiceButtonGroup(modifier: Modifier = Modifier, radioOptions: List<String>, isSelected: Boolean, selectedOption: (String) -> Unit) {
//
//
//    radioOptions.forEach { text ->
//        ChoiceButton(
//            data = text,
//            isSelected = text == selectedOption,
//            onSelect = { selectedOption( text) }
//        )
//    }
//}
@Composable
fun ChoiceButton(data: String, isSelected: Boolean, onSelect : ()-> Unit ) {

    Button(
        modifier = Modifier,
        onClick = { onSelect },
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            1.dp,
            Color.Black
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if(isSelected) Color.Cyan else Color.Blue,
            contentColor = Color.Black
        )

    ) {

        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .height(16.dp)
                    .width(14.dp)
                    ,
                painter = painterResource(id = (R.drawable.outline_circle_24) ) ,
                contentDescription = "Add",
                tint = if(isSelected) Color.Cyan else Color.Black

            )


            Box(modifier = Modifier.padding(start = 12.dp)) {
                Text(
                    modifier = Modifier.padding(end = 2.dp),
                    text = data,
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp
                )


            }
        }
    }
}



private fun formatTime(timeInMillis: Long): String {
    val totalSeconds = timeInMillis / 1000
    val minutes = totalSeconds / 60
    val remainingSeconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

@Composable
fun UsualButton(modifier: Modifier = Modifier,data : String, onClick:()->Unit) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            1.dp,
            Color.Black
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )

    ){
        Text(text = data)
    }
}
@Preview
@Composable
private fun MCQPrev() {
MCQLayout()
}