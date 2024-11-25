package api.entity

import api.entity.behavior.Movable
import api.entity.behavior.Placeable
import api.entity.environment.Position

abstract class Entity(
    open var health: Int,
    open var agility: Int,
    open var strength: Int,
    open val mapChar: Char,
    open val displayName: String,
) :
    Placeable,
    Movable {
    override fun move(pos: Position): Position {
        this.pos = pos
        return this.pos
    }
}