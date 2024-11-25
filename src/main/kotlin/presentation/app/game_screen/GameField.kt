package presentation.app.game_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import api.entity.environment.Position
import api.util.constant.MapChar.ELIXIR
import api.util.constant.MapChar.EXIT
import api.util.constant.MapChar.FOOD
import api.util.constant.MapChar.GHOST
import api.util.constant.MapChar.MIMIC
import api.util.constant.MapChar.OGRE
import api.util.constant.MapChar.PLAYER
import api.util.constant.MapChar.SCROLL
import api.util.constant.MapChar.SNAKE_MAGE
import api.util.constant.MapChar.TREASURE
import api.util.constant.MapChar.VAMPIRE
import api.util.constant.MapChar.WEAPON
import api.util.constant.MapChar.ZOMBIE
import api.util.constant.MapConstants.MAP_HEIGHT
import api.util.constant.MapConstants.MAP_WIDTH
import api.util.constant.SymbolConstants.CORRIDOR_CHAR
import api.util.constant.SymbolConstants.INNER_AREA_CHAR
import api.util.constant.SymbolConstants.OUTER_AREA_CHAR
import api.util.constant.SymbolConstants.WALL_CHAR
import controller.dto.DungeonDto
import presentation.util.FogOfWarMapper

/**
 * Отрисовка игрового поля.
 * Карта занимает 1728.dp ширины.
 * @param dungeonMap карта подземелья - прилетает с бэкенда
 */
@Composable
internal fun GameField(dungeonMap: DungeonDto, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxHeight()
    ) {
        val playground = dungeonMap.playground
        val dungeonLevel = dungeonMap.dungeonLevel

        val unknownPlayground =
            FogOfWarMapper.mapPlaygroundToUnknownPlayground(playground, dungeonLevel)
        val foggedPlayground =
            FogOfWarMapper.mapPlaygroundToFoggedPlayground(playground, dungeonLevel)

        val corridorPositions = dungeonMap.corridorPositions
        for (y in 0 until MAP_HEIGHT) {
            Row {
                for (x in 0 until MAP_WIDTH) {
                    val isCorridor = Position(x = x, y = y) in corridorPositions
                    val isUnknown = unknownPlayground[y][x] == FogOfWarMapper.UNKNOWN_FIELD
                    val isFogged = foggedPlayground[y][x] == FogOfWarMapper.FOGGED_FIELD
                    Cell(
                        cell = playground[y][x],
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.DarkGray),
                        isCorridor = isCorridor,
                        isUnknown = isUnknown,
                        isFogged = isFogged,
                    )
                }
            }
        }
    }

}

/**
 * Отрисовка ячейки игрового поля.
 * @param cell символ из констант
 */
@Composable
internal fun Cell(
    cell: Char,
    modifier: Modifier,
    isCorridor: Boolean,
    isUnknown: Boolean,
    isFogged: Boolean,
) {
    Box(
        modifier = modifier
    ) {
        val backgroundImage = GetPainterForBackground(cell, isCorridor)
        val foregroundImage = GetPainterForForeground(cell)
        val foggedFieldImage = GetPainterForFogged(isFogged, isCorridor, cell)
        val unknownFieldImage = GetPainterForUnknownField(isUnknown)

        backgroundImage?.let {
            Image(
                painter = it,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

        foregroundImage?.let {
            Image(
                painter = it,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

        foggedFieldImage?.let {
            Image(
                painter = it,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                alpha = 0.8f
            )
        }

        unknownFieldImage?.let {
            Image(
                painter = it,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun GetPainterForFogged(
    isFogged: Boolean,
    isCorridor: Boolean,
    cell: Char
): Painter? {
    return when (cell) {
        WALL_CHAR,
        OUTER_AREA_CHAR -> null

        else -> {
            if (isFogged) {
                if (isCorridor) {
                    painterResource("icons/environment/corridor.png")
                } else {
                    painterResource("icons/environment/ground.png")
                }
            } else {
                null
            }
        }
    }
}

@Composable
fun GetPainterForUnknownField(isUnknown: Boolean): Painter? {
    return if (isUnknown) painterResource("icons/environment/unknown_field.png")
    else null
}

/**
 * Возвращает иконку заднего плана
 * @param char символ из констант
 * @return иконка из ресурсов
 */
@Composable
internal fun GetPainterForBackground(
    char: Char,
    isCorridor: Boolean
): Painter? {
    return when (char) {
        PLAYER, ZOMBIE, GHOST, OGRE, VAMPIRE, SNAKE_MAGE, MIMIC,
        ELIXIR, FOOD, TREASURE, WEAPON, SCROLL, EXIT -> {
            if (isCorridor) {
                painterResource("icons/environment/corridor.png")
            } else {
                painterResource("icons/environment/ground.png")
            }
        }

        WALL_CHAR -> painterResource("icons/environment/wall.png")
        CORRIDOR_CHAR -> painterResource("icons/environment/corridor.png")
        INNER_AREA_CHAR -> painterResource("icons/environment/ground.png")

        OUTER_AREA_CHAR -> null
        else -> null
    }
}

/**
 * Возвращает иконку переднего плана
 * @param char символ из констант
 * @return иконка из ресурсов
 */
@Composable
internal fun GetPainterForForeground(char: Char): Painter? {
    return when (char) {
        PLAYER -> painterResource("icons/creatures/hero.png")
        ZOMBIE -> painterResource("icons/creatures/zombie.png")
        GHOST -> painterResource("icons/creatures/ghost.png")
        OGRE -> painterResource("icons/creatures/ogre.png")
        VAMPIRE -> painterResource("icons/creatures/vampire.png")
        SNAKE_MAGE -> painterResource("icons/creatures/mage.png")
        MIMIC -> painterResource("icons/creatures/mimic.png")
        ELIXIR -> painterResource("icons/items/elixirs/elixir.png")
        FOOD -> painterResource("icons/items/food/bread.png")
        TREASURE -> painterResource("icons/items/treasure.png")
        WEAPON -> painterResource("icons/items/weapons/axe.png")
        SCROLL -> painterResource("icons/items/scrolls/scroll.png")
        EXIT -> painterResource("icons/environment/exit.png")

        WALL_CHAR, CORRIDOR_CHAR,
        OUTER_AREA_CHAR, INNER_AREA_CHAR -> null

        else -> null
    }
}

@Composable
fun EasterEgg() {
    Box(
        modifier = Modifier.size(32.dp)
    ) {
        Image(
            painter = painterResource("icons/environment/easter_egg.png"),
            contentDescription = null,
            modifier = Modifier.clickable {

            }
        )
    }
}

