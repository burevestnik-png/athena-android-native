package ru.yofik.athena.common.data.api.mappers

interface ApiMapper<in From, out To> {
    fun mapToDomain(apiEntity: From?): To
}
