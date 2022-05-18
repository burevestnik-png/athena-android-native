package ru.yofik.athena.common.presentation.model

interface UiMapper<E, V> {
    fun mapToView(model: E): V
}
