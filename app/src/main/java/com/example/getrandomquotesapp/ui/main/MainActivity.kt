package com.example.getrandomquotesapp.ui.main

import android.os.Bundle
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.getrandomquotesapp.data.response.RandomQuotesResponse
import com.example.getrandomquotesapp.ui.theme.GetRandomQuotesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GetRandomQuotesAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RandomQuotesScreen(
                        innerPaddingValues = innerPadding
                    )

                }
            }
        }
    }
}

@Composable
fun RandomQuotesScreen(
    mainViewModel: MainViewModel = viewModel(),
    modifier: Modifier = Modifier,
    innerPaddingValues: PaddingValues
) {
    val randomQuotes = mainViewModel.quote.value
    val error = mainViewModel.errorMessage.value

    LaunchedEffect(Unit) {
        mainViewModel.fetchRandomQuotes()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {

        if (randomQuotes != null) {
            Text(
                text = "ID: ${randomQuotes.id}",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Author: ${randomQuotes.author}",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Quote: \"${randomQuotes.quote}\"",
                style = MaterialTheme.typography.bodySmall
            )
        } else if (error.isNotEmpty()) {
            Text("Error: $error", color = MaterialTheme.colorScheme.error)
        } else {
            Text("Loading...")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { mainViewModel.fetchRandomQuotes() },
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .height(32.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Get Your Random Quote",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}

@Composable
fun GetRandomQuotesPreviewContent(quote: RandomQuotesResponse) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = quote.author,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = quote.quote,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { },
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .height(32.dp)
        ) {
            Text(
                text = "Get Your Random Quote",
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun RandomQuotesPreview() {
    val dummyQuote = RandomQuotesResponse(
        id = 1,
        quote = "Be yourself; everyone else is already taken.",
        author = "Oscar Wilde"
    )
    GetRandomQuotesPreviewContent(quote = dummyQuote)
}