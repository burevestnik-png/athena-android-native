package ru.yofik.athena.common.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import ru.yofik.athena.common.R
import ru.yofik.athena.common.databinding.ViewAvatarBinding
import timber.log.Timber
import java.util.*
import kotlin.math.absoluteValue

class AvatarView(context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    companion object {
        private const val DEFAULT_TEXT = ""
        private const val DEFAULT_TEXT_SIZE = 16f
    }

    private var text = DEFAULT_TEXT
    private var textSize = DEFAULT_TEXT_SIZE

    private val binding: ViewAvatarBinding

    init {
        binding = ViewAvatarBinding.inflate(LayoutInflater.from(context), this, true)

        setupAttributes(attrs)
        setupUI()
    }

    fun setText(text: String) {
        this.text = text
        setupUI()
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AvatarView)

        with(typedArray) {
            text = getString(R.styleable.AvatarView_text) ?: DEFAULT_TEXT
            textSize = getDimension(R.styleable.AvatarView_textSize, DEFAULT_TEXT_SIZE)
        }

        typedArray.recycle()
    }

    private fun setupUI() {
        Timber.d("setupUI: $text")
        if (text.isEmpty()) return

        binding.iconText.apply {
            text = extractInitials(this@AvatarView.text)
            textSize = this@AvatarView.textSize
        }

        binding.icon.apply { setColorFilter(context.getColor(BackgroundGenerator.get(text))) }
    }
}

private fun extractInitials(name: String): String {
    return name.trim().split(" ").joinToString("") { it.first().uppercase() }
}

object BackgroundGenerator {
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
