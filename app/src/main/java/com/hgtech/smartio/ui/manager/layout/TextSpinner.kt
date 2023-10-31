package com.hgtech.smartio.ui.manager.layout

import android.content.Context
import android.text.InputType
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.hgtech.smartio.databinding.LayoutHintTextSpinnerBinding

fun LayoutHintTextSpinnerBinding.getText(): String {
    return contentText.text.toString()
}


private fun LayoutHintTextSpinnerBinding.setHint(hint: String) {
    hintText.text = hint
}

private fun LayoutHintTextSpinnerBinding.setIme(ime: Int = EditorInfo.IME_ACTION_DONE) {
    contentText.imeOptions = ime
}

private fun LayoutHintTextSpinnerBinding.setInputType(type: Int = InputType.TYPE_CLASS_TEXT) {
    contentText.inputType = type
}

private fun LayoutHintTextSpinnerBinding.onEditorActionListener(
    isLast: Boolean = false,
    onClicked: () -> Unit
) {
    contentText.setOnEditorActionListener { _, _, _ ->
        if (isLast) resetLayout()
        onClicked.invoke()
        true
    }
}

fun LayoutHintTextSpinnerBinding.resetLayout() {
    if (getText().isEmpty()) {
        hintText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
        contentText.isVisible = false
    }
}

fun LayoutHintTextSpinnerBinding.setup(
    activity: FragmentActivity,
    hint: String,
    ime: Int = EditorInfo.IME_ACTION_DONE,
    type: Int = InputType.TYPE_CLASS_TEXT
) {
    root.setOnClickListener {
        onClick(activity)
    }
    setHint(hint)
    setIme(ime)
    setInputType(type)
}

fun LayoutHintTextSpinnerBinding.setup(
    activity: FragmentActivity,
    hint: String,
    ime: Int = EditorInfo.IME_ACTION_DONE,
    type: Int = InputType.TYPE_CLASS_TEXT,
    isLast: Boolean = false,
    onClicked: () -> Unit
) {
    setup(activity, hint, ime, type)
    onEditorActionListener(isLast, onClicked)
}

fun LayoutHintTextSpinnerBinding.onClick(activity: FragmentActivity) {
    activity.currentFocus?.clearFocus()
    show()
    contentText.requestFocus()
    contentText.setSelection(getText().length)
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(contentText, InputMethodManager.SHOW_IMPLICIT)
}

fun LayoutHintTextSpinnerBinding.show() {
    hintText.visibility = View.VISIBLE
    hintText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
    contentText.visibility = View.VISIBLE
}