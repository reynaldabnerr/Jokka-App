package com.example.jokka_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.jokka_app.navigation.NavGraph
import com.example.jokka_app.ui.theme.JokkaAppTheme  // Pastikan nama tema sesuai dengan yang telah Anda buat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JokkaAppTheme {  // Gunakan tema yang sesuai
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavGraph(navController = navController)
                }
            }
        }
    }
}
