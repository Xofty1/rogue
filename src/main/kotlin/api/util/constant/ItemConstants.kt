package api.util.constant

import api.util.item_type.FoodType
import api.util.item_type.TreasureType
import api.util.item_type.WeaponType

object ItemStrength {
    val weapons = mapOf(
        WeaponType.SWORD to 15,
        WeaponType.AXE to 20,
        WeaponType.KNIFE to 12,
        WeaponType.UNARMED to 5,
    )

    const val ELIXIR = 10
    const val SCROLL = 5
}

object ItemAgility {
    const val ELIXIR = 10
    const val SCROLL = 5
}

object ItemHealth {
    val food = mapOf(
        FoodType.APPLE to 5,
        FoodType.BREAD to 10,
        FoodType.MEAT to 15,
    )
}

object ItemMaxHealth {
    const val ELIXIR = 10
    const val SCROLL = 5
}

object ItemTreasure {
    val costs = mapOf(
        TreasureType.GOLD_TREASURE to 100,
        TreasureType.GEMS_TREASURE to 200,
        TreasureType.ARTIFACT_TREASURE to 300,
    )
}