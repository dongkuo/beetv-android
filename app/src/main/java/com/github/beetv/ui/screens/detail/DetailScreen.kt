package com.github.beetv.ui.screens.detail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.github.beetv.ui.components.LoadingIndicator
import com.github.beetv.ui.components.Tabs
import com.github.beetv.ui.data.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)

@Composable
fun DetailScreen(
    channelId: Long,
    itemId: String,
    navHostController: NavHostController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sourceGroupIndex = state.sourceGroupIndex
    val itemDetail = state.itemDetail
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = channelId, key2 = itemId) {
        viewModel.loadDetail(channelId, itemId)
    }
    Box {
        Column {
            Row(Modifier.padding(16.dp)) {
                AsyncImage(
                    model = itemDetail.cover,
                    contentDescription = itemDetail.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(200.dp)
                        .aspectRatio(0.75F)
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    onError = {
                        Log.e("AsyncImage", "", it.result.throwable)
                    }
                )
                Column(Modifier.padding(start = 16.dp)) {
                    Text(
                        text = state.itemDetail.name,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    )
                    LabelText("主演", itemDetail.actors.joinToString(", "))
                    LabelText("年代", itemDetail.releaseYear.toString())
                    LabelText("分类", itemDetail.category.name)
                    LabelText("国家", itemDetail.originCountry)
                    LabelText("简介", itemDetail.summary)
                }
            }
            Tabs(selectedTabIndex = sourceGroupIndex) {
                itemDetail.sourceGroups.forEach { sourceGroup ->
                    tabPane(sourceGroup.name, viewModel::selectSourceGroup) {
                        FlowRow(modifier = Modifier.padding(8.dp)) {
                            sourceGroup.sources.forEach { source ->
                                ChipItem(source.name, source.url) {
                                    coroutineScope.launch {
                                        val mediaUrl = viewModel.resolveMediaUrl(it)
                                        navHostController.navigate(Routes.playerPage(mediaUrl))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        // Loading
        if (state.isLoading) {
            LoadingIndicator()
        }
    }
}

@Composable
fun LabelText(label: String, content: String) {
    Row {
        Text(text = "$label: ")
        Text(text = content)
    }
}

@Composable
fun <T> ChipItem(text: String, data: T, onClick: (T) -> Unit) {
    Button(onClick = { onClick(data) }) {
        Text(text = text)
    }
}