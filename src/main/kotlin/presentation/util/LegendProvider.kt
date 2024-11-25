package presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

object LegendProvider {

    @Composable
    fun getLegend(): List<Legend> {
        return listOf(
            Legend(
                entityType = GameEntity.CONTROL,
                title = "Перемещение",
                icon = painterResource("icons/buttons/w_key.png"),
                description = "Движение вверх."
            ),
            Legend(
                entityType = GameEntity.CONTROL,
                title = "Перемещение",
                icon = painterResource("icons/buttons/s_key.png"),
                description = "Движение вниз."
            ),
            Legend(
                entityType = GameEntity.CONTROL,
                title = "Перемещение",
                icon = painterResource("icons/buttons/a_key.png"),
                description = "Движение влево."
            ),
            Legend(
                entityType = GameEntity.CONTROL,
                title = "Перемещение",
                icon = painterResource("icons/buttons/d_key.png"),
                description = "Движение вправо."
            ),
            Legend(
                entityType = GameEntity.CONTROL,
                title = "Рекорды",
                icon = painterResource("icons/buttons/t_key.png"),
                description = "Статистика забегов."
            ),
            Legend(
                entityType = GameEntity.CONTROL,
                title = "Инфо",
                icon = painterResource("icons/buttons/tab_key.png"),
                description = "Не нужно звонить Малдеру и Скалли, если встретил что-то непонятное. Просто прочитай"
            ),
            Legend(
                entityType = GameEntity.CONTROL,
                title = "Рюкзак",
                icon = painterResource("icons/buttons/r_key.png"),
                description = "Ну где там мои манаточки."
            ),
            Legend(
                entityType = GameEntity.CONTROL,
                title = "Рестарт",
                icon = painterResource("icons/buttons/r_key.png"),
                description = "Перезапускает игровую сессию."
            ),
            Legend(
                entityType = GameEntity.CONTROL,
                title = "Закрыть",
                icon = painterResource("icons/buttons/esc_key.png"),
                description = "Закрыть открытое окошко."
            ),
            Legend(
                entityType = GameEntity.HERO,
                title = "Это вы!",
                icon = painterResource("icons/creatures/hero.png"),
                description = "Вы решили покорить подземелье, боги восхищаются вашей отвагой. Но ваша цель - не принцесса. Сокровища!" +
                        "\n\"За глаза меня кличут вором. Рад представиться - Индиана Джонс\""
            ),
            Legend(
                entityType = GameEntity.ENEMY,
                title = "Зомби",
                icon = painterResource("icons/creatures/zombie.png"),
                description = "Охотится только за интеллектуалами и теми кто подойдет слишком близко." +
                        "\n\"Бр-е-е-е-е-е-йнс, э-э-э, бр-е-е-е-е-е-йнс.\""
            ),
            Legend(
                entityType = GameEntity.ENEMY,
                title = "Призрак",
                icon = painterResource("icons/creatures/ghost.png"),
                description = "Призрак обожает пугать неопытных искателей приключений и появляясь и исчезая в самый неожиданный момент." +
                        "\n\"Б-у-у-у-у-у!\""
            ),
            Legend(
                entityType = GameEntity.ENEMY,
                title = "Вампир",
                icon = painterResource("icons/creatures/vampire.png"),
                description = "Благодаря своим неестественным способностям всегда готов к нападению. Первый удар по вампиру всегда провальный." +
                        "\n\"Ты — мой личный сорт героина.\""
            ),
            Legend(
                entityType = GameEntity.ENEMY,
                title = "Огр",
                icon = painterResource("icons/creatures/ogre.png"),
                description = "Обладает крепким здоровьем и не менее крепкой самоуверенностью, благодаря этому может атаковать дважды." +
                        "\n\"Здесь всё гораздо сложнее, чем просто луковицы.\""
            ),
            Legend(
                entityType = GameEntity.ENEMY,
                title = "Змее-маг",
                icon = painterResource("icons/creatures/mage.png"),
                description = "Владеет могущественной и запрещенной магией. Может оглушить игрока во время своей атаки." +
                        "\n\"Говорила мама - учи котлин, зачем тебе этот питон\""
            ),
            Legend(
                entityType = GameEntity.ENEMY,
                title = "Мимик",
                icon = painterResource("icons/creatures/mimic.png"),
                description = "Объект № [УДАЛЕНО]: [ДАННЫЕ СКРЫТЫ]. Принимает формы, внушающие [ДОСТУП ЗАКРЫТ ПО СОБСТВЕННОЙ БЕЗОПАСНОСТИ]." +
                        "\n\"Я тучка, тучка, тучка, я вовсе не медведь!\""
            ),
            Legend(
                entityType = GameEntity.ENVIRONMENT,
                title = "Сундук с сокровищами",
                icon = painterResource("icons/items/treasure.png"),
                description = "Бабки, бабки, с#ка, бабки!"
            ),
            Legend(
                entityType = GameEntity.ENVIRONMENT,
                title = "Лестница на следующий этаж",
                icon = painterResource("icons/environment/exit.png"),
                description = "Кто знает, что там на следующем этаже. Будьте осторожны. "
            ),
            Legend(
                entityType = GameEntity.ENVIRONMENT,
                title = "Стена",
                icon = painterResource("icons/environment/wall.png"),
                description = "Можно постучать, но вам не ответят."
            ),
            Legend(
                entityType = GameEntity.ENVIRONMENT,
                title = "Коридор",
                icon = painterResource("icons/environment/corridor.png"),
                description = "В этом подземелье множество узких и неудобных коридоров. Но будьте осторожны, неведомые твари могут последовать за вами."
            ),
            Legend(
                entityType = GameEntity.FOOD,
                title = "Яблоко",
                icon = painterResource("icons/items/food/apple.png"),
                description = "Надеюсь, его не мачеха тут обронила. \nВосстанавливает 5 хп."
            ),
            Legend(
                entityType = GameEntity.FOOD,
                title = "Хлеб",
                icon = painterResource("icons/items/food/bread.png"),
                description = "Без хлеба сыт не будешь. \nВосстанавливает 10 хп."
            ),
            Legend(
                entityType = GameEntity.FOOD,
                title = "Мясо",
                icon = painterResource("icons/items/food/meat.png"),
                description = "Давай мясо, давай мясо! \nВосстанавливает 15 хп."
            ),
            Legend(
                entityType = GameEntity.FOOD,
                title = "Пиво",
                icon = painterResource("icons/items/food/beer.png"),
                description = "Пивка для рывка. \nВосстанавливает 18 хп."

            ),
            Legend(
                entityType = GameEntity.ELIXIR,
                title = "Сыр",
                icon = painterResource("icons/items/food/cheese.png"),
                description = "Ч-и-и-и-и-и-и-и-и-з. \nВосстанавливает 20 хп."

            ),
            Legend(
                entityType = GameEntity.ELIXIR,
                title = "Эликсир силы",
                icon = painterResource("icons/items/elixirs/elixir_str.png"),
                description = "Увеличивает силу на 10 на 10 ходов."
            ),
            Legend(
                entityType = GameEntity.ELIXIR,
                title = "Эликсир здоровья",
                icon = painterResource("icons/items/elixirs/elixir_mhp.png"),
                description = "Увеличивает максимальное здоровье на 10 на 10 ходов."
            ),
            Legend(
                entityType = GameEntity.ELIXIR,
                title = "Эликсир ловкости",
                icon = painterResource("icons/items/elixirs/elixir_agi.png"),
                description = "Увеличивает ловкость на 10 на 10 ходов."
            ),
            Legend(
                entityType = GameEntity.SCROLL,
                title = "Свиток силы",
                icon = painterResource("icons/items/scrolls/scroll_str.png"),
                description = "Как дела в качалке, пацаны? \nНавсегда увеличивает силу на 5 единиц."
            ),
            Legend(
                entityType = GameEntity.SCROLL,
                title = "Свиток здоровья",
                icon = painterResource("icons/items/scrolls/scroll_mhp.png"),
                description = "Береги платье снову, а здоровье смолоду. \nНавсегда увеличивает максимальное здоровье на 5 единиц."
            ),
            Legend(
                entityType = GameEntity.SCROLL,
                title = "Свиток ловкости",
                icon = painterResource("icons/items/scrolls/scroll_agi.png"),
                description = "Блиц, блиц, скорость без границ! \nНавсегда увеличивает ловкость на 5 единиц."
            ),
            Legend(
                entityType = GameEntity.WEAPON,
                title = "Палка",
                icon = painterResource("icons/items/weapons/stick.png"),
                description = "Простая палка, всяко лучше чем кулаками. Добавляет +2 к урону."
            ),
            Legend(
                entityType = GameEntity.WEAPON,
                title = "Кинжал",
                icon = painterResource("icons/items/weapons/knife.png"),
                description = "Кинжал. Добавляет +7 к урону."
            ),
            Legend(
                entityType = GameEntity.WEAPON,
                title = "Меч",
                icon = painterResource("icons/items/weapons/sword.png"),
                description = "Солдатский меч. Добавляет +10 к урону."
            ),
            Legend(
                entityType = GameEntity.WEAPON,
                title = "Топор",
                icon = painterResource("icons/items/weapons/axe.png"),
                description = "Топор дровосека. Добавляет +15 к урону."
            ),
            Legend(
                entityType = GameEntity.WEAPON,
                title = "Эпический меч",
                icon = painterResource("icons/items/weapons/epic_sword.png"),
                description = "Меч великого героя. Добавляет +20 к урону."
            )
        )
    }
}