import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import presentation.app.App

fun main() = application {
    val windowState = rememberWindowState(
        width = 1920.dp,
        height = 1080.dp,
        placement = WindowPlacement.Fullscreen
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "The best Rogalik in 21 school",
        state = windowState
    ) {
        App()
    }
}



