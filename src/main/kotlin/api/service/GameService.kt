package api.service

import api.entity.Backpack
import api.entity.Player
import api.entity.environment.DungeonMap
import api.entity.environment.Position
import api.service.convertor.EnvConverter
import api.service.generator.ContentGenerator
import api.service.generator.EnvGenerator
import api.util.factory.ItemFactory
import api.util.factory.PlayerFactory
import api.util.item_type.WeaponType
import data.storage.GameHistoryStorage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object GameService {
    private val envGenerator = EnvGenerator
    private val contentGenerator = ContentGenerator
    private val envConverter = EnvConverter
    private val playerFactory = PlayerFactory
    private val itemFactory = ItemFactory
    private val gameHistoryStorage = GameHistoryStorage
    private const val GAME_HISTORY_FILE_PATH = "./save.json"
    private const val TABLE_RECORDS_FILE_PATH = "./records.txt"

    fun startGame(): DungeonMap {
        val dungeon = envGenerator.generateDungeon()

        val startWeapon = itemFactory.createWeapon(WeaponType.UNARMED, Position())
        val player = playerFactory.createPlayer(Backpack(), startWeapon, Position())

        contentGenerator.generatePlayerPos(dungeon.roomSequence, player)
        contentGenerator.generateContent(dungeon.roomSequence, player.pos)

        val dungeonMap = envConverter.dungeonToMap(dungeon)
        return dungeonMap
    }

    fun nextDungeon(player: Player, prevDungeonLevel: Int): DungeonMap {
        val nextDungeonLevel = prevDungeonLevel + 1
        val nextDungeon = envGenerator.generateDungeon()

        contentGenerator.generatePlayerPos(nextDungeon.roomSequence, player)
        contentGenerator.generateContent(nextDungeon.roomSequence, player.pos, nextDungeonLevel)

        val dungeonMap = envConverter.dungeonToMap(nextDungeon)
        dungeonMap.dungeonLevel = nextDungeonLevel

        return dungeonMap
    }

    fun saveGame(dungeon: DungeonMap) {
        gameHistoryStorage.saveToFile(dungeon, GAME_HISTORY_FILE_PATH)
    }

    fun loadGame(): DungeonMap? {
        return gameHistoryStorage.loadFromFile(GAME_HISTORY_FILE_PATH)
    }

    fun saveRecord(dungeon: DungeonMap) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val date = LocalDateTime.now().format(formatter)
        val player = dungeon.player as Player
        val record = "Дата: $date. Достигнут уровень подземелья: ${dungeon.dungeonLevel}. " +
                "Найдено сокровищ: ${player.rating}. "
        gameHistoryStorage.saveRecord(record, TABLE_RECORDS_FILE_PATH)
    }

    fun loadRecords(): List<String> {
        return gameHistoryStorage.loadRecords(TABLE_RECORDS_FILE_PATH)
    }
}