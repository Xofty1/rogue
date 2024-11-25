package presentation.app.start_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import presentation.app.Toast
import presentation.util.MyModifier.buttonModifier
import presentation.util.MyStyle.menuButtonStyle

@Composable
fun StartMenu(
    onStartNewGame: () -> Unit,
    onLoadGame: () -> Unit,
    onExit: () -> Unit,
    isErrorLoading: Boolean,
    resetError: () -> Unit
) {
    Box {
        Image(
            painter = painterResource("icons/rogue_background_menu.jpg"),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,

            ) {
            Button(
                modifier = buttonModifier,
                onClick = onStartNewGame
            ) {
                Text(text = "Новая игра", style = menuButtonStyle)
            }
            Button(
                modifier = buttonModifier,
                onClick = onLoadGame
            ) {
                Text(text = "Загрузить игру", style = menuButtonStyle)
            }
            Button(
                modifier = buttonModifier,
                onClick = onExit
            ) {
                Text(text = "Выход", style = menuButtonStyle)
            }
        }

        if (isErrorLoading) {
            Toast("У вас нет сохраненных игр", 2000L, resetError)
        }
    }


}