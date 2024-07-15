package com.example.leadcode

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import com.example.clientsdk.MyQuestionsAPIClient
import com.example.leadcode.ui.theme.LeadCodeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeadCodeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
//                    WebViewScreen()
//
//                    val viewModel = hiltViewModel<MyQuestionsViewModel>()
////                     Now you can use the ViewModel to access data and trigger actions
//                    val questions = viewModel.fetchQuestions() // Assuming 'questions' is a StateFlow or LiveData in your ViewModel
//
//
//
//                    Text(text = questions)

                MCQLayout()
                }
            }
        }
    }
}

@Composable
fun WebViewScreen() {

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()

                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
            }
        },
        update = { webView ->
            webView.loadUrl("https://404ilejltl.execute-api.ap-southeast-2.amazonaws.com/test/hello")
        }
    )
}
@Composable
fun FirstScreen() {
    Column(Modifier.fillMaxSize()) {


        var select1 by remember { mutableStateOf(false) }

        Text(text = "What is the native language for coding in android app development")
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = select1, onClick = { select1 = !select1 })
            Text("Kotlin")
        }
        Row(verticalAlignment = Alignment.CenterVertically){


            RadioButton(selected = select1, onClick = { select1 = !select1 })
            Text("C++")
        }
        Row(verticalAlignment = Alignment.CenterVertically){


            RadioButton(selected = select1, onClick = { select1 = !select1 })
            Text("Python")
        }
        Row(verticalAlignment = Alignment.CenterVertically){


            RadioButton(selected = select1, onClick = { select1 = !select1 })
            Text("C#")
        }
        OneMinuteTimer()

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LeadCodeTheme {
    App()
    }
}

@Composable
fun App() {
    Surface(modifier = Modifier.fillMaxSize()) {
        RadioButtonSample()
    }
}

@Composable
fun RadioButtonSample() {
    // List of options
    val radioOptions = listOf("Option 1", "Option 2", "Option 3")

    // State to store the currently selected option
    val selectedOption = remember { mutableStateOf("") }

    Column(Modifier.padding(PaddingValues(16.dp))) {
        radioOptions.forEach { text ->
            RadioButtonWithLabel(
                label = text,
                isSelected = text == selectedOption.value,
                onSelect = { selectedOption.value = text }
            )
        }
    }
}

@Composable
fun RadioButtonWithLabel(
    label: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(modifier = Modifier
        .padding(vertical = 8.dp)
        .clickable { onSelect() }, verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = isSelected,
            onClick = { onSelect() }
        )
        Text(
            text = label,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}


