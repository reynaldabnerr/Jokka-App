package com.example.jokka_app.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jokka_app.R

@Composable
fun HomeScreen() {
    val images = listOf(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5
    )

    val texts = listOf(
        R.string.travel1,
        R.string.travel2,
        R.string.travel3,
        R.string.travel4,
        R.string.travel5
    )

    // Create a horizontally scrollable list using LazyRow
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), // To allow scrolling vertically in case it's needed
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(images.zip(texts)) { item ->
                val (imageRes, textRes) = item
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(200.dp)
                        .padding(16.dp)
                ) {
                    // Display image
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = stringResource(id = textRes),
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Display corresponding text
                    Text(text = stringResource(id = textRes))
                }
            }
        }
    }
}