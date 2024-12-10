package component.carousel

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
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import data.Destination
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Carousel(destinations: List<Destination>) {
    val carouselState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var currentIndex by remember { mutableIntStateOf(0) }

    // Auto-scroll every 2 seconds
    LaunchedEffect(carouselState) {
        while (true) {
            delay(2000) // Auto-scroll every 2 seconds
            currentIndex = (currentIndex + 1) % destinations.size
            coroutineScope.launch {
                carouselState.animateScrollToItem(currentIndex)
            }
        }
    }

    LazyRow(
        state = carouselState,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f)
            .clip(RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(destinations) { destination ->
            Image(
                painter = rememberAsyncImagePainter(model = destination.destinationimage),
                contentDescription = destination.destinationname,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}