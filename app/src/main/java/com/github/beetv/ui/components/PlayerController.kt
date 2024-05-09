package com.github.beetv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import com.github.beetv.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds


@Composable
fun PlayerController(player: Player) {
    val state = remember { ControllerState(player) }

    LaunchedEffect(true) { state.loopTasks() }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.weight(1.0F))
        Column(
            modifier = Modifier
                .background(Brush.verticalGradient(listOf(Color(0, 0, 0, 50), Color(0, 0, 0, 150))))
                .fillMaxWidth(),
        ) {
            ProgressBar(
                state.position,
                state.duration,
                state::onDragProgress,
                state::onFinishDragProgress
            )
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TimeLabel(state.position)
                Row(
                    modifier = Modifier.weight(1F),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    val iconSize = 56.dp
                    val space = 16.dp
                    // rewind button
                    IconButton(onClick = {
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_rewind),
                            contentDescription = "rewind",
                            tint = Color.White,
                            modifier = Modifier.size(iconSize)
                        )
                    }
                    Spacer(Modifier.size(space))
                    // play button
                    IconToggleButton(
                        checked = state.isPlaying,
                        onCheckedChange = { state.playerToggle() }) {
                        Icon(
                            painter = painterResource(if (state.isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
                            contentDescription = "play/pause",
                            tint = Color.White,
                            modifier = Modifier.size(iconSize)
                        )
                    }
                    Spacer(Modifier.size(space))
                    // forward button
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.ic_forward),
                            contentDescription = "forward",
                            tint = Color.White,
                            modifier = Modifier.size(iconSize)
                        )
                    }
                }
                TimeLabel(state.duration)
            }
        }
    }
}

@Composable
private fun ProgressBar(
    position: Long,
    duration: Long,
    onValueChange: (Long) -> Unit,
    onValueChangeFinished: () -> Unit
) {
    Slider(
        modifier = Modifier.padding(horizontal = 8.dp),
        valueRange = 0F..duration.toFloat().coerceAtLeast(0F),
        value = position.toFloat(),
        onValueChange = { onValueChange(it.toLong()) },
        onValueChangeFinished = { onValueChangeFinished() }
    )
}

@Composable
private fun TimeLabel(milliseconds: Long) {
    var remainSecond = milliseconds / 1000
    val hour = remainSecond / 3600
    remainSecond -= hour * 3600
    val minute = remainSecond / 60
    remainSecond -= minute * 60
    Text(
        "${paddingZero(hour)}:${paddingZero(minute)}:${paddingZero(remainSecond)}",
        color = Color.White
    )
}

private fun paddingZero(number: Long): String = if (number < 10) "0$number" else "$number"

private typealias Task = suspend () -> Unit

private class ControllerState(val player: Player) {
    var position by mutableLongStateOf(0L)
    var duration by mutableLongStateOf(0L)
    var isPlaying by mutableStateOf(true)
    val tasks = Channel<Task>(UNLIMITED)

    init {
        player.addListener(object : Player.Listener {

            override fun onEvents(player: Player, events: Player.Events) {
                if (events.containsAny(Player.EVENT_IS_PLAYING_CHANGED)
                ) {
                    isPlaying = player.isPlaying
                    sendTask(this@ControllerState::updateProgress)
                }
                if (events.containsAny(
                        Player.EVENT_POSITION_DISCONTINUITY,
                        Player.EVENT_TIMELINE_CHANGED,
                        Player.EVENT_AVAILABLE_COMMANDS_CHANGED
                    )
                ) {
                    updateDuration()
                }
            }
        })
    }

    fun onDragProgress(position: Long) {
        this.position = position
        if (isPlaying) {
            player.pause()
        }
    }

    fun onFinishDragProgress() {
        player.seekTo(position)
        player.play()
    }

    suspend fun updateProgress() {
        if (isPlaying) {
            val playerPosition = player.currentPosition
            position = if (playerPosition > 0) playerPosition else 0
            delay(1.seconds)
            sendTask { updateProgress() }
        }
    }

    private fun updateDuration() {
        val playerDuration = player.contentDuration
        duration = if (playerDuration > 0) playerDuration else 0
    }

    fun playerToggle() {
        isPlaying = !isPlaying
        if (player.isPlaying) {
            player.pause()
        } else {
            player.play()
        }
    }

    fun playerSeek() {
        player.seekTo(position)
    }

    fun sendTask(task: Task) = tasks.trySend(task)

    suspend fun loopTasks() {
        while (true) {
            tasks.receive().invoke()
        }
    }
}
