package api.service.game_mechanic

import api.entity.Backpack
import api.entity.item.*

object BackpackService {
    fun getItem(backpack: Backpack, rowNumber: Int, cellNumber: Int): Item? {
        return when (rowNumber) {
            0 -> backpack.elixirs[cellNumber]
            1 -> backpack.foods[cellNumber]
            2 -> backpack.scrolls[cellNumber]
            3 -> backpack.weapons[cellNumber]
            else -> null
        }
    }

    fun pickUpItem(backpack: Backpack, item: Item): String? {
        return when (item) {
            is Elixir -> addToArray(backpack.elixirs, item)
            is Food -> addToArray(backpack.foods, item)
            is Scroll -> addToArray(backpack.scrolls, item)
            is Weapon -> addToArray(backpack.weapons, item)
            else -> null
        }
    }

    private fun <T> addToArray(array: Array<T?>, item: T): String? {
        if (item == null) return null

        val index = array.indexOfFirst { it == null }
        return if (index == -1) {
            null
        } else {
            array[index] = item
            item as Item
            item.name
        }
    }

    fun removeItem(backpack: Backpack, item: Item): Boolean {
        return when (item) {
            is Elixir -> removeFromArray(backpack.elixirs, item)
            is Food -> removeFromArray(backpack.foods, item)
            is Scroll -> removeFromArray(backpack.scrolls, item)
            is Weapon -> removeFromArray(backpack.weapons, item)
            else -> false
        }
    }

    private fun <T> removeFromArray(array: Array<T?>, item: T): Boolean {
        val index = array.indexOf(item)
        return if (index != -1) {
            array[index] = null
            true
        } else {
            false
        }
    }
}