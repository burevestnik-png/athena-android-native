package ru.yofik.athena.common.utils

import java.util.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * https://stackoverflow.com/questions/22463062/how-to-parse-format-dates-with-localdatetime-java-8
 */
fun LocalDateTime.toFormattedString(format: String = "HH:mm"): String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern(format, Locale("ru"))
    return this.format(dateTimeFormatter)
}
