package ru.yofik.athena.common.domain.repositories

interface CommonRepository {
    suspend fun removeAllCache()
}