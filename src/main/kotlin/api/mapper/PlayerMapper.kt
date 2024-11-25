package api.mapper

import api.entity.Backpack
import api.entity.Player
import controller.dto.PlayerDto

object PlayerMapper {
    fun Player.toDto(): PlayerDto {
        return PlayerDto(
            health = this.health,
            agility = this.agility,
            strength = this.strength,
            rating = this.rating,
            visionRange = this.visionRange,
            maxHealth = this.maxHealth,
            backpack = deepCopyBackpack(this.backpack),
            equippedWeapon = this.equippedWeapon.name
        )
    }

    private fun deepCopyBackpack(backpack: Backpack): Backpack {
        return Backpack(
            weapons = backpack.weapons.copyOf(),
            scrolls = backpack.scrolls.copyOf(),
            elixirs = backpack.elixirs.copyOf(),
            foods = backpack.foods.copyOf()
        )
    }
}
