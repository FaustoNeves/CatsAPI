package com.fausto.designsystem.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import com.squareup.picasso.Transformation

class GradientTransformation : Transformation {
    private var startColor = Color.argb(240, 0, 0, 0)
    private var endColor = Color.TRANSPARENT
    override fun transform(source: Bitmap): Bitmap {
        val x = source.width
        val y = source.height
        val gradientBitmap = source.copy(source.config!!, true)
        val canvas = Canvas(gradientBitmap)
        val grad = LinearGradient(
            (x / 2).toFloat(),
            y.toFloat(),
            (x / 2).toFloat(),
            (y / 2).toFloat(),
            startColor,
            endColor,
            Shader.TileMode.CLAMP
        )
        val p = Paint(Paint.DITHER_FLAG)
        p.shader = null
        p.isDither = true
        p.isFilterBitmap = true
        p.shader = grad
        canvas.drawPaint(p)
        source.recycle()
        return gradientBitmap
    }

    override fun key(): String {
        return "Gradient"
    }
}