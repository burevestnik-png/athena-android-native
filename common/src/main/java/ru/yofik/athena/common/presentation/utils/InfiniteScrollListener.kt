package ru.yofik.athena.common.presentation.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

abstract class InfiniteScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val pageSize: Int,
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        Timber.d(
            "Visible=$visibleItemCount; Total=$totalItemCount; First=$firstVisibleItemPosition IsLoading=${isLoading()}; IsLast=${isLastPage()}"
        )


        if (!isLoading() && !isLastPage()) {
            /*if (
                (visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                firstVisibleItemPosition >= 0 &&
                totalItemCount >= pageSize
            ) {
                Timber.d("loadMoreItems")
                loadMoreItems()
            }*/
            if (
                (visibleItemCount + (totalItemCount - firstVisibleItemPosition)) >= totalItemCount &&
                firstVisibleItemPosition >= 0 &&
                totalItemCount >= pageSize
            ) {
                Timber.d("loadMoreItems")
                loadMoreItems()
            }
        }

//        Timber.d("  \n")

    }

    abstract fun loadMoreItems()

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean
}
