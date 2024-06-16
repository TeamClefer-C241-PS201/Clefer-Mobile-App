package com.jimbonlemu.clefer.components

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.jimbonlemu.clefer.R.color
import com.jimbonlemu.clefer.R.styleable
import com.jimbonlemu.clefer.databinding.CustomCircleTitleBinding

class CircleTitleComponent(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private val binding: CustomCircleTitleBinding
    init {
        binding = CustomCircleTitleBinding.inflate(LayoutInflater.from(context), this, true)
        attrs?.let { retrieveAttributes(it) }
    }

    @SuppressLint("CustomViewStyleable")
    private fun retrieveAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, styleable.CustomAnalyzeTitle)

        val circleColor = typedArray.getColor(
            styleable.CustomAnalyzeTitle_itemCircleColor,
            ContextCompat.getColor(context, color.cleferRed)
        )
        val circleText = typedArray.getString(styleable.CustomAnalyzeTitle_itemCircleText) ?: ""
        val text = typedArray.getString(styleable.CustomAnalyzeTitle_android_text) ?: ""
        typedArray.recycle()
        binding.apply {
            customCircleIcon.setCircleColor(circleColor)
            customCircleIcon.setCircleText(circleText)
            customCircleTitle.text = text
        }
    }
}