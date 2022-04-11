package ru.yofik.athena.common.data.api.model.mappers

interface DtoMapper<E, D> {
    fun mapToDomain(entityDTO: E): D
}
