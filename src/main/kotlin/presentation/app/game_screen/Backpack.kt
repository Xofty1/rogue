package presentation.app.game_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import api.entity.Backpack
import api.entity.item.*
import api.util.item_type.ElixirType
import api.util.item_type.FoodType
import api.util.item_type.ScrollType
import api.util.item_type.WeaponType
import presentation.util.ItemType
import presentation.util.MyStyle.commonTextStyle
import presentation.util.MyStyle.textColorBackpackNumbers
import presentation.util.MyStyle.titleTextStyle
import presentation.util.PresentationConstants.INDEX_OFFSET
import presentation.util.PresentationConstants.ROW_NUMBER_ELIXIR
import presentation.util.PresentationConstants.ROW_NUMBER_FOOD
import presentation.util.PresentationConstants.ROW_NUMBER_SCROLL
import presentation.util.PresentationConstants.ROW_NUMBER_WEAPON

/**
 * Отображает содержимое инвентаря в диалоговом окне
 */
@Composable
fun BackpackContent(
    onDismiss: () -> Unit,
    backpack: Backpack,
    onUse: (Int, Int) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .size(460.dp, 300.dp)
                .background(Color.Gray)
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(text = "Инвентарь", style = titleTextStyle)
                Text(text = "Нажмите для использования", style = commonTextStyle)
                Row {
                    Text(text = "Зеленый - ловкость", style = commonTextStyle, color = Color.Green)
                    Spacer(Modifier.width(8.dp))
                    Text(text = "Синий - сила", style = commonTextStyle, color = Color.Blue)
                    Spacer(Modifier.width(8.dp))
                    Text(text = "Красный - макс ХП", style = commonTextStyle, color = Color.Red)
                }


                ItemTypes(ItemType.ELIXIR, backpack.elixirs.map { it }.toTypedArray(), onUse)
                ItemTypes(ItemType.FOOD, backpack.foods.map { it }.toTypedArray(), onUse)
                ItemTypes(ItemType.SCROLL, backpack.scrolls.map { it }.toTypedArray(), onUse)
                ItemTypes(ItemType.WEAPON, backpack.weapons.map { it }.toTypedArray(), onUse)
            }
        }
    }
}

/**
 * Отображает один ряд инвентаря.
 * Навешивает слушатель клика на предмет, если он в рюкзаке.
 * @param itemType тип предмета.
 * @param array массив предметов.
 */
@Composable
private fun ItemTypes(
    itemType: ItemType,
    array: Array<Item?>,
    onUse: (Int, Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            Text(
                modifier = Modifier.width(100.dp),
                text = itemType.value,
                style = commonTextStyle
            )
        }

        itemsIndexed(array) { index, item ->
            Box(
                modifier = Modifier
                    .size(33.dp)
                    .border(1.dp, Color.Black)
            ) {
                val foregroundImage = GetPainterForBackpack(item)
                Text(
                    text = "${index + INDEX_OFFSET}",
                    style = titleTextStyle,
                    color = textColorBackpackNumbers
                )
                foregroundImage?.let {
                    Image(
                        painter = it,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().clickable {
                            onUse(getItemTypeRowNumber(itemType), index)
                        }
                    )
                }
            }
        }
    }
}

/**
 * Возвращает номер строки для заданного типа предмета.
 *
 * @param itemType Тип предмета, для которого нужно определить номер строки.
 * @return Номер строки, соответствующий заданному типу предмета.
 */
private fun getItemTypeRowNumber(
    itemType: ItemType
): Int {
    return when (itemType) {
        ItemType.ELIXIR -> ROW_NUMBER_ELIXIR
        ItemType.FOOD -> ROW_NUMBER_FOOD
        ItemType.SCROLL -> ROW_NUMBER_SCROLL
        ItemType.WEAPON -> ROW_NUMBER_WEAPON
    }
}

/**
 * Возвращает иконку предмета для рюкзака
 * @param item предмет из инвентаря
 * @return иконка из ресурсов
 */
@Composable
private fun GetPainterForBackpack(
    item: Item?
): Painter? {
    return when (item) {
        is Elixir -> {
            when (item.type) {
                ElixirType.AGILITY_ELIXIR -> painterResource("icons/items/elixirs/elixir_agi.png")
                ElixirType.STRENGTH_ELIXIR -> painterResource("icons/items/elixirs/elixir_str.png")
                ElixirType.MAX_HEALTH_ELIXIR -> painterResource("icons/items/elixirs/elixir_mhp.png")
            }
        }

        is Food -> {
            when (item.type) {
                FoodType.APPLE -> painterResource("icons/items/food/apple.png")
                FoodType.BREAD -> painterResource("icons/items/food/bread.png")
                FoodType.MEAT -> painterResource("icons/items/food/meat.png")
                FoodType.BEER -> painterResource("icons/items/food/beer.png")
                FoodType.CHEESE -> painterResource("icons/items/food/cheese.png")
            }
        }

        is Weapon -> {
            when (item.type) {
                WeaponType.SWORD -> painterResource("icons/items/weapons/sword.png")
                WeaponType.AXE -> painterResource("icons/items/weapons/axe.png")
                WeaponType.KNIFE -> painterResource("icons/items/weapons/knife.png")
                WeaponType.UNARMED -> painterResource("icons/items/weapons/knife.png")
                WeaponType.STICK -> painterResource("icons/items/weapons/stick.png")
                WeaponType.EPIC_SWORD -> painterResource("icons/items/weapons/epic_sword.png")
            }
        }

        is Scroll -> {
            when (item.type) {
                ScrollType.MAX_HEALTH_SCROLL -> painterResource("icons/items/scrolls/scroll_mhp.png")
                ScrollType.STRENGTH_SCROLL -> painterResource("icons/items/scrolls/scroll_str.png")
                ScrollType.AGILITY_SCROLL -> painterResource("icons/items/scrolls/scroll_agi.png")
            }
        }

        else -> null
    }
}
