package ru.yofik.athena.common.data.cache.converters

import androidx.room.TypeConverter
import ru.yofik.athena.common.domain.model.chat.ChatType

class ChatTypeConverter {
    @TypeConverter
    fun toChatType(value: String?) = value?.let { enumValueOf<ChatType>(value) }

    @TypeConverter
    fun fromChatType(value: ChatType?) = value?.name
}
