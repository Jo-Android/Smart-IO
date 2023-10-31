package com.hgtech.smartio.ui.manager.layout

import android.content.Context
import android.text.InputType
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.hgtech.smartio.databinding.LayoutTextEditBinding


fun LayoutTextEditBinding.getText(): String {
    return contentText.text.toString()
}


fun LayoutTextEditBinding.setInputType(type: Int = InputType.TYPE_CLASS_TEXT) {
    contentText.imeOptions = EditorInfo.IME_ACTION_DONE
    contentText.inputType = type
}


fun LayoutTextEditBinding.resetLayout(activity: FragmentActivity) {
    contentText.error = null
    contentText.isFocusableInTouchMode = false
    hideKeyboard(activity)
    if (getText().isEmpty()) {
        hintText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        contentText.isVisible = false
    }
}


private fun LayoutTextEditBinding.setup(isVisible: Boolean) {
    setInputType(InputType.TYPE_NULL)
    contentText.isFocusableInTouchMode = false
    contentText.isVisible = isVisible
}

private fun LayoutTextEditBinding.onEditorActionListener(
    activity: FragmentActivity,
    onCLick: () -> Unit
) {
    contentText.setOnEditorActionListener { _, _, _ ->
        resetLayout(activity)
        onCLick.invoke()
        true
    }
}

fun LayoutTextEditBinding.setupEdit(
    hint: String,
    activity: FragmentActivity,
    onCLick: () -> Unit,
    isEdit: Boolean = true
) {
    setup(hint, isEdit)
    onEditorActionListener(activity, onCLick)
}

fun LayoutTextEditBinding.setup(
    hint: String,
    isEdit: Boolean = false,
    isContentVisible: Boolean = true
) {
    setup(isContentVisible)
    edit.isVisible = isEdit
    hintText.text = hint
}

fun LayoutTextEditBinding.text(text: String, activity: FragmentActivity) {
    if (text.isNotEmpty()) {
        hintText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
        contentText.visibility = View.VISIBLE
        contentText.setText(text)
    } else
        resetLayout(activity)
}

fun LayoutTextEditBinding.edit(activity: FragmentActivity, type: Int = InputType.TYPE_CLASS_TEXT) {
    setInputType(type)
    contentText.isFocusableInTouchMode = true
    contentText.isVisible = true
    contentText.requestFocus()
    contentText.setSelection(getText().length)
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(contentText, InputMethodManager.SHOW_IMPLICIT)
}