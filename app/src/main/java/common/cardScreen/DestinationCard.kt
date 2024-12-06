package common.cardScreen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Top level declaration for data class Destination
data class Destination(
    val id: Int,
    val imageResId: Int,
    val nameResId: Int,
    val categoryResId: Int,
    val descriptionResId: Int // Include description
)

// Top level declaration for Composable function PlaceCard
@Composable
fun PlaceCard(
    destination: Destination, // Parameter to receive the destination object
    isLiked: Boolean,
    onLikeClick: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    val expandTransition = updateTransition(isExpanded, label = "expandTransition")
    val cardHeight by expandTransition.animateDp(
        transitionSpec = { spring(stiffness = Spring.StiffnessLow) },
        label = "cardHeight"
    ) { expanded -> if (expanded) 350.dp else 200.dp }  // Adjust card height

    val colorTransition = updateTransition(isLiked, label = "colorTransition")
    val favoriteColor by colorTransition.animateColor(
        transitionSpec = { spring(stiffness = Spring.StiffnessHigh) },
        label = "favoriteColor"
    ) { liked -> if (liked) Color.Red else Color.White }

    val rotationTransition = updateTransition(isLiked, label = "rotationTransition")
    val favoriteRotation by rotationTransition.animateFloat(
        transitionSpec = { spring(stiffness = Spring.StiffnessHigh) },
        label = "favoriteRotation"
    ) { liked -> if (liked) 360f else 0f }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .padding(8.dp)
            .clickable { isExpanded = !isExpanded }
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
    ) {
        Box {
            Image(
                painter = painterResource(id = destination.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = destination.nameResId),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    IconButton(
                        onClick = onLikeClick,
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.White.copy(alpha = 0.3f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite",
                            tint = favoriteColor,
                            modifier = Modifier.graphicsLayer(rotationZ = favoriteRotation)
                        )
                    }
                }

                AnimatedVisibility(
                    visible = isExpanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    // Deskripsi muncul saat expanded
                    Text(
                        text = stringResource(id = destination.descriptionResId),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom =  100.dp)
                    )

                    // Kolom untuk kategori, muncul saat expanded
                    Column {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(id = destination.categoryResId),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            modifier = Modifier.padding(top = 150.dp)
                        )
                    }
                }
            }
        }
    }
}