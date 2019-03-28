package ru.dimasan92.cloudnotes.ui.customviews

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.annotation.Dimension
import androidx.annotation.Dimension.DP
import androidx.core.view.children
import org.jetbrains.anko.dip
import ru.dimasan92.cloudnotes.data.model.Color
import ru.dimasan92.cloudnotes.extensions.getColorRes

private const val PALETTE_ANIMATION_DURATION = 150L
private const val HEIGHT = "height"
private const val SCALE = "scale"
@Dimension(unit = DP) private const val COLOR_VIEW_PADDING = 8

class ColorPickerView : LinearLayout {

    var onColorClickListener: (color: Color) -> Unit = {}
    val isOpen: Boolean
        get() = measuredHeight > 0

    private var desiredHeight = 0
    private val animator by lazy {
        ValueAnimator().apply {
            duration = PALETTE_ANIMATION_DURATION
            addUpdateListener(updateListener)
        }
    }

    private val updateListener by lazy {
        ValueAnimator.AnimatorUpdateListener { animator ->
            layoutParams.apply {
                height = animator.getAnimatedValue(HEIGHT) as Int
            }.let {
                layoutParams = it
            }

            val scaleFactor = animator.getAnimatedValue(SCALE) as Float
            children.forEach {
                it.scaleX = scaleFactor
                it.scaleY = scaleFactor
                it.alpha = scaleFactor
            }
        }
    }

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttrs: Int)
            : super(context, attrs, defStyleAttrs) {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER

        Color.values().forEach { color ->
            addView(
                ColorCircleView(context).apply {
                    fillColorRes = color.getColorRes()
                    tag = color
                    dip(COLOR_VIEW_PADDING).let {
                        setPadding(it, it, it, it)
                    }
                    setOnClickListener { onColorClickListener(it.tag as Color) }
                }
            )
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        layoutParams.apply {
            desiredHeight = height
            height = 0
        }.let {
            layoutParams = it
        }
    }

    fun open() {
        animator.cancel()
        animator.setValues(
            PropertyValuesHolder.ofInt(HEIGHT, measuredHeight, desiredHeight),
            PropertyValuesHolder.ofFloat(SCALE, getChildAt(0).scaleX, 1f)
        )
        animator.start()
    }

    fun close() {
        animator.cancel()
        animator.setValues(
            PropertyValuesHolder.ofInt(HEIGHT, measuredHeight, 0),
            PropertyValuesHolder.ofFloat(SCALE, getChildAt(0).scaleX, 0f)
        )
        animator.start()

    }
}