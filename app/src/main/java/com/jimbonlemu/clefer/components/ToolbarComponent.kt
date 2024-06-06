package com.jimbonlemu.clefer.components

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.jimbonlemu.clefer.R

class ToolbarComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.custom_toolbar, this)
    }

    fun setupToolbar(title: String, showBackButton: Boolean = false, backAction: (() -> Unit)? = null) {
        val toolbarTitle = findViewById<TextView>(R.id.toolbar_title)
        toolbarTitle.text = title
        toolbarTitle.setTextColor(context.getColor(R.color.black))

        if (showBackButton) {
            this.setNavigationIcon(R.drawable.ic_arrow_back)
            this.setNavigationOnClickListener {
                backAction?.invoke()
            }
        } else {
            this.navigationIcon = null
        }
    }
}
