package api.util

enum class EnemyType(val displayName: String) {
    ZOMBIE("Зомби"),
    VAMPIRE("Вампир"),
    GHOST("Призрак"),
    OGRE("Огр"),
    SNAKE_MAGE("Змей-маг"),
    MIMIC("Мимик");

    override fun toString(): String {
        return displayName
    }
}
