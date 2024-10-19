package api.entity.item

import api.entity.behavior.Placeable

abstract class Item(open val name: String) : Placeable
