package com.jimbonlemu.clefer.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
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

        val toolbarBackIcon = findViewById<ImageView>(R.id.toolbar_back_icon)

        if (showBackButton) {
            toolbarBackIcon.visibility = View.VISIBLE
            this.setContentInsetsAbsolute(0, 0)
            toolbarBackIcon.setOnClickListener {
                backAction?.invoke()
            }
        } else {
            toolbarBackIcon.visibility = View.GONE
        }
    }
}

