package api.util.factory

import api.entity.Backpack
import api.entity.Player
import api.entity.environment.Position
import api.entity.item.Weapon
import api.util.constant.PlayerStats

object PlayerFactory {
    fun createPlayer(backpack: Backpack, weapon: Weapon, pos: Position): Player {
        return Player(
            health = PlayerStats.HEALTH,
            agility = PlayerStats.AGILITY,
            strength = PlayerStats.STRENGTH,
            pos = pos,
            maxHealth = PlayerStats.HEALTH,
            backpack = backpack,
            equippedWeapon = weapon
        )
    }
}