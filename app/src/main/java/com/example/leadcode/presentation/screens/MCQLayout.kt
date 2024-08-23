package com.example.leadcode.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.leadcode.R
import com.example.leadcode.model.MCQuestion
import com.example.leadcode.model.Question
import com.example.leadcode.utils.composables.NormalText
import com.example.leadcode.utils.composables.RadioButtonGroup
import kotlinx.coroutines.delay




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
            containerColor = if (isSelected) Color.Cyan else Color.Blue,
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
                    .width(14.dp),
                painter = painterResource(id = (R.drawable.outline_circle_24)),
                contentDescription = "Add",
                tint = if (isSelected) Color.Cyan else Color.Black

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






@Composable
fun UsualButton(modifier: Modifier = Modifier,data : String, color: Color = Color.White, onClick:()->Unit,) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            1.dp,
            Color.Black
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.Black
        )

    ){
        Text(text = data)
    }
}
@Preview
@Composable
private fun MCQPrev() {
//MCQLayout()
}