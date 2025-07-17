package helper

import kotlinx.serialization.json.Json
import org.apache.kafka.common.serialization.Deserializer

inline fun <reified T> createJsonDeserializer(
    json: Json = Json { ignoreUnknownKeys = true }
): Deserializer<T> = object : Deserializer<T> {
    override fun deserialize(topic: String, data: ByteArray?): T? {
        if (data == null) return null
        return json.decodeFromString(data.decodeToString())
    }
}