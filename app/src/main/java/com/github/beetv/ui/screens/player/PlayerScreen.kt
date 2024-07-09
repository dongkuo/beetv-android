package com.github.beetv.ui.screens.player

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import com.github.beetv.ui.components.PlayerController

@Composable
fun PlayerScreen(url: String) {
    Log.i("PlayerScreen", url)
    VideoPlayer(Uri.parse(url))
}

@Composable
fun VideoPlayer(
    uri: Uri,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val context = LocalContext.current
    val player = rememberPlayer(context, uri)

    Box(Modifier.fillMaxSize()) {
        PlayerSurface(context, player)
        PlayerController(player)
    }

    DisposableEffect(lifecycleOwner) {
        onDispose {
            player.release()
        }
    }
}

@Composable
private fun PlayerSurface(
    context: Context,
    player: Player
) {
    AndroidView({
        val playerView = PlayerView(context)
        playerView.player = player
        playerView.layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        playerView
    })
}

@Composable
@OptIn(UnstableApi::class)
private fun rememberPlayer(
    context: Context,
    uri: Uri
): Player {
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
//                val defaultDataSourceFactory = DefaultDataSource.Factory(context)
//                val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
//                    context,
//                    defaultDataSourceFactory
//                )
//                val source = HlsMediaSource.Factory(dataSourceFactory)
//                    .createMediaSource(MediaItem.fromUri(uri))
//
//                setMediaSource(source)
                val mediaItem = MediaItem.fromUri(uri)
                setMediaItem(mediaItem)
                prepare()
                play()
            }
    }
    exoPlayer.playWhenReady = true
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
    return exoPlayer
}


