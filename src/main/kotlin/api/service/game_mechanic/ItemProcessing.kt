package api.service.game_mechanic

import api.entity.Player
import api.entity.item.*

object ItemProcessing {
    fun useItemOnPlayer(player: Player, item: Item): String? {
        return when (item) {
            is Elixir -> {
                if (item.isUsed) {
                    "Эликсир уже выпит. Будет действовать еще ${item.duration} ходов"
                } else {
                    item.isUsed = true
                    increasePlayerStats(item.agility, item.strength, item.maxHealth, player)
                    "Выпит: ${item.name}"
                }
            }

            is Scroll -> {
                increasePlayerStats(item.agility, item.strength, item.maxHealth, player)
                "Применен: ${item.name}"
            }

            is Food -> {
                val health = player.health + item.health
                player.health = if (health < player.maxHealth) health else player.maxHealth
                "Съедена еда: ${item.name}"
            }

            is Weapon -> {
                player.strength -= player.equippedWeapon.strength
                player.equippedWeapon = item
                player.strength += item.strength
                "Экипировано: ${item.name}"
            }

            is Treasure -> {
                player.rating += item.cost
                "Найдено сокровище: ${item.name} ценностью ${item.cost}"
            }

            else -> null
        }
    }

    private fun increasePlayerStats(agi: Int, str: Int, maxHp: Int, player: Player) {
        player.maxHealth += maxHp
        player.health += maxHp

        player.agility += agi
        player.strength += str
    }

    fun decreasePlayerStats(agi: Int, str: Int, maxHp: Int, player: Player) {
        player.maxHealth -= maxHp
        player.health = if (player.health > player.maxHealth) player.maxHealth else player.health

        player.agility -= agi
        player.strength -= str
    }
}