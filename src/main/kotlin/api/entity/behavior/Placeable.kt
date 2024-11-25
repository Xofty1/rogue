package api.entity.behavior

import api.entity.Player
import api.entity.enemy.*
import api.entity.environment.Position
import api.entity.item.*
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = Enemy::class, name = "enemy"),
    JsonSubTypes.Type(value = Item::class, name = "item"),
    JsonSubTypes.Type(value = Player::class, name = "player"),
    JsonSubTypes.Type(value = Ghost::class, name = "ghost"),
    JsonSubTypes.Type(value = Zombie::class, name = "zombie"),
    JsonSubTypes.Type(value = Vampire::class, name = "vampire"),
    JsonSubTypes.Type(value = Ogre::class, name = "ogre"),
    JsonSubTypes.Type(value = SnakeMage::class, name = "snake-mage"),
    JsonSubTypes.Type(value = Mimic::class, name = "mimic"),
    JsonSubTypes.Type(value = Exit::class, name = "exit"),
    JsonSubTypes.Type(value = Food::class, name = "food"),
    JsonSubTypes.Type(value = Elixir::class, name = "elixir"),
    JsonSubTypes.Type(value = Scroll::class, name = "scroll"),
    JsonSubTypes.Type(value = Weapon::class, name = "weapon"),
    JsonSubTypes.Type(value = Treasure::class, name = "treasure"),
)
interface Placeable {
    var pos: Position
}
