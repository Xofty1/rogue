package api.util.factory

import api.entity.environment.Position
import api.entity.item.*
import api.util.constant.*
import api.util.constant.ItemElixir.DURATION
import api.util.constant.ItemGenChance.ELIXIR
import api.util.constant.ItemGenChance.FOOD
import api.util.constant.ItemGenChance.SCROLL
import api.util.constant.ItemGenChance.TREASURE
import api.util.item_type.*

object ItemFactory {
    private fun createElixir(type: ElixirType, pos: Position): Elixir {
        val agility = if (type == ElixirType.AGILITY_ELIXIR) ItemAgility.ELIXIR else 0
        val strength = if (type == ElixirType.STRENGTH_ELIXIR) ItemStrength.ELIXIR else 0
        val maxHealth = if (type == ElixirType.MAX_HEALTH_ELIXIR) ItemMaxHealth.ELIXIR else 0

        return Elixir(
            type = type,
            name = type.value,
            pos = pos,
            agility = agility,
            strength = strength,
            maxHealth = maxHealth,
            isUsed = false,
            duration = DURATION,
        )
    }

    private fun createFood(type: FoodType, pos: Position): Food {
        val health = ItemHealth.food[type] ?: 0
        return Food(
            type = type,
            name = type.value,
            pos = pos,
            health = health
        )
    }

    private fun createTreasure(type: TreasureType, pos: Position): Treasure {
        val cost = ItemTreasure.costs[type] ?: 0
        return Treasure(
            type = type,
            name = type.value,
            pos = pos,
            cost = cost
        )
    }

    fun createWeapon(type: WeaponType, pos: Position): Weapon {
        val strength = ItemStrength.weapons[type] ?: 0
        return Weapon(
            type = type,
            name = type.value,
            pos = pos,
            strength = strength
        )
    }

    private fun createScroll(type: ScrollType, pos: Position): Scroll {
        val agility = if (type == ScrollType.AGILITY_SCROLL) ItemAgility.SCROLL else 0
        val strength = if (type == ScrollType.STRENGTH_SCROLL) ItemStrength.SCROLL else 0
        val maxHealth = if (type == ScrollType.MAX_HEALTH_SCROLL) ItemMaxHealth.SCROLL else 0

        return Scroll(
            type = type,
            name = type.value,
            pos = pos,
            agility = agility,
            strength = strength,
            maxHealth = maxHealth
        )
    }

    fun createExit(pos: Position): Exit {
        return Exit("Выход", pos)
    }

    fun createRandomItem(pos: Position): Item {
        val chance = (1..100).random()

        return when {
            chance <= TREASURE -> createTreasure(TreasureType.entries.random(), pos)
            chance <= FOOD -> createFood(FoodType.entries.random(), pos)
            chance <= SCROLL -> createScroll(ScrollType.entries.random(), pos)
            chance <= ELIXIR -> createElixir(ElixirType.entries.random(), pos)
            else -> createWeapon(WeaponType.entries.filter { it != WeaponType.UNARMED }
                .random(), pos)
        }
    }
}
