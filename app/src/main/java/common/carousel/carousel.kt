package common.carousel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import common.cardHome.Place
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Carousel(places: List<Place>) {
    val carouselState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var currentIndex by remember { mutableIntStateOf(0) }

    // LaunchedEffect to auto-scroll every 2 seconds
    LaunchedEffect(carouselState) {
        while (true) {
            delay(2000) // Autonext every 2 seconds
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
            .aspectRatio(16 / 9f) // Set aspect ratio 16:9 for the whole carousel
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
                    .clip(RoundedCornerShape(16.dp)), // Rounded corners on image
                contentScale = ContentScale.Crop
            )
        }
    }
}