package com.example.leadcode

import android.graphics.fonts.FontFamily
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val topics = listOf(
        "Android",
        "AWS",
        "Azure",
        "GCP",
        "Web Development",
        "DSA",
        "Machine Learning",
        "Deep Learning",
        "Backend",
        "Frontend",
        "Blockchain",
        "Web3",
        "Angular",
        "Data Engineering",
        "Data Analysis",
        "Firebase",
        "Javascript",
        "Node.js",
        "Mongodb",
        "React"
    )
    val color: Modifier
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Row {


                Text(text = "LeadCode", fontWeight = FontWeight.ExtraBold, fontSize = 36.sp
                ,style = TextStyle(
                        brush = Brush.linearGradient(colors = listOf( Color(0xFFFF5722),Color(0xFF03A9F4))),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 36.sp
                    ),

                    fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                )

            }
            },
                navigationIcon = { Image(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription ="back" ) },
                actions = { Image(imageVector = Icons.Default.MoreVert, contentDescription = "Settings")})
        }) {
        Column(modifier = Modifier.padding(10.dp)) {


            Text(
                modifier = Modifier.padding(top = 60.dp),
                text = "Select the topic you want",
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold
            )
            LazyHorizontalStaggeredGrid(
                rows = StaggeredGridCells.Adaptive(minSize = 50.dp), modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(it)
                    .wrapContentSize()
            ) {
                itemsIndexed(topics) { index, item ->
                    TopicButton(
                        modifier = Modifier
                            .wrapContentHeight(Alignment.CenterVertically)
                            .wrapContentWidth()
                            .padding(2.dp),
                        data = item
                    )
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
            Button(onClick = { /*TODO*/ },
                shape = RoundedCornerShape(48.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            , colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {


                Text(
                    text = "Submit",
                    fontSize = 24.sp

                )
            }
            }

    }

}


@Composable
fun TopicButton(modifier: Modifier, data: String) {

    Button(
        modifier = modifier,
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            1.dp,
            Color.Black
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )

    ) {

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(end = 2.dp),
                text = data,
                fontWeight = FontWeight.Black,
                fontSize = 16.sp
            )

            Icon(
                modifier = Modifier
                    .height(16.dp)
                    .width(14.dp)
                    .padding(start = 4.dp),
                imageVector = Icons.Default.Add,
                contentDescription = "Add"
            )
        }

    }

}

@Preview
@Composable
private fun AllPreview() {
    MainScreen()
}