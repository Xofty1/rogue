package api.service.game_mechanic

import api.entity.Entity
import api.entity.Player
import api.entity.enemy.SnakeMage
import api.entity.enemy.Vampire
import api.service.updater.StateUpdater
import api.util.constant.SLEEP_CHANCE
import api.util.constant.VAMPIRE_DAMAGE_DIVISOR
import kotlin.random.Random

object FightService {
    private val stateUpdater = StateUpdater

    fun aggressiveAction(attacker: Entity, defender: Entity): List<String> {
        val resultAction = mutableListOf<String>()

        stateUpdater.updateGhostsAndOgresWhenAggressiveAction(attacker)

        return if (checkHitChance(attacker, defender)) {
            val damage = dealDamage(attacker, defender)

            resultAction.add(
                "${attacker.displayName} попадает по ${defender.displayName} и наносит $damage урона"
            )

            if (attacker is SnakeMage && defender is Player && Random.nextDouble() < SLEEP_CHANCE) {
                defender.isSleeping = true
                resultAction.add("${defender.displayName} засыпает")
            }

            if (attacker is Vampire && defender is Player) {
                val damageMaxHealth = (damage / VAMPIRE_DAMAGE_DIVISOR).coerceAtLeast(1)
                defender.maxHealth -= damageMaxHealth
                resultAction.add(
                    "${attacker.displayName} снижает максимальное хп " +
                            "${defender.displayName} на $damageMaxHealth."
                )
            }

            resultAction
        } else {
            listOf("${attacker.displayName} промахивается по ${defender.displayName}")
        }
    }

    private fun dealDamage(attacker: Entity, defender: Entity): Int {
        val remindedHealth = defender.health - attacker.strength
        defender.health = if (remindedHealth > 0) remindedHealth else 0

        return attacker.strength
    }

    private fun checkHitChance(attacker: Entity, defender: Entity): Boolean {
        if (defender is Vampire && defender.isProtected) {
            defender.isProtected = false
            return false
        }

        val hitChance = attacker.agility.toDouble() / (attacker.agility + defender.agility)
        return Random.nextDouble() < hitChance
    }
}
