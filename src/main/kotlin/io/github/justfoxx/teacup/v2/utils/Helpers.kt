package io.github.justfoxx.teacup.v2.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Path

val GSON: Gson = GsonBuilder().setPrettyPrinting().create()

/**
 * Reads a JSON object from a file.
 *
 * @return The JSON object.
 * @throws FileNotFoundException If the file is not found.
 */
@Throws(FileNotFoundException::class)
fun Path.readJsonObject(): JsonObject {
    val file = this.toFile()
    return file.jsonReader()
}

/**
 * Writes a JSON object to a file.
 *
 * @param data The JSON object to write to the file.
 * @return The JSON object.
 * @throws Exception If the file is not found or if the data cannot be written to the file.
 */
@Throws(Exception::class)
fun Path.writeByJsonObject(data: JsonObject): JsonObject {
    val file = this.toFile()
    return file.jsonWriter(data)
}

/**
 * Converts an object to a JSON object and writes it to a file.
 *
 * @param data The object to convert to a JSON object and write to the file.
 * @return The JSON object.
 * @throws Exception If the file is not found or if the data cannot be written to the file.
 */
@Throws(Exception::class)
fun <Object> Path.writeObject(data: Object): JsonObject {
    val jsonObject = GSON.toJsonTree(data).asJsonObject
    return this.writeByJsonObject(jsonObject)
}

/**
 * Gets a default JSON object from a file. If the file does not exist, writes the default object to the file.
 *
 * @param defaultObject The default object to write to the file if the file does not exist.
 * @return The JSON object.
 * @throws Exception If the file is not found or if the data cannot be written to the file.
 */
@Throws(Exception::class)
fun <Object> Path.getDefaultJsonObject(defaultObject: Object?): JsonObject {
    return if (defaultObject != null)
        this.writeObject(defaultObject)
    else
        JsonObject()
}

private fun File.jsonReader(): JsonObject {
    val reader = JsonReader(FileReader(this))
    return JsonParser.parseReader(reader).asJsonObject
}

private fun File.jsonWriter(data: JsonObject): JsonObject {
    Files.writeString(this.toPath(), GSON.toJson(data))
    return this.jsonReader()
}

@Suppress("unused")
fun String.formater(format: HashMap<String,Any>): String {
    var result = this
    for (pair in format) {
        result = result.replace(pair.key, pair.value.toString())
    }
    return result
}