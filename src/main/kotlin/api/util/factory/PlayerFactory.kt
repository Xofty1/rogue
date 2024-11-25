package api.util.factory

import api.entity.Backpack
import api.entity.Player
import api.entity.environment.Position
import api.entity.item.Weapon
import api.util.constant.MapChar.PLAYER
import api.util.constant.PlayerStats

object PlayerFactory {
    fun createPlayer(backpack: Backpack, weapon: Weapon, pos: Position): Player {
        return Player(
            mapChar = PLAYER,
            health = PlayerStats.HEALTH,
            agility = PlayerStats.AGILITY,
            strength = PlayerStats.STRENGTH,
            rating = 0,
            isSleeping = false,
            pos = pos,
            maxHealth = PlayerStats.HEALTH,
            visionRange = PlayerStats.VISION_RANGE,
            backpack = backpack,
            displayName = "Приключенец",
            equippedWeapon = weapon
        )
    }
}