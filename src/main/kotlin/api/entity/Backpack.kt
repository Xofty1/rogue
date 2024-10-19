package api.entity

import api.entity.item.*
import api.util.constant.BackpackStats

data class Backpack(
    val treasures: Array<Treasure?> = arrayOfNulls(BackpackStats.MAX_ITEMS_BY_TYPE),
    val weapons: Array<Weapon?> = arrayOfNulls(BackpackStats.MAX_ITEMS_BY_TYPE),
    val items: Array<Item?> = arrayOfNulls(BackpackStats.MAX_ITEMS_BY_TYPE),
    val scrolls: Array<Scroll?> = arrayOfNulls(BackpackStats.MAX_ITEMS_BY_TYPE),
    val elixirs: Array<Elixir?> = arrayOfNulls(BackpackStats.MAX_ITEMS_BY_TYPE),
    val foods: Array<Food?> = arrayOfNulls(BackpackStats.MAX_ITEMS_BY_TYPE),
)