package ru.yofik.athena.common.domain.model.pagination

data class Pagination(
    val currentPage: Int,
    val currentAmountOfItems: Int,
    val pageSize: Int = DEFAULT_PAGE_SIZE
) {
    companion object {
        const val DEFAULT_PAGE_SIZE = 3
    }

    val canLoadMore: Boolean
        get() = currentAmountOfItems == pageSize
}
