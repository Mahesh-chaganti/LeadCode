package com.example.leadcode.utils.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun HeadingText(modifier: Modifier = Modifier, data: String, fontSize: TextUnit = 24.sp
                , fontWeight: FontWeight = FontWeight.ExtraBold
                , textAlign: TextAlign = TextAlign.Center,
                color : Color = Color.White,
                lineHeight: TextUnit = 18.sp
) {
    Text(
        text = data,
        color = color,
        fontSize = fontSize,
        fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
        fontWeight = fontWeight,
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
        style = TextStyle(lineHeight = lineHeight)
    )
}
@Composable
fun NormalText(modifier: Modifier = Modifier, data: String, fontSize: TextUnit = 12.sp
               , fontWeight: FontWeight =  FontWeight.ExtraLight
               , textAlign: TextAlign = TextAlign.Justify,
               color : Color = Color.White,
               lineHeight: TextUnit = 18.sp
) {
    Text(
        text = data,
        color = color,
        fontSize = fontSize,
        fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
        fontWeight = fontWeight,
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
        style = TextStyle(lineHeight = lineHeight)
    )
}
