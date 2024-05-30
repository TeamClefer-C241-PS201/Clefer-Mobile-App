package com.jimbonlemu.clefer.components

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.google.android.material.textfield.TextInputLayout
import com.jimbonlemu.clefer.R
import com.jimbonlemu.clefer.databinding.CustomViewEditTextBinding

class CustomViewEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var binding: CustomViewEditTextBinding =
        CustomViewEditTextBinding.inflate(LayoutInflater.from(context), this)

    var labelText: String
        get() = binding.label.text.toString()
        set(value) {
            binding.label.text = value
        }

    var hintText: String
        get() = binding.editText.hint.toString()
        set(value) {
            binding.editText.hint = value
        }

    var inputText: String
        get() = binding.editText.text.toString()
        set(value) {
            binding.editText.setText(value)
        }

    private var isPasswordInputType: Boolean = false
        set(value) {
            field = value
            setupInputType()
        }

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomViewEditText)
        labelText = attributes.getString(R.styleable.CustomViewEditText_labelText) ?: ""
        hintText = attributes.getString(R.styleable.CustomViewEditText_hintText) ?: ""
        isPasswordInputType = attributes.getBoolean(R.styleable.CustomViewEditText_isPasswordTypeInput, false)
        attributes.recycle()

        setupInputType()
    }

    private fun setupInputType() {
        if (isPasswordInputType) {
            binding.textInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            binding.editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        } else {
            binding.textInputLayout.endIconMode = TextInputLayout.END_ICON_NONE
            binding.editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
    }
}
