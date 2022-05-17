package ru.yofik.athena.common.domain.model.pagination

data class Pagination(
    val currentPage: Int,
) {
    companion object {
        const val DEFAULT_PAGE_SIZE = 10
    }
}
