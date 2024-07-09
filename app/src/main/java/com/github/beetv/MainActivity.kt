package com.github.beetv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.beetv.server.Server
import com.github.beetv.ui.components.NavRail
import com.github.beetv.ui.data.NAV_RAIL_ITEMS
import com.github.beetv.ui.data.Routes
import com.github.beetv.ui.screens.detail.DetailScreen
import com.github.beetv.ui.screens.home.HomeScreen
import com.github.beetv.ui.screens.player.PlayerScreen
import com.github.beetv.ui.theme.BeetvTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var server: Server

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeetvTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    val isShowSideBar by remember { mutableStateOf(true) }
                    val navController: NavHostController = rememberNavController()

                    Row {
                        if (isShowSideBar) {
                            SideBar(navController)
                        }
                        NavGraph(navController)
                    }
                }
            }
        }

        // start server
        lifecycleScope.launch {
            server.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // stop server
        lifecycleScope.launch {
            server.stop()
        }
    }
}

@Composable
fun SideBar(navController: NavHostController) {
    var selectedSideIndex by remember { mutableIntStateOf(1) }
    NavRail(selectedSideIndex, NAV_RAIL_ITEMS) { index, navRailItem ->
        selectedSideIndex = index
        if (navRailItem.route != null) {
            navController.navigate(navRailItem.route)
        }
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController,
        Routes.HOME_ROUTE,
        Modifier.fillMaxSize()
    ) {
        composable(Routes.HOME_ROUTE) {
            HomeScreen(navController)
        }
        composable(Routes.PLAYER_ROUTE) {
            PlayerScreen(it.arguments?.getString("url") ?: "")
        }
        composable(Routes.DETAIL_ROUTE) {
            DetailScreen(
                (it.arguments?.getString("channelId") ?: "0").toLong(),
                it.arguments?.getString("itemId") ?: "",
                navController
            )
        }
    }
}
