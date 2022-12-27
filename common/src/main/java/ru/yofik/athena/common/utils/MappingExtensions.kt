package ru.yofik.athena.common.utils

import ru.yofik.athena.common.data.api.common.MappingException

fun <T> T?.getOrThrowMappingException(field: String): T =
    this ?: throw MappingException("$field cant be null")
