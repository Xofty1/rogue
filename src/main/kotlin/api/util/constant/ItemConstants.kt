package api.util.constant

import api.util.item_type.FoodType
import api.util.item_type.TreasureType
import api.util.item_type.WeaponType

object ItemStrength {
    val weapons = mapOf(
        WeaponType.EPIC_SWORD to 25,
        WeaponType.AXE to 20,
        WeaponType.SWORD to 15,
        WeaponType.KNIFE to 12,
        WeaponType.STICK to 7,
        WeaponType.UNARMED to 5,
    )

    const val ELIXIR = 15
    const val SCROLL = 5
}

object ItemAgility {
    const val ELIXIR = 15
    const val SCROLL = 5
}

object ItemHealth {
    val food = mapOf(
        FoodType.APPLE to 20,
        FoodType.BREAD to 35,
        FoodType.MEAT to 85,
        FoodType.BEER to 50,
        FoodType.CHEESE to 90,
    )
}

object ItemMaxHealth {
    const val ELIXIR = 30
    const val SCROLL = 5
}

object ItemTreasure {
    val costs = mapOf(
        TreasureType.GOLD_TREASURE to 100,
        TreasureType.GEMS_TREASURE to 200,
        TreasureType.ARTIFACT_TREASURE to 300,
    )
}

object ItemElixir {
    const val DURATION = 30
}