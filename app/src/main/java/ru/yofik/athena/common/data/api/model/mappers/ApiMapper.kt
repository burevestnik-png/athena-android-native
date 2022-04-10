package ru.yofik.athena.common.data.api.model.mappers

interface ApiMapper<E, D> {
    fun mapToDomain(entityDTO: E): D
}
