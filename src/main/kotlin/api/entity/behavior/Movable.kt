package api.entity.behavior

import api.entity.environment.Position

interface Movable {
    fun move(pos: Position): Position
}