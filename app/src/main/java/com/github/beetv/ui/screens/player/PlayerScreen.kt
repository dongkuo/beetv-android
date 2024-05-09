package com.github.beetv.ui.screens.player

import android.content.Context
import android.net.Uri
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
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import com.github.beetv.ui.components.PlayerController

@Composable
fun PlayerScreen() {
    VideoPlayer(Uri.parse("https://vdept3.bdstatic.com/mda-mhfdmrew4xng1ekj/sc/cae_h264/1629107069949559296/mda-mhfdmrew4xng1ekj.mp4?v_from_s=hkapp-haokan-hnb&auth_key=1714149362-0-0-a0526cbfc7ab7c1ba273d93ba1f67f98&bcevod_channel=searchbox_feed&cr=2&cd=0&pd=1&pt=3&logid=2162024071&vid=7410769634688450347&klogid=2162024071&abtest="))
}

@Composable
fun VideoPlayer(
    uri: Uri,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val context = LocalContext.current
    val player = rememberPlayer(context, uri)

    Box(Modifier.fillMaxSize()) {
        PlayerScreen(context, player)
        PlayerController(player)
    }

    DisposableEffect(lifecycleOwner) {
        onDispose {
            player.release()
        }
    }
}

@Composable
private fun PlayerScreen(
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
                val defaultDataSourceFactory = DefaultDataSource.Factory(context)
                val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                    context,
                    defaultDataSourceFactory
                )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(uri))

                setMediaSource(source)
                prepare()
            }
    }
    exoPlayer.playWhenReady = true
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
    return exoPlayer
}


