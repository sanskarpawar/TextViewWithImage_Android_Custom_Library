package com.sanskarpawar.textimageview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.DynamicDrawableSpan
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat

/**
 * A custom TextView that allows inserting image before or inside the text.
 */
class ImageTextView : AppCompatTextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    init {
        // set the top padding to 6dp
        setPadding(
            paddingLeft,
            resources.getDimensionPixelSize(R.dimen.dp_6),
            paddingRight,
            paddingBottom
        )
    }

    /**
     * Inserts an image before a given word in the text.
     *
     * @param drawableRes The resource ID of the drawable to insert.
     * @param word The word before which to insert the image.
     */
    fun insertImageBeforeWord(@DrawableRes drawableRes: Int, word: String) {
        val textString = text.toString()

        val startIndex = textString.indexOf(word)

        if (startIndex != -1) {
            val spannableStringBuilder = SpannableStringBuilder(text)
            val drawable = ContextCompat.getDrawable(context, drawableRes) ?: return
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            val imageSpan = CenteredImageSpan(drawable)
            spannableStringBuilder.insert(startIndex, "  ")
            spannableStringBuilder.setSpan(
                imageSpan,
                startIndex,
                startIndex + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            text = spannableStringBuilder
        }
    }

    /**
     * A custom DynamicDrawableSpan that centers the image vertically and horizontally.
     */
    private class CenteredImageSpan(drawable: Drawable) : CustomDynamicDrawableSpan() {

        private val alignment = ALIGN_CENTER
        private val drawable: Drawable = drawable.mutate()

        override fun getDrawable(): Drawable {
            val layers = arrayOf(drawable)
            val layerDrawable = LayerDrawable(layers)
            layerDrawable.setLayerInset(0, 0, 0, 0, 30)
            return drawable
        }

        override fun getSize(
            paint: Paint,
            text: CharSequence?,
            start: Int,
            end: Int,
            fontMetricsInt: Paint.FontMetricsInt?
        ): Int {
            return drawable.intrinsicWidth
        }

        override fun draw(
            canvas: Canvas,
            text: CharSequence?,
            start: Int,
            end: Int,
            x: Float,
            top: Int,
            y: Int,
            bottom: Int,
            paint: Paint
        ) {
            val transY = (bottom + top - drawable.intrinsicHeight) / 2f
            canvas.save()
            canvas.translate(x, transY)
            drawable.draw(canvas)
            canvas.restore()
        }

        override fun getVerticalAlignment(): Int {
            return alignment
        }
    }
}

open class CustomDynamicDrawableSpan : DynamicDrawableSpan() {

    override fun getDrawable(): Drawable {
        throw UnsupportedOperationException("Not supported")
    }
}
