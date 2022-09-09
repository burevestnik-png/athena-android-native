package ru.yofik.athena.common.data

import kotlinx.coroutines.flow.flow
import java.util.*
import java.util.concurrent.TimeUnit

class Resource<in Input, out Output>(
    private val remoteFetch: suspend (Input) -> Output,
    private val cacheFetch: suspend (Input) -> Output,
    private val storeCache: suspend (Output) -> Unit,
    private val deleteCache: suspend () -> Unit,
    private val refreshStrategy: RefreshStrategy = NoRefreshStrategy()
) {
    suspend fun query(args: Input, force: Boolean = false) =
        flow<Output> {
            if (!force) {
            }

            if (force || refreshStrategy.isExpired()) {
                kotlin.runCatching {}
            }
        }
}

sealed interface RefreshStrategy {
    fun refresh()
    fun isExpired(): Boolean
}

class NoRefreshStrategy : RefreshStrategy {
    override fun refresh() {}
    override fun isExpired() = true
}

class PeriodicRefreshStrategy(private val refreshRate: Long = TimeUnit.MINUTES.toMillis(1)) :
    RefreshStrategy {
    private var lastUpdate = Date()

    override fun refresh() {
        lastUpdate = Date()
    }

    override fun isExpired(): Boolean {
        return (Date().time - lastUpdate.time) > refreshRate
    }
}
