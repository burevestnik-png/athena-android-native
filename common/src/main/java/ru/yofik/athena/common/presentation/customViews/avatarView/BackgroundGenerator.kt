package ru.yofik.athena.common.presentation.customViews.avatarView

import java.util.*
import kotlin.math.absoluteValue
import ru.yofik.athena.common.R

internal object BackgroundGenerator {
    private val colors =
        listOf(
            R.color.cyan,
            R.color.teal,
            R.color.yellow,
            R.color.amber,
            R.color.deep_purple,
            R.color.purple,
            R.color.green
        )

    private val size
        get() = colors.size

    fun get(name: String): Int {
        return colors[getIndex(name)]
    }

    private fun getIndex(name: String): Int {
        return Objects.hash(name).absoluteValue % size
    }
}
