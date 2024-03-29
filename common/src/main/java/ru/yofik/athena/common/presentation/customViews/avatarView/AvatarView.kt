package ru.yofik.athena.common.presentation.customViews.avatarView

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.appcompat.content.res.AppCompatResources
import ru.yofik.athena.common.R
import ru.yofik.athena.common.databinding.ViewAvatarBinding

class AvatarView(context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {
    companion object {
        private const val DEFAULT_TEXT = ""
        private const val DEFAULT_TEXT_SIZE = 16f
    }

    private val binding: ViewAvatarBinding

    private var textSize = DEFAULT_TEXT_SIZE
    var text = DEFAULT_TEXT
        set(value) {
            field = value
            setDefaultState()
        }

    private var state: State = State.DEFAULT

    ///////////////////////////////////////////////////////////////////////////
    // INIT
    ///////////////////////////////////////////////////////////////////////////

    init {
        binding = ViewAvatarBinding.inflate(LayoutInflater.from(context), this, true)
        setupAttributes(attrs)
        setDefaultState()
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AvatarView)

        with(typedArray) {
            text = getString(R.styleable.AvatarView_text) ?: DEFAULT_TEXT
            textSize = getDimension(R.styleable.AvatarView_textSize, DEFAULT_TEXT_SIZE)
        }

        typedArray.recycle()
    }

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun setState(state: State) =
        when (state) {
            is State.DEFAULT -> setDefaultState()
            is State.SELECTED -> setSelectedState()
        }

    private fun setDefaultState() {
        if (state is State.SELECTED) {
            binding.iconText.visibility = VISIBLE
        }

        if (text.isEmpty()) return
        state = State.DEFAULT

        binding.apply {
            iconText.apply {
                this.text = this@AvatarView.text.extractInitials()
                textSize = this@AvatarView.textSize
            }

            icon.apply {
                setColorFilter(hashBasedColor)
                setImageDrawable(bgAvatarBackground)
                background = null
            }
        }
    }

    private fun setSelectedState() {
        if (state is State.DEFAULT) {
            binding.iconText.visibility = GONE
        }

        binding.icon.apply {
            setColorFilter(purpleColor)
            setImageDrawable(checkIcon)
            background = circleBackground
        }

        state = State.SELECTED
    }

    ///////////////////////////////////////////////////////////////////////////
    // UI UTILS
    ///////////////////////////////////////////////////////////////////////////

    private val purpleColor = context.getColor(R.color.purple)
    private val whiteColor = context.getColor(R.color.white)
    private val hashBasedColor: Int
        get() = context.getColor(BackgroundGenerator.get(text))

    private val checkIcon = AppCompatResources.getDrawable(context, R.drawable.baseline_check_24)
    private val bgAvatarBackground = AppCompatResources.getDrawable(context, R.drawable.bg_avatar)

    private val circleBackground =
        GradientDrawable().apply {
            color = ColorStateList.valueOf(whiteColor)
            shape = GradientDrawable.OVAL
            setStroke(3, ColorStateList.valueOf(purpleColor))
        }

    sealed class State {
        object DEFAULT : State()
        object SELECTED : State()
    }
}

private fun String.extractInitials(): String {
    return this.trim().split(" ").joinToString("") { it.first().uppercase() }
}
