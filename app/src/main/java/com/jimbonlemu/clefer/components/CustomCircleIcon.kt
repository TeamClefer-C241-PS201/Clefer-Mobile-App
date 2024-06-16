package com.jimbonlemu.clefer.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.jimbonlemu.clefer.R

class CustomCircleIcon : View {

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var circleColor: Int = Color.RED
    private var circleText: String = "1"

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
        init()
    }

    private fun init() {
        circlePaint.style = Paint.Style.FILL
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.color = Color.WHITE


        val typeface = ResourcesCompat.getFont(context, R.font.poppins_bold)
        textPaint.typeface = typeface


        val textSize = resources.getDimensionPixelSize(R.dimen.text_size_default).toFloat()
        textPaint.textSize = textSize
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleCanvasView)
        circleColor = typedArray.getColor(R.styleable.CircleCanvasView_circleColor, Color.RED)
        circleText = typedArray.getString(R.styleable.CircleCanvasView_circleText) ?: "1"
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width
        val height = height

        val radius = width.coerceAtMost(height) / 2f
        val centerX = width / 2f
        val centerY = height / 2f

        circlePaint.color = circleColor
        canvas.drawCircle(centerX, centerY, radius, circlePaint)

        val textBaseline = centerY - (textPaint.descent() + textPaint.ascent()) / 2
        canvas.drawText(circleText, centerX, textBaseline, textPaint)
    }

    fun setCircleColor(color: Int) {
        circleColor = color
        invalidate()
    }

    fun setCircleText(text: String) {
        circleText = text
        invalidate()
    }
}
