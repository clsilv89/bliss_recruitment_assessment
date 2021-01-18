package com.caio.modulo1.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.caio.modulo1.R

fun Context.getCardProgressDrawable(colorId: Int = R.color.colorAccent,
                                    progressStrokeWidth: Float = 8f,
                                    progressCenterRadius: Float = 32f): Drawable {
    return CircularProgressDrawable(this).apply {
        strokeWidth = progressStrokeWidth
        centerRadius = progressCenterRadius
        setColorSchemeColors(ContextCompat.getColor(this@getCardProgressDrawable, colorId))
        start()
    }
}