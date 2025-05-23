package com.vanshika.klf.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BorderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = -0x1 // white color, same as -1 in Java
        style = Paint.Style.FILL
    }

    private val thickness: Float = resources.displayMetrics.density * 3f
    private val length: Float = resources.displayMetrics.density * 48f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Top-left vertical
        canvas.drawRect(0f, 0f, thickness, length, borderPaint)
        // Top-left horizontal
        canvas.drawRect(0f, 0f, length, thickness, borderPaint)

        // Top-right vertical
        canvas.drawRect(width - thickness, 0f, width.toFloat(), length, borderPaint)
        // Top-right horizontal
        canvas.drawRect(width - length, 0f, width.toFloat(), thickness, borderPaint)

        // Bottom-left vertical
        canvas.drawRect(0f, height - length, thickness, height.toFloat(), borderPaint)
        // Bottom-left horizontal
        canvas.drawRect(0f, height - thickness, length, height.toFloat(), borderPaint)

        // Bottom-right vertical
        canvas.drawRect(width - thickness, height - length, width.toFloat(), height.toFloat(), borderPaint)
        // Bottom-right horizontal
        canvas.drawRect(width - length, height - thickness, width.toFloat(), height.toFloat(), borderPaint)
    }
}
