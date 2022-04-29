package ru.yofik.athena.common.data.api.mapping

interface ApiMapper<E, D> {
    fun mapToDomain(entityDTO: E?): D
}
