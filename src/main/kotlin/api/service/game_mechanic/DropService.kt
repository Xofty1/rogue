package api.service.game_mechanic

import api.entity.enemy.Enemy
import api.util.constant.MAX_VARIATION
import api.util.constant.MIN_VARIATION
import api.util.constant.StatCoefs.AGILITY_COEF
import api.util.constant.StatCoefs.HEALTH_COEF
import api.util.constant.StatCoefs.HOSTILITY_COEF
import api.util.constant.StatCoefs.STRENGTH_COEF
import kotlin.random.Random

object DropService {
    fun calculateTreasureDropFromEnemy(enemy: Enemy): Int {
        val hostilityFactor = enemy.hostility * HOSTILITY_COEF
        val strengthFactor = enemy.strength * STRENGTH_COEF
        val agilityFactor = enemy.agility * AGILITY_COEF
        val healthFactor = enemy.health * HEALTH_COEF

        val baseTreasure = (hostilityFactor + strengthFactor + agilityFactor + healthFactor).toInt()

        val randomVariation = Random.nextInt(MIN_VARIATION, MAX_VARIATION)

        return (baseTreasure + randomVariation).coerceAtLeast(0)
    }
}
