package component.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AppBarDetails(
    title: String,
    navController: NavController
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent, // Pastikan tidak ada warna solid pada Surface
        shadowElevation = 4.dp // Tambahkan bayangan jika diperlukan
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFD90000), // Deep Red
                            Color(0xFFD10062), // Magenta
                            Color(0xFFFF6F61)  // Coral Pink
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // Back Button
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White // Menggunakan warna putih agar kontras
                )
            }
            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 8.dp) // Jarak antara ikon dan teks
            )
        }
    }
}