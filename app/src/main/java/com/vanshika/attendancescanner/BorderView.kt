package com.vanshika.attendancescanner
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BorderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFFFFFFFF.toInt() // White color
        style = Paint.Style.FILL
    }

    private val thickness = 3f * resources.displayMetrics.density // Convert to dp
    private val length = 48f * resources.displayMetrics.density // Convert to dp

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Top-left corner
        canvas.drawRect(0f, 0f, thickness, length, borderPaint)
        canvas.drawRect(0f, 0f, length, thickness, borderPaint)

        // Top-right corner
        canvas.drawRect(width - thickness, 0f, width.toFloat(), length, borderPaint)
        canvas.drawRect(width - length, 0f, width.toFloat(), thickness, borderPaint)

        // Bottom-left corner
        canvas.drawRect(0f, height - length, thickness, height.toFloat(), borderPaint)
        canvas.drawRect(0f, height - thickness, length, height.toFloat(), borderPaint)

        // Bottom-right corner
        canvas.drawRect(width - thickness, height - length, width.toFloat(), height.toFloat(), borderPaint)
        canvas.drawRect(width - length, height - thickness, width.toFloat(), height.toFloat(), borderPaint)
    }
}
