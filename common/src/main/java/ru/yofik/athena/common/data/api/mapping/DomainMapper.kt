package ru.yofik.athena.common.data.api.mapping

interface DomainMapper<E, D> {
    fun mapToApi(domainModel: E): D
}
