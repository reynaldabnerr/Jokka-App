// File: HomeScreen.kt
package features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jokka_app.R
import common.card.Place
import common.card.PlaceCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    // Daftar tempat wisata dengan gambar dan teks
    val places = listOf(
        Place(R.drawable.image1, R.string.travel1),
        Place(R.drawable.image2, R.string.travel2),
        Place(R.drawable.image3, R.string.travel3),
        Place(R.drawable.image4, R.string.travel4),
        Place(R.drawable.image5, R.string.travel5)
    )

    // LazyColumn untuk membuat seluruh konten HomeScreen bisa di-scroll secara vertikal
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding() // Safe area untuk seluruh layout
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Spasi antar item di LazyColumn
    ) {
        // Item pertama: Carousel
        item {
            Carousel(places = places)
        }

        // Spacer untuk memberikan jarak antara Carousel dan PlaceCard
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Box utama untuk "Place To Visit"
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)) // Membuat box utama rounded
                    .background(Color.DarkGray) // Warna background yang lebih terang
                    .padding(16.dp) // Padding dalam box utama
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Menampilkan judul di atas list
                    Text(
                        text = "Place To Visit",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp) // Jarak antara judul dan list
                    )

                    // LazyRow untuk menampilkan list tempat wisata
                    val listState = rememberLazyListState()
                    LazyRow(
                        state = listState,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        items(places) { place ->
                            PlaceCard(place = place)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Carousel(places: List<Place>) {
    val carouselState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var currentIndex by remember { mutableIntStateOf(0) }

    // LaunchedEffect to auto-scroll every 2 seconds
    LaunchedEffect(carouselState) {
        while (true) {
            delay(2000) // Autonext setiap 2 detik
            currentIndex = (currentIndex + 1) % places.size
            coroutineScope.launch {
                carouselState.animateScrollToItem(currentIndex)
            }
        }
    }

    LazyRow(
        state = carouselState,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f) // Menetapkan rasio 16:9 untuk keseluruhan carousel
            .clip(RoundedCornerShape(16.dp)), // Rounded corners
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(places) { place ->
            Image(
                painter = painterResource(id = place.imageResourceId),
                contentDescription = LocalContext.current.getString(place.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
                    .clip(RoundedCornerShape(16.dp)), // Rounded corners pada gambar
                contentScale = ContentScale.Crop
            )
        }
    }
}