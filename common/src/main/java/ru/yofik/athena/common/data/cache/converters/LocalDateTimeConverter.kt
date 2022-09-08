package ru.yofik.athena.common.data.cache.converters

import androidx.room.TypeConverter
import org.threeten.bp.LocalDateTime
import ru.yofik.athena.common.utils.TimeUtils
import ru.yofik.athena.common.utils.toFormattedString

class LocalDateTimeConverter {

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { TimeUtils.parseToLocalDateTime(it) }
    }

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        return value?.let { TimeUtils.localDateTimeToString(it) }
    }
}
