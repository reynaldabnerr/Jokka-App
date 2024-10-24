package common.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String,
    actions: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp) // Standard app bar height
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFD90000), // Deep Red
                        Color(0xFFD10062), // Magenta
                        Color(0xFFFF6F61)  // Coral Pink
                    )
                )
            )
            .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)),
        title = {
            // Title is aligned vertically center
            Text(
                text = title,
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 8.dp, top = 8.dp)
                    .wrapContentSize(Alignment.CenterStart) // Align text vertically centered
            )
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 16.dp) // Padding to keep actions aligned
            ) {
                actions?.invoke()
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}