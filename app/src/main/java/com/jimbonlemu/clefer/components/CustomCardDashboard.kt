package com.jimbonlemu.clefer.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.jimbonlemu.clefer.R

class CustomCardDashboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val ivIcon: ImageView
    private val tvIconHeadline: TextView
    private val tvIconSubHeadline: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_card_dashboard, this, true)
        ivIcon = findViewById(R.id.iv_icon)
        tvIconHeadline = findViewById(R.id.tv_icon_headline)
        tvIconSubHeadline = findViewById(R.id.tv_icon_sub_headline)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomCardDashboard,
            0, 0
        ).apply {
            try {
                ivIcon.setImageDrawable(getDrawable(R.styleable.CustomCardDashboard_iconSrc))
                tvIconHeadline.text = getString(R.styleable.CustomCardDashboard_headlineText)
                tvIconSubHeadline.text = getString(R.styleable.CustomCardDashboard_subHeadlineText)
            } finally {
                recycle()
            }
        }
    }

    fun setIconSrc(resourceId: Int) {
        ivIcon.setImageResource(resourceId)
    }

    fun setHeadlineText(text: String) {
        tvIconHeadline.text = text
    }

    fun setSubHeadlineText(text: String) {
        tvIconSubHeadline.text = text
    }
}