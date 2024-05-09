package com.github.beetv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.beetv.ui.data.Routes
import com.github.beetv.ui.screens.home.HomeScreen
import com.github.beetv.ui.screens.player.PlayerScreen
import com.github.beetv.ui.theme.BeetvTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeetvTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    val navController: NavHostController = rememberNavController()
                    NavHost(
                        navController,
                        Routes.HOME_ROUTE,
                        Modifier.fillMaxSize()
                    ) {
                        composable(Routes.HOME_ROUTE) {
                            HomeScreen()
                        }
                        composable(Routes.PLAYER_ROUTE) {
                            PlayerScreen()
                        }
                    }
                }
            }
        }
    }
}
