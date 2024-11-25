package presentation.app.game_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import controller.dto.PlayerDto
import presentation.util.MyStyle.statsTextStyle
import presentation.util.MyStyle.titleTextStyle
import presentation.util.PresentationConstants.BACKPACK_ICON

/**
 * Отрисовка панели инвентаря и статов
 */
@Composable
internal fun SidePanel(
    player: PlayerDto,
    action: List<String>,
    isBagOpen: Boolean,
    openBackpack: () -> Unit,
    closeBackpack: () -> Unit,
    onUse: (Int, Int) -> Unit,
    modifier: Modifier,
    level: Int
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(Color.Gray)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Уровень $level",
            style = titleTextStyle,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Рюкзак",
            style = titleTextStyle
        )
        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(BACKPACK_ICON),
            contentDescription = "Рюкзак",
            alignment = Alignment.Center,
            modifier = Modifier
                .clickable {
                    openBackpack()
                }
        )
        Spacer(modifier = Modifier.height(32.dp))

        PlayerStats(player)
        Spacer(modifier = Modifier.height(32.dp))

        Actions(action)

        // Отрисовка рюкзака
        if (isBagOpen) {
            BackpackContent(
                onDismiss = closeBackpack,
                backpack = player.backpack,
                onUse = onUse
            )
        }
    }
}

/**
 * Отображает список событий за прошлый ход
 */
@Composable
private fun Actions(
    action: List<String>
) {
    LazyColumn(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "События",
                style = titleTextStyle,
                modifier = Modifier.padding(bottom = 8.dp),
                textAlign = TextAlign.Center
            )
        }
        itemsIndexed(action) { index, action ->
            Text(
                text = "${index + 1}. $action",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp).width(160.dp)
            )
        }
    }
}

/**
 * Отрисовывает статы
 * @param player персонаж
 */
@Composable
private fun PlayerStats(
    player: PlayerDto
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Статы",
            style = titleTextStyle
        )
        Spacer(Modifier.height(8.dp))

        val healthIsLow = if (player.health <= player.maxHealth / 3) Color.Red else Color.White
        Text(
            text = "Здоровье: ${player.health}/${player.maxHealth}",
            style = statsTextStyle,
            color = healthIsLow,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))

        Text(
            text = "Ловкость: ${player.agility}",
            style = statsTextStyle,
            color = Color.Green
        )
        Spacer(Modifier.height(8.dp))

        Text(
            text = "Сила: ${player.strength}",
            style = statsTextStyle,
            color = Color.Blue
        )
        Spacer(Modifier.height(8.dp))

        Text(
            text = "Оружие: ${player.equippedWeapon}",
            style = statsTextStyle,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))

        Text(
            text = "Сокровища: ${player.rating}",
            style = statsTextStyle,
            color = Color.Yellow,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}
