package api.entity

import api.entity.item.Elixir
import api.entity.item.Food
import api.entity.item.Scroll
import api.entity.item.Weapon
import api.util.constant.BackpackStats

class Backpack(
    val elixirs: Array<Elixir?> = arrayOfNulls(BackpackStats.MAX_ITEMS_BY_TYPE),
    val foods: Array<Food?> = arrayOfNulls(BackpackStats.MAX_ITEMS_BY_TYPE),
    val scrolls: Array<Scroll?> = arrayOfNulls(BackpackStats.MAX_ITEMS_BY_TYPE),
    val weapons: Array<Weapon?> = arrayOfNulls(BackpackStats.MAX_ITEMS_BY_TYPE)
)
