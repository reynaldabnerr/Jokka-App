// File: PlaceCard.kt
package com.example.jokka_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.jokka_app.R

// Data class untuk tempat wisata
data class Place(
    val imageResourceId: Int,
    val stringResourceId: Int
)

@Composable
fun PlaceCard(place: Place, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .width(200.dp)
            .clip(RoundedCornerShape(16.dp)) // Sudut rounded untuk setiap item
            .background(Color.White)
            .padding(16.dp) // Padding dalam setiap item
    ) {
        // Menampilkan gambar
        Image(
            painter = painterResource(id = place.imageResourceId),
            contentDescription = LocalContext.current.getString(place.stringResourceId),
            modifier = Modifier
                .height(110.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)), // Gambar dengan sudut rounded
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Menampilkan teks di bawah gambar
        Text(
            text = LocalContext.current.getString(place.stringResourceId),
            fontSize = 16.sp,
            fontWeight = FontWeight(400),
            color = Color.DarkGray,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}