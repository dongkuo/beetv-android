//package com.github.beetv.ui.screens.channel
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavHostController
//import com.github.beetv.ui.component.PosterFlow
//import com.github.beetv.ui.component.SideBar
//import com.github.beetv.ui.component.TabBar
//import com.github.beetv.ui.data.posterGridGroup
//import com.github.beetv.ui.data.sideBarBottomItems
//import com.github.beetv.ui.data.sideBarCenterItems
//import com.github.beetv.ui.data.sideBarTopItems
//import com.github.beetv.ui.data.tabs
//import com.github.beetv.ui.data.Routes
//
//@Composable
//fun ChannelScreen(navController: NavHostController) {
//    Row(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
//        // Left
//        SideBar(sideBarTopItems, sideBarCenterItems, sideBarBottomItems)
//        // Right
//        Column {
//            // TabRow
//            var selectedTabIndex by remember { mutableIntStateOf(0) }
//            TabBar(selectedTabIndex, tabs) { selectedTabIndex = it }
//            // ContentView
//            when (selectedTabIndex) {
//                0 -> {
//                    LazyColumn {
//                        items(posterGridGroup.entries.toList()) {
//                            PosterFlow(it.key, it.value) {
//                                navController.navigate(Routes.PLAYER_ROUTE)
//                            }
//                        }
//                    }
//                }
//
//                1 -> {
//                    Text("Page 1")
//                }
//
//                2 -> {
//                    Text("Page 2")
//                }
//
//                3 -> {
//                    Text("Page 3")
//                }
//            }
//        }
//    }
//}