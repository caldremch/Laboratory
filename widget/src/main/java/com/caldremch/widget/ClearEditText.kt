package com.caldremch.widget

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat

class ClearEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr), TextWatcher, OnTouchListener {

    var clearDrawable = ContextCompat.getDrawable(context, R.drawable.ic_clear)!!

    init {
        addTextChangedListener(this)
        setOnTouchListener(this)
        clearDrawable.setBounds(0, 0, clearDrawable.intrinsicWidth, clearDrawable.intrinsicHeight)
        handleVisible(text.toString())
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {

    }

    override fun afterTextChanged(s: Editable?) {
        handleVisible(s?.toString())
    }

    private fun handleVisible(content: String?) {
        Log.d("TAG", "handleVisible: $clearDrawable")
        if (TextUtils.isEmpty(content)) {
            setCompoundDrawables(null, null, null, null)
        } else {
            setCompoundDrawables(null, null, clearDrawable, null)
        }
    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {

        if (compoundDrawables[2] == null) return false
        if (event.action != MotionEvent.ACTION_UP) return false
        if (event.x > width - paddingRight - clearDrawable.intrinsicWidth) {
            setText("")
            handleVisible(null)
        }
        return false
    }


}