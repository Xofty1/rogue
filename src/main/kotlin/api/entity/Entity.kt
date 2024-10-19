package api.entity

import api.entity.behavior.Movable
import api.entity.behavior.Placeable

abstract class Entity(open var health: Int, open var agility: Int, open var strength: Int) :
    Placeable,
    Movable