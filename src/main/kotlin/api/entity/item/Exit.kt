package api.entity.item

import api.entity.environment.Position

data class Exit(override val name: String, override var pos: Position) : Item(name)