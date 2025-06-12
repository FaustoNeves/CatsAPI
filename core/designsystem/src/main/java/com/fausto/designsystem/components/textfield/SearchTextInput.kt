package com.fausto.designsystem.components.textfield

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.fausto.designsystem.R
import com.google.android.material.textfield.TextInputLayout

class SearchTextInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : TextInputLayout(context, attrs, defStyle) {
    init {
        setColors()
        setHintTextAppearance(R.style.typography_hint_text_input)
    }

    private fun setColors() {
        hintTextColor = getColorStateList(R.color.black)
        startIconDrawable = ContextCompat.getDrawable(context, R.drawable.baseline_search_24)
    }

    private fun getColorStateList(color: Int): ColorStateList {
        return ColorStateList.valueOf(
            ContextCompat.getColor(context, color)
        )
    }
}