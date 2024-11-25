package presentation.app.game_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import presentation.util.Legend
import presentation.util.LegendProvider
import presentation.util.MyModifier.buttonModifier
import presentation.util.MyModifier.dialogBoxModifier
import presentation.util.MyStyle.dialogProperties
import presentation.util.MyStyle.extraLargeTextStyle
import presentation.util.MyStyle.largeTextStyle
import presentation.util.MyStyle.menuButtonStyle
import presentation.util.MyStyle.titleTextStyle

/**
 * Отображение окна рекордов.
 *
 * @param onRecord TAB для открытия окна.
 * @param tableRecords таблица рекордов.
 */
@Composable
internal fun RecordTableWindow(
    onRecord: () -> Unit,
    tableRecords: List<String>
) {
    Dialog(
        onDismissRequest = onRecord,
        properties = dialogProperties
    ) {
        Box(
            modifier = dialogBoxModifier,
        ) {
            Text(
                text = "Рекорды",
                textAlign = TextAlign.Center,
                style = extraLargeTextStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
            )
            LazyColumn(
                modifier = Modifier.padding(top = 72.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(tableRecords) { record ->
                    Text(
                        text = record,
                        style = titleTextStyle,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

/**
 * Отображение окна с Легендой
 */
@Composable
internal fun LegendWindow(
    onLegend: () -> Unit
) {
    val legend = LegendProvider.getLegend()

    Dialog(
        onDismissRequest = onLegend,
        properties = dialogProperties
    ) {
        Box(
            modifier = dialogBoxModifier,
        ) {
            Text(
                text = "Легенда",
                textAlign = TextAlign.Center,
                style = extraLargeTextStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            )
            LazyColumn(
                modifier = Modifier
                    .padding(top = 72.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(legend) { legend ->
                    LegendCard(legend)
                }
            }
        }
    }
}

/**
 * Карточка одной легенды
 */
@Composable
fun LegendCard(legend: Legend) {
    Card(
        Modifier
            .height(92.dp)
            .fillMaxWidth(),
        elevation = 10.dp,
        backgroundColor = Color(0xFF804080)
    ) {
        Row(
            Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(64.dp),
                painter = legend.icon,
                contentDescription = legend.title
            )
            Spacer(Modifier.width(8.dp))
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = legend.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = legend.description,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}

/**
 * Отображение окна после смерти персонажа.
 * @param onStartNewGame старт новой игры.
 * @param onExit выход из приложения.
 */
@Composable
internal fun PlayerDeathWindow(
    onDismiss: () -> Unit,
    onStartNewGame: () -> Unit,
    onExit: () -> Unit,
    rating: Int
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = dialogProperties
    ) {
        Box(
            modifier = dialogBoxModifier,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Игра окончена",
                    style = extraLargeTextStyle,
                    color = Color.Black
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Ваш счёт: $rating",
                    style = largeTextStyle,
                    color = Color.Black
                )
                Button(
                    modifier = buttonModifier,
                    onClick = onStartNewGame
                ) {
                    Text(text = "Новая Игра", style = menuButtonStyle)
                }
                Button(
                    modifier = buttonModifier,
                    onClick = onExit
                ) {
                    Text(text = "Выход", style = menuButtonStyle)
                }
            }
        }
    }
}

/**
 * Отображение диалогового окна паузы.
 * @param onDismiss действие для скрытия панели.
 * @param onExit действие для закрытия приложения.
 */
@Composable
internal fun PauseWindow(
    onDismiss: () -> Unit,
    onExit: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = dialogProperties
    ) {
        Box(
            modifier = dialogBoxModifier,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Пауза",
                    textAlign = TextAlign.Center,
                    style = extraLargeTextStyle
                )
                Button(
                    modifier = buttonModifier,
                    onClick = onDismiss
                ) {
                    Text(text = "Продолжить игру", style = menuButtonStyle)
                }
                Button(
                    modifier = buttonModifier,
                    onClick = onExit
                ) {
                    Text(text = "Выход", style = menuButtonStyle)
                }
            }
        }
    }
}