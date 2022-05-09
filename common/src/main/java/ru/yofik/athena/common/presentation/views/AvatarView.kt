package ru.yofik.athena.common.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import ru.yofik.athena.common.R
import ru.yofik.athena.common.databinding.ViewAvatarBinding

class AvatarView(context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    companion object {
        private const val DEFAULT_TEXT = ""
        private const val DEFAULT_TEXT_SIZE = 16f
    }

    private var text = DEFAULT_TEXT
    private var textSize = DEFAULT_TEXT_SIZE

    private val binding: ViewAvatarBinding

    init {
        binding = ViewAvatarBinding.inflate(LayoutInflater.from(context), this, false)

        setupAttributes(attrs)
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
        binding.iconText.apply {
            text = this@AvatarView.text
            textSize = this@AvatarView.textSize
        }
    }
}