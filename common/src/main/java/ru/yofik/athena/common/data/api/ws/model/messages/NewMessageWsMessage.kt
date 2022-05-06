package ru.yofik.athena.common.data.api.ws.model.messages

import com.squareup.moshi.*
import ru.yofik.athena.common.data.api.http.model.chat.responses.dto.MessageDto
import ru.yofik.athena.common.data.api.ws.model.ArgumentType
import ru.yofik.athena.common.data.api.ws.model.CommandType

@JsonClass(generateAdapter = false)
data class NewMessageWsMessage(
    @field:Json(name = "type") val type: ArgumentType,
    @field:Json(name = "message") val message: MessageDto
)

@JsonClass(generateAdapter = true)
data class JsonNewMessage(
    @field:Json(name = "command") val command: CommandType,
    @field:Json(name = "argument") val argument: NewMessageWsMessage
)

class NewMessageWsMessageAdapter : JsonAdapter<NewMessageWsMessage>() {
    @ToJson
    fun toJson(message: NewMessageWsMessage): JsonNewMessage {
        throw UnsupportedOperationException()
    }

    @FromJson
    fun fromJson(json: JsonNewMessage): NewMessageWsMessage {
        println(json)
        return json.argument.copy()
    }

    companion object {
        fun build(): JsonAdapter<NewMessageWsMessage> {
            return Moshi.Builder().add(NewMessageWsMessageAdapter()).build()
                .adapter(NewMessageWsMessage::class.java)
        }
    }

    override fun toJson(writer: JsonWriter, value: NewMessageWsMessage?) {
        TODO("Not yet implemented")
    }

    override fun fromJson(reader: JsonReader): NewMessageWsMessage? {
        TODO("Not yet implemented")
    }
}

val a = """
    {
      "command": "RECEIVE_NOTIFICATION",
      "argument": {
        "type": "NEW_MESSAGE",
        "message": {
          "id": 1,
          "text": "Hi!",
          "senderId": 14,
          "chatId": 57,
          "creationDate": "2022-05-03T11:45:31.836541",
          "modificationDate": "2022-05-03T11:45:31.836541"
        }
      }
    }
""".trimIndent()

fun main() {
    val adapter = NewMessageWsMessageAdapter.build()
    val b = adapter.fromJson(a)
    println(b)
}
