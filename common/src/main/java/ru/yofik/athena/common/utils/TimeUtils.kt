package ru.yofik.athena.common.utils

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

object TimeUtils {
    const val FULL_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    const val SHORT_TIME_FORMAT = "HH:mm"

    fun parseToLocalDateTime(dateTimeString: String?): LocalDateTime {
        if (dateTimeString == null || dateTimeString.isEmpty()) {
            return LocalDateTime.now()
        }

        return try {
            LocalDateTime.parse(dateTimeString)
        } catch (e: Exception) {
            val dateFormatter = DateTimeFormatter.ofPattern(FULL_TIME_FORMAT)
            LocalDateTime.parse(dateTimeString, dateFormatter)
        }
    }

    fun localDateTimeToString(localDateTime: LocalDateTime, format: String = FULL_TIME_FORMAT): String {
        return localDateTime.format(DateTimeFormatter.ofPattern(format))
    }
}
