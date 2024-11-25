package controller.dto

import api.entity.Backpack

data class PlayerDto(
    val health: Int,
    val agility: Int,
    val strength: Int,
    val maxHealth: Int,
    val visionRange: Int,
    val rating: Int,
    val backpack: Backpack,
    val equippedWeapon: String,
)
