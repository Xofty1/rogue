package data.storage

import api.entity.environment.DungeonMap
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.io.IOException

object GameHistoryStorage {
    private val mapper = jacksonObjectMapper()

    fun saveToFile(dungeonMap: DungeonMap, filePath: String): Boolean {
        return try {
            val jsonString = mapper.writeValueAsString(dungeonMap)
            File(filePath).writeText(jsonString)
            true
        } catch (e: IOException) {
            println("Error saving file: ${e.message}")
            false
        }
    }

    fun loadFromFile(filePath: String): DungeonMap? {
        return try {
            val jsonString = File(filePath).readText()
            mapper.readValue<DungeonMap>(jsonString)
        } catch (e: IOException) {
            println("Error reading file: ${e.message}")
            null
        } catch (e: Exception) {
            println("Error deserializing file: ${e.message}")
            null
        }
    }

    fun saveRecord(record: String, filePath: String): Boolean {
        return try {
            File(filePath).appendText(record + System.lineSeparator())
            true
        } catch (e: IOException) {
            println("Error saving record: ${e.message}")
            false
        }
    }

    fun loadRecords(filePath: String, limit: Int = 10): List<String> {
        return try {
            File(filePath).useLines { lines ->
                lines.toList().takeLast(limit)
            }
        } catch (e: IOException) {
            println("Error reading records: ${e.message}")
            emptyList()
        }
    }
}
