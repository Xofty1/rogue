package api.service

import api.entity.environment.Position
import api.util.factory.ItemFactory
import api.util.item_type.ElixirType

class TestService {
    fun test() {
        println(ItemFactory.createElixir(ElixirType.STRENGTH_ELIXIR, Position(3, 4)))
    }
}