package com.example.leadcode

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomRadioButton(
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedColor: Color = Color.Cyan,
    unselectedColor: Color = Color.White,
    data : String
) {

    Button(
        modifier = Modifier,
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(
            1.dp,
            Color.Black
        ),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)

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
                painter = painterResource(id =  if(isSelected) R.drawable.round_check_circle_24 else (R.drawable.outline_circle_24)),
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

//    Box(
//        modifier = Modifier
//            .size(24.dp)
//            .clip(CircleShape)
//            .background(if (selected) selectedColor else unselectedColor.copy(alpha = 0.6f))
//            .clickable(onClick = onClick)
//            .padding(4.dp)
//    ) {
//        if (selected) {
//            Box(
//                modifier = Modifier
//                    .size(12.dp)
//                    .clip(CircleShape)
//                    .background(Color.White)
//                    .align(Alignment.Center)
//            )
//        }
//    }
    }

}

@Composable
fun RadioButtonGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column {
        options.forEach { option ->

                CustomRadioButton(
                    isSelected = selectedOption == option,
                    onClick = { onOptionSelected(option) },
                    data = option
                )


        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomRadioButtonPreview() {
    var selectedOption by remember { mutableStateOf("") }
    RadioButtonGroup(
        options = listOf("Option 1", "Option 2", "Option 3"),
        selectedOption = selectedOption,
        onOptionSelected = { selectedOption = it }
    )
}
