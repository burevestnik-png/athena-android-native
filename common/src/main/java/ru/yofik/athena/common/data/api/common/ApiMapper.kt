package ru.yofik.athena.common.data.api.common

interface ApiMapper<in From, out To> {
    fun mapToDomain(apiEntity: From?): To
}
